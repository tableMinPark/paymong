package com.paymong.battle.battle.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.battle.battle.dto.common.BattleLog;
import com.paymong.battle.battle.dto.common.BattleUser;
import com.paymong.battle.battle.dto.common.CharacterStats;
import com.paymong.battle.battle.dto.response.BattleMessageResDto;
import com.paymong.battle.battle.dto.common.BattleRoom;
import com.paymong.battle.battle.vo.request.BattleReadyByLocationReqVo;
import com.paymong.battle.battle.vo.response.BattleReadyByLocationResVo;
import com.paymong.battle.global.exception.NotFoundException;
import com.paymong.battle.global.redis.LocationRepository;
import com.paymong.battle.global.redis.MatchingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BattleService {
    private final LocationRepository locationRepository;
    private final MatchingRepository matchingRepository;
    // 상수
    @Value("${battle.max_turn}")
    private Integer maxTurn;
    @Value("${battle.default_health}")
    private Double defaultHealth;
    private final ObjectMapper objectMapper;

    /*  redis 로 이식 부분 시작 (로드밸런싱 구동)*/
    private Map<String, BattleRoom> battleRoomMap;

    /* redis 이식 부분 끝 */

    @PostConstruct
    private void init() {
        battleRoomMap = new LinkedHashMap<>();
    }

    public BattleRoom findRoomById(String battleRoomId) throws NotFoundException {
        BattleRoom battleRoom = Optional.ofNullable(battleRoomMap.get(battleRoomId))
                                        .orElseThrow(() -> new NotFoundException());
        return battleRoom;
    }

    public BattleReadyByLocationResVo battleReadyByLocation(BattleReadyByLocationReqVo battleReadyByLocationReqVo) throws RuntimeException {

        Long characterId = battleReadyByLocationReqVo.getCharacterId();
        Double latitude = battleReadyByLocationReqVo.getLatitude();
        Double longitude = battleReadyByLocationReqVo.getLongitude();

        String battleRoomId = "";
        String name = "";
        String order = "";

        // 대기 세션서버에 대기열 등록
        locationRepository.save(characterId, latitude, longitude);
        matchingRepository.save(characterId);

        // 자신 기준 가까운 10명 리스트
        List<String> characterIdList = locationRepository.findById(characterId);

        for (String otherCharacterId : characterIdList) {
            Long id = Long.parseLong(otherCharacterId);
            // 매칭이 되었는지 확인
            if (matchingRepository.findById(id).isPresent()) {
                // 정보 조회
                BattleUser battleUser = matchingRepository.findById(id).get();
                // 다른 사람이 매칭하지 못하게 대기열에서 삭제
                matchingRepository.remove(id);
                // 상대방 세션에 연결해서 매칭 정보 전달

                break;
            }
        }

        return BattleReadyByLocationResVo.builder()
                .battleRoomId(battleRoomId)
                .name(name)
                .order(order)
                .build();
    }

    public BattleMessageResDto battleActive(Integer nowTurn, CharacterStats statsA, CharacterStats statsB, BattleLog battleLog){

        /*
            - 몸무게 = 방어력  ///  근력 = 공격
            - 서로의 피를 알 수 있음
            - 받는 데미지 : 상대 데미지 * 100/(100+나의 방어력)
            - 주는 데미지 : 100 + 나의 공격력
            - 최대 10번 턴 제
            - 체력 500
        */

        String nextAttacker;
        Double damageA = 0.0;
        Double damageB = 0.0;

        if (nowTurn % 2 != 0) {
            // A 공격
            if (!isSameDirection(battleLog)) {
                // B가 A의 공격을 받음
                log.info("A 공격 : 다른방향");
                Double powerA = 100.0 + statsA.getStrength();
                Double defenseB = 100.0 + statsB.getWeight();
                damageB = powerA * 100 / defenseB;
            } else {
                log.info("A 공격 : 같은방향");
                // B가 A의 공격을 회피함
                damageA = 0.0;
                damageB = 0.0;
            }
            nextAttacker = "B";

        } else {
            // B 공격
            if (!isSameDirection(battleLog)) {
                // A가 B의 공격을 받음
                log.info("B 공격 : 다른방향");
                Double powerB = 100.0 + statsB.getStrength();
                Double defenseA = 100.0 + statsA.getWeight();
                damageA = powerB * 100 / defenseA;
            } else {
                // A가 B의 공격을 회피함
                log.info("B 공격 : 같은방향");
                damageA = 0.0;
                damageB = 0.0;
            }
            nextAttacker = "A";
        }
        return BattleMessageResDto.builder()
                .nextAttacker(nextAttacker)
                .damageA(damageA)
                .damageB(damageB)
                .build();
    }

    private boolean isSameDirection(BattleLog battleLog) {
        // 같은 방향
        if (battleLog.getSelectA().equals(battleLog.getSelectB()))
            return true;
        // 다른 방향
        else
            return false;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
