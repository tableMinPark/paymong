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
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    @Value("${battle.max_turn}")
    private Integer maxTurn;
    @Value("${battle.default_health}")
    private Double defaultHealth;

    private final ObjectMapper objectMapper;
    private final BattleService battleService;
    private final LocationRepository locationRepository;

    private Map<String, String> battleRoomMap;

    @PostConstruct
    private void init() {
        battleRoomMap = new LinkedHashMap<>();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();
        BattleMessageReqDto battleMessageReqDto = objectMapper.readValue(payload, BattleMessageReqDto.class);

        Long characterId = battleMessageReqDto.getCharacterId();
        Double latitude = battleMessageReqDto.getLatitude();
        Double longitude = battleMessageReqDto.getLongitude();

        try {
            switch (battleMessageReqDto.getType()){
                case CONNECT:
                    // 매칭 대기열 등록
                    log.info("handleTextMessage - CONNECT");

                    // 세션 서버에 대기열 등록
                    locationRepository.save(characterId, latitude, longitude);
                    battleService.addMatching(characterId, Matching.builder()
                            .characterId(characterId)
                            .session(session)
                            .build());

                    System.out.println("##### 현재 저장된 매칭 대기열 #####");
                    for (Matching matching : battleService.findAllMatching()) {
                        System.out.println(matching);
                    }
                    System.out.println("#################################");

                case RECONNECT:
                    // 매칭 시작
                    log.info("handleTextMessage - RECONNECT");

                    // 잠시 대기열에서 제외 (매칭 상대 찾는 동안 매칭이 이뤄지면 안됨)
                    Matching matchingA = battleService.findMatching(characterId);
                    battleService.removeMatching(characterId);

                    // 자신 기준 가까운 10명 리스트
                    List<String> idList = locationRepository.findById(characterId);

                    System.out.println("##### 자신 기준 가까운 10명 리스트 #####");
                    for (String id : idList) {
                        if (id.equals(characterId.toString())) continue;
                        System.out.println(id);
                    }
                    System.out.println("######################################");

                    Boolean isMatching = false;
                    for (String id : idList) {
                        if (characterId.equals(characterId.toString())) continue;

                        Long characterBId = Long.parseLong(id);
                        Matching matchingB = battleService.findMatching(characterBId);

                        if (matchingB != null) {
                            System.out.println("##### 매칭 상대 찾음! #####");
                            System.out.println(matchingA.getCharacterId() + " VS " + matchingB.getCharacterId());
                            System.out.println("###########################");

                            battleService.removeMatching(characterBId);
                            locationRepository.remove(characterId);
                            locationRepository.remove(characterBId);

                            String battleRoomId = UUID.randomUUID().toString();

                            CharacterStats statsA = battleService.findCharacterStats(characterId, defaultHealth);
                            CharacterStats statsB = battleService.findCharacterStats(characterBId, defaultHealth);

                            BattleRoom battleRoom = BattleRoom.builder()
                                    .maxTurn(maxTurn)
                                    .battleRoomId(battleRoomId)
                                    .sessionA(matchingA.getSession())
                                    .sessionB(matchingB.getSession())
                                    .statsA(statsA)
                                    .statsB(statsB)
                                    .build();

                            battleService.addBattleRoom(battleRoomId, battleRoom);

                            System.out.println("##### 배틀방 리스트 #####");
                            for (BattleRoom br : battleService.findAllBattleRoom()) {
                                System.out.println(br.getStatsMap().get("A").getCharacterId() + " VS " + br.getStatsMap().get("B").getCharacterId());
                            }
                            System.out.println("########################");

                            battleService.sendMessage(matchingA.getSession(), BattleMessageResDto.builder()
                                    .battleRoomId(battleRoomId)
                                    .nowTurn(0)
                                    .nextAttacker("A")                  // 무조건 A 먼저 시작
                                    .order("A")
                                    .healthA(defaultHealth)
                                    .healthB(defaultHealth)
                                    .damageA(0.0)
                                    .damageB(0.0)
                                    .build());
                            battleService.sendMessage(matchingB.getSession(), BattleMessageResDto.builder()
                                    .battleRoomId(battleRoomId)
                                    .nowTurn(0)
                                    .nextAttacker("A")                  // 무조건 A 먼저 시작
                                    .order("B")
                                    .healthA(defaultHealth)
                                    .healthB(defaultHealth)
                                    .damageA(0.0)
                                    .damageB(0.0)
                                    .build());

                            isMatching = true;
                            battleRoomMap.put(matchingA.getSession().getId(), battleRoomId);
                            battleRoomMap.put(matchingB.getSession().getId(), battleRoomId);
                            break;
                        }
                    }

                    // 매칭 실패 시 실패 응답 전송
                    if (!isMatching) {
                        battleService.addMatching(characterId, matchingA);
                        battleService.sendMessage(session, new ErrorResponse(BattleStateCode.MATCHING_FAIL));
                    }
                    break;

                case LEFT:
                case RIGHT:
                    // 방 찾아서 배틀 로직 진행
                    BattleRoom battleRoom = battleService.findBattleRoom(battleMessageReqDto.getBattleRoomId());
                    battleRoom.handlerActions(battleMessageReqDto, battleService);
                    break;
            }
        } catch (NotFoundMachingException e) {
            e.printStackTrace();
            log.error("Not Found Matching");
            battleService.sendMessage(session, new ErrorResponse(BattleStateCode.MATCHING_FAIL));
        }  catch (NotFoundException e) {
            e.printStackTrace();
            log.error("Not Found BattleRoom");
            battleService.sendMessage(session, new ErrorResponse(BattleStateCode.FIND_BATTLE_ROOM_FAIL));
        } catch (RuntimeException e) {
            e.printStackTrace();
            log.error("Internal Server Error");
            battleService.sendMessage(session, new ErrorResponse(BattleStateCode.FAIL));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 연결이 끊긴 배틀방은 게임 종료 시킴
        String battleRoomId = battleRoomMap.get(session.getId());
        BattleRoom battleRoom = battleService.findBattleRoom(battleRoomId);
        battleRoom.endBattle(battleService);
        battleService.removeBattleRoom(battleRoomId);
    }
}
