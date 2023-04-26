package com.paymong.battle.battle.dto.common;

import com.paymong.battle.battle.dto.request.BattleMessageReqDto;
import com.paymong.battle.battle.dto.response.BattleMessageResDto;
import com.paymong.battle.battle.service.BattleService;
import com.paymong.battle.information.dto.response.FindCharacterResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@Slf4j
@Getter
public class BattleRoom {
    private Integer maxTurn;
    private Double defaultHealth;

    private String battleRoomId;
    private String name;
    private Set<WebSocketSession> sessions;

    // 게임 진행 인스턴스
    private Map<String, CharacterStats> statsMap;
    private BattleLog battleLog;
    private Integer nowTurn = 0;

    @Builder
    public BattleRoom(Integer maxTurn, Double defaultHealth, String battleRoomId, String name) {
        this.maxTurn = maxTurn;
        this.defaultHealth = defaultHealth;
        this.battleRoomId = battleRoomId;
        this.name = name;
        this.sessions = new HashSet<>();
        this.statsMap = new HashMap<>();
        this.battleLog = new BattleLog();
        this.nowTurn = 0;
    }

    // A 가 먼저 공격 (A는 기본적으로 먼저 방만든 사람이 됨)
    public void handlerActions(WebSocketSession session, BattleMessageReqDto battleMessageReqDto, BattleService battleService) {

        Long characterId = battleMessageReqDto.getCharacterId();
        String order = battleMessageReqDto.getOrder();

        // 입장
        if (battleMessageReqDto.getType().equals(BattleMessageReqDto.MessageType.ENTER)) {
            log.info(order + " 입장");

            // 캐릭터 스텟 API 호출
            FindCharacterResponse findCharacterResponse = new FindCharacterResponse();
            findCharacterResponse.setCharacterId(characterId);
            findCharacterResponse.setStrength(1);
            findCharacterResponse.setWeight(2);

            // 스텟 저장
            statsMap.put(order,  CharacterStats.builder()
                    .characterId(findCharacterResponse.getCharacterId())
                    .health(defaultHealth)
                    .strength(findCharacterResponse.getStrength())
                    .weight(findCharacterResponse.getWeight())
                    .build());
            // 소켓 세션 저장
            sessions.add(session);
            
            // 상대방 입장 했으면 배틀 시작
            if (statsMap.size() == 2) {
                sendMessage(BattleMessageResDto.builder()
                        .nowTurn(nowTurn)
                        .nextAttacker("A")                  // 무조건 A 먼저 시작
                        .healthA(defaultHealth)
                        .healthB(defaultHealth)
                        .damageA(0.0)
                        .damageB(0.0)
                        .build(), battleService);
                nowTurn++;
            }
        }
        // 배틀 선택
        else{
            log.info(nowTurn + " : " + order + " 선택완료");
            if (battleMessageReqDto.getType().equals(BattleMessageReqDto.MessageType.LEFT)){
                if (order.equals("A"))
                    battleLog.setSelectA(BattleLog.FightType.LEFT);
                else
                    battleLog.setSelectB(BattleLog.FightType.LEFT);
            } else {
                if (order.equals("A"))
                    battleLog.setSelectA(BattleLog.FightType.RIGHT);
                else
                    battleLog.setSelectB(BattleLog.FightType.RIGHT);
            }

            // 한턴이 끝났는지 확인
            Boolean isOver = false;
            if ((order.equals("A") && battleLog.getSelectB() != null) || (order.equals("B") && battleLog.getSelectA() != null)) {
                isOver = true;
            }

            // 한턴이 끝남
            if (isOver) {
                log.info("모두 선택완료 결과 계산");
                CharacterStats statsA = statsMap.get("A");
                CharacterStats statsB = statsMap.get("B");

                BattleMessageResDto battleMessageResDto
                        = battleService.battleActive(nowTurn, statsA, statsB, battleLog);

                Double healthA = statsA.getHealth() - battleMessageResDto.getDamageA();
                Double healthB = statsB.getHealth() - battleMessageResDto.getDamageB();

                // 응답 갱신
                battleMessageResDto.setNowTurn(nowTurn);
                battleMessageResDto.setHealthA(healthA);
                battleMessageResDto.setHealthB(healthB);

                // 턴 갱신, 현재 체력 갱신
                statsMap.get("A").setHealth(healthA);
                statsMap.get("B").setHealth(healthB);
                
                sendMessage(battleMessageResDto, battleService);
                // 마지막 턴인지 확인
                if (maxTurn.equals(nowTurn) || healthA <= 0 || healthB <= 0){
                    // nowTurn을 -1로 보냄
                    battleMessageResDto.setNowTurn(-1);
                    battleMessageResDto.setDamageA(0.0);
                    battleMessageResDto.setDamageB(0.0);
                    if (healthA < 0) battleMessageResDto.setHealthA(0.0);
                    if (healthB < 0) battleMessageResDto.setHealthB(0.0);

                    sendMessage(battleMessageResDto, battleService);
                } else {
                    nowTurn++;
                    battleLog.setSelectA(null);
                    battleLog.setSelectB(null);
                }
            }
        }
    }

    private <T> void sendMessage(T message, BattleService battleService) {
        sessions.parallelStream()
                .forEach(session -> battleService.sendMessage(session, message));
    }
}
