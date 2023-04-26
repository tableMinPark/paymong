package com.paymong.battle.battle.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.battle.battle.vo.common.BattleLog;
import com.paymong.battle.battle.vo.common.Matching;
import com.paymong.battle.battle.vo.common.CharacterStats;
import com.paymong.battle.battle.dto.response.BattleMessageResDto;
import com.paymong.battle.battle.vo.common.BattleRoom;
import com.paymong.battle.global.exception.NotFoundException;
import com.paymong.battle.information.dto.response.FindCharacterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BattleService {
    private final ObjectMapper objectMapper;
    private Map<String, BattleRoom> battleRoomMap;
    private Map<Long, Matching> matchingMap;

    @PostConstruct
    private void init() {
        battleRoomMap = new LinkedHashMap<>();
        matchingMap = new LinkedHashMap<>();
    }

    public BattleRoom findBattleRoom(String battleRoomId) throws NotFoundException {
        return Optional.ofNullable(battleRoomMap.get(battleRoomId))
                                        .orElseThrow(() -> new NotFoundException());
    }

    public List<BattleRoom> findAllBattleRoom() {
        return battleRoomMap.values().stream().collect(Collectors.toList());
    }

    public void addBattleRoom(String battleRoomId, BattleRoom battleRoom) {
        battleRoomMap.put(battleRoomId, battleRoom);
    }

    public void removeBattleRoom(String battleRoomId) {
        battleRoomMap.remove(battleRoomId);
    }

    public Matching findMatching(Long characterId) {
        return matchingMap.get(characterId);
    }

    public List<Matching> findAllMatching() {
        return matchingMap.values().stream().collect(Collectors.toList());
    }

    public void addMatching(Long characterId, Matching matching) throws RuntimeException {
        matchingMap.put(characterId, matching);
    }

    public void removeMatching(Long characterId) {
        matchingMap.remove(characterId);
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

    public CharacterStats findCharacterStats(Long characterId, Double defaultHealth) {
        FindCharacterResponse findCharacterResponse = new FindCharacterResponse();
        findCharacterResponse.setCharacterId(characterId);
        findCharacterResponse.setStrength(1);
        findCharacterResponse.setWeight(2);

        return CharacterStats.builder()
                .characterId(findCharacterResponse.getCharacterId())
                .health(defaultHealth)
                .strength(findCharacterResponse.getStrength())
                .weight(findCharacterResponse.getWeight())
                .build();
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
