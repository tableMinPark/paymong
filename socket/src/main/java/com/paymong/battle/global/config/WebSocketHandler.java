package com.paymong.battle.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.battle.battle.dto.request.BattleMessageReqDto;
import com.paymong.battle.battle.dto.response.BattleMessageResDto;
import com.paymong.battle.battle.vo.common.BattleRoom;
import com.paymong.battle.battle.service.BattleService;
import com.paymong.battle.battle.vo.common.CharacterStats;
import com.paymong.battle.battle.vo.common.Matching;
import com.paymong.battle.global.code.BattleStateCode;
import com.paymong.battle.global.exception.NotFoundException;
import com.paymong.battle.global.exception.NotFoundMachingException;
import com.paymong.battle.global.redis.LocationRepository;
import com.paymong.battle.global.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import javax.websocket.Session;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@EnableScheduling
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    @Value("${battle.max_turn}")
    private Integer totalTurn;
    @Value("${battle.default_health}")
    private Double defaultHealth;

    private final Integer maxMatchingTime = 5;

    private final ObjectMapper objectMapper;
    private final BattleService battleService;
    private final LocationRepository locationRepository;

    private Queue<Matching> matchingQueue;
    private Map<Long, Matching> matchingMap;
    private Map<String, String> battleRoomMap;
    private Map<Long, String> battleCharacterIdMap;

    @Scheduled(fixedDelay = 500)
    public void battleMatching() {

        if (matchingQueue.isEmpty()) return;

        try {
            log.info("handleTextMessage - Scheduler");

            Matching matchingA = matchingQueue.poll();

            Long characterId = matchingA.getCharacterId();
            WebSocketSession session = matchingA.getSession();

            // 이미 다른사람과 매칭된 플레이어는 패스
            if (matchingMap.get(characterId) == null) return;

            // 매칭 실패 (maxMatchingCount 만큼 매칭을 돌렸을 때)
            Long waitTime = Duration.between(matchingA.getMatchingStart(), LocalDateTime.now()).getSeconds();
            if (waitTime > maxMatchingTime) {
                battleService.sendMessage(session, new ErrorResponse(BattleStateCode.MATCHING_FAIL));
                return;
            }

            log.info(matchingA.getCharacterId() + " " + waitTime);

            // 자신 기준 가까운 10명 리스트
            List<String> idList = locationRepository.findById(matchingA.getCharacterId());

            Boolean isMatching = false;
            for (String id : idList) {
                if (id.equals(characterId.toString())) continue;

                Long characterBId = Long.parseLong(id);
                Matching matchingB = matchingMap.get(characterBId);

                if (matchingB != null) {
                    // 현재 매칭된 플레이어 대기열에서 삭제
                    matchingMap.remove(characterBId);
                    // 위치정보 삭제
                    locationRepository.remove(characterId);
                    locationRepository.remove(characterBId);

                    // 방 ID 생성
                    String battleRoomId = UUID.randomUUID().toString();

                    // 기본 체력 설정
                    CharacterStats statsA = battleService.findCharacterStats(characterId, defaultHealth);
                    CharacterStats statsB = battleService.findCharacterStats(characterBId, defaultHealth);

                    // 배틀방 생성
                    BattleRoom battleRoom = BattleRoom.builder()
                            .totalTurn(totalTurn)
                            .battleRoomId(battleRoomId)
                            .sessionA(matchingA.getSession())
                            .sessionB(matchingB.getSession())
                            .statsA(statsA)
                            .statsB(statsB)
                            .build();

                    // 방 Map에 추가
                    battleService.addBattleRoom(battleRoomId, battleRoom);

                    // A 매칭 응답 전송
                    battleService.sendMessage(matchingA.getSession(), BattleMessageResDto.builder()
                            .battleRoomId(battleRoomId)
                            .nowTurn(0)
                            .totalTurn(totalTurn)
                            .nextAttacker("A")                  // 무조건 A 먼저 시작
                            .order("A")
                            .healthA(defaultHealth)
                            .healthB(defaultHealth)
                            .damageA(0.0)
                            .damageB(0.0)
                            .build());

                    // B 매칭 응답 전송
                    battleService.sendMessage(matchingB.getSession(), BattleMessageResDto.builder()
                            .battleRoomId(battleRoomId)
                            .nowTurn(0)
                            .totalTurn(totalTurn)
                            .nextAttacker("A")                  // 무조건 A 먼저 시작
                            .order("B")
                            .healthA(defaultHealth)
                            .healthB(defaultHealth)
                            .damageA(0.0)
                            .damageB(0.0)
                            .build());

                    // 배틀 진행 맵에 저장
                    battleCharacterIdMap.put(characterId, battleRoomId);
                    battleCharacterIdMap.put(characterBId, battleRoomId);
                    battleRoomMap.put(matchingA.getSession().getId(), battleRoomId);
                    battleRoomMap.put(matchingB.getSession().getId(), battleRoomId);
                    isMatching = true;
                }
            }

            // 매칭되지 않으면 대기열로 다시 복귀
            if (!isMatching) {
                matchingQueue.add(Matching.builder()
                        .characterId(matchingA.getCharacterId())
                        .session(matchingA.getSession())
                        .matchingStart(matchingA.getMatchingStart())
                        .build());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    private void init() {
        battleRoomMap = new LinkedHashMap<>();
        battleCharacterIdMap = new LinkedHashMap<>();
        matchingQueue = new ArrayDeque<>();
        matchingMap = new LinkedHashMap<>();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            String payload = message.getPayload();
            BattleMessageReqDto battleMessageReqDto = objectMapper.readValue(payload, BattleMessageReqDto.class);

            log.info(payload);

            Long characterId = battleMessageReqDto.getCharacterId();
            Double latitude = battleMessageReqDto.getLatitude();
            Double longitude = battleMessageReqDto.getLongitude();

            switch (battleMessageReqDto.getType()){
                case CONNECT:
                    // 매칭 대기열 등록
                    log.info("handleTextMessage - CONNECT");

                    // 세션 서버에 대기열 등록
                    Matching player = Matching.builder()
                            .characterId(characterId)
                            .session(session)
                            .matchingStart(LocalDateTime.now())
                            .build();

                    locationRepository.save(characterId, latitude, longitude);
                    matchingQueue.add(player);
                    matchingMap.put(characterId, player);

                    System.out.println(matchingQueue);
                    System.out.println(matchingMap);

                    break;

                case DISCONNECT:
                    // 매칭 대기열에서 삭제
                    log.info("handleTextMessage - DISCONNECT");
                    Thread.sleep(1000);
                    // 세션 서버에 대기열 삭제
                    locationRepository.remove(characterId);
                    matchingMap.remove(characterId);

                    System.out.println(matchingQueue);
                    System.out.println(matchingMap);

                    // 배틀중에 탈주하는 경우
                    String battleRoomId = battleCharacterIdMap.get(characterId);
                    if (battleRoomId != null){
                        BattleRoom battleRoom = battleService.findBattleRoom(battleRoomId);
                        // 정상 종료
                        battleRoom.endBattle(battleService, battleRoomId);
                        if (battleRoom != null) {
                            battleService.removeBattleRoom(battleRoomId);
                        }
                    }
                    break;

                case LEFT:
                case RIGHT:
                    // 방 찾아서 배틀 로직 진행
                    BattleRoom battleRoom = battleService.findBattleRoom(battleMessageReqDto.getBattleRoomId());
                    battleRoom.handlerActions(battleMessageReqDto, battleService);
                    break;
            }
        } catch (Exception e) {
            log.error("handleTextMessage - exception");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 연결이 끊긴 배틀방은 게임 종료 시킴
//        String battleRoomId = battleRoomMap.get(session.getId());
//        if (battleRoomId != null){
//            BattleRoom battleRoom = battleService.findBattleRoom(battleRoomId);
//            battleRoom.endBattle(battleService);
//            battleService.removeBattleRoom(battleRoomId);
//        }
//
//        // 매칭을 하지 않고 연결해제하는 경우 대기열에서 제거
//        Long characterId = characterIdMap.get(session.getId());
//        locationRepository.remove(characterId);
    }
}
