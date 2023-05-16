package com.paymong.battle.battle.vo.common;

import com.paymong.battle.battle.dto.request.BattleMessageReqDto;
import com.paymong.battle.battle.dto.response.BattleMessageResDto;
import com.paymong.battle.battle.service.BattleService;
import com.paymong.battle.battle.vo.common.BattleLog.FightType;
import com.paymong.battle.global.code.MessageType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Getter
public class BattleRoom {

    private Integer totalTurn;
    private String battleRoomId;
    private Map<String, WebSocketSession> sessions;
    private Map<String, MongStats> statsMap;
    private BattleLog battleLog;
    private Integer nowTurn = 0;


    @Builder
    public BattleRoom(Integer totalTurn,
        String battleRoomId,
        WebSocketSession sessionA,
        WebSocketSession sessionB,
        MongStats statsA,
        MongStats statsB
    ) {
        this.totalTurn = totalTurn;
        this.battleRoomId = battleRoomId;
        this.sessions = new HashMap<>() {{
            put("A", sessionA);
            put("B", sessionB);
        }};
        this.statsMap = new HashMap<>() {{
            put("A", statsA);
            put("B", statsB);
        }};
        this.battleLog = new BattleLog();
        this.nowTurn = 1;
    }

    // A 가 먼저 공격 (A는 기본적으로 먼저 방만든 사람이 됨)
    public void handlerActions(BattleMessageReqDto battleMessageReqDto, BattleService battleService)
        throws IOException, RuntimeException {

        String order = battleMessageReqDto.getOrder();

        log.info(nowTurn + " : " + order + " 선택완료");
        // LEFT, RIGHT, STAY 3가지 TYPE
        if (battleMessageReqDto.getType().equals(MessageType.LEFT)) {
            if (order.equals("A")) {
                battleLog.setSelectA(BattleLog.FightType.LEFT);
            } else {
                battleLog.setSelectB(BattleLog.FightType.LEFT);
            }
        } else if (battleMessageReqDto.getType().equals(MessageType.RIGHT)) {
            if (order.equals("A")) {
                battleLog.setSelectA(BattleLog.FightType.RIGHT);
            } else {
                battleLog.setSelectB(BattleLog.FightType.RIGHT);
            }
        } else {
            if (order.equals("A")) {
                battleLog.setSelectA(BattleLog.FightType.STAY);
            } else {
                battleLog.setSelectB(BattleLog.FightType.STAY);
            }
        }

        // 한턴이 끝났는지 확인
        Boolean isOver = false;
        if ((order.equals("A") && battleLog.getSelectB() != null) || (order.equals("B")
            && battleLog.getSelectA() != null)) {
            isOver = true;
        }

        // 한턴이 끝남
        if (isOver) {
            log.info("모두 선택완료 결과 계산");
            MongStats statsA = statsMap.get("A");
            MongStats statsB = statsMap.get("B");

            BattleMessageResDto battleMessageResDto
                = battleService.battleActive(nowTurn, statsA, statsB, battleLog);

            Double healthA = statsA.getHealth() - battleMessageResDto.getDamageA();
            Double healthB = statsB.getHealth() - battleMessageResDto.getDamageB();

            // 응답 갱신
            battleMessageResDto.setBattleRoomId(battleMessageReqDto.getBattleRoomId());
            battleMessageResDto.setMongCodeA("");
            battleMessageResDto.setMongCodeB("");
            battleMessageResDto.setNowTurn(nowTurn);
            battleMessageResDto.setTotalTurn(totalTurn);
            battleMessageResDto.setHealthA(healthA);
            battleMessageResDto.setHealthB(healthB);

            // 턴 갱신, 현재 체력 갱신
            statsMap.get("A").setHealth(healthA);
            statsMap.get("B").setHealth(healthB);

            // 마지막 턴인지 확인
            if (totalTurn.equals(nowTurn) || healthA <= 0 || healthB <= 0) {
                // point 갱신 해야함

                // 1. 동점
                if (healthA == 0 && healthB == 0) {
                    battleService.keepMoney(statsA.getMongId());
                    battleService.keepMoney(statsB.getMongId());
                }

                // 2. A 우승
                else if (healthA > healthB) {
                    battleService.earnMoney(statsA.getMongId());
                    battleService.spendMoney(statsB.getMongId());
                }

                // 3. B 우승
                else if (healthB > healthA) {
                    battleService.spendMoney(statsA.getMongId());
                    battleService.earnMoney(statsB.getMongId());
                }

                battleMessageResDto.setNowTurn(-1);
            } else {
                nowTurn++;
                battleLog.setSelectA(null);
                battleLog.setSelectB(null);
            }

            battleMessageResDto.setOrder("A");
            battleService.sendMessage(sessions.get("A"), battleMessageResDto);
            battleMessageResDto.setOrder("B");
            battleService.sendMessage(sessions.get("B"), battleMessageResDto);
        }
    }

    private BattleMessageResDto endMessage() {
        return BattleMessageResDto.builder()
            .battleRoomId(battleRoomId)
            .mongCodeA("")
            .mongCodeB("")
            .nowTurn(-1)
            .totalTurn(totalTurn)
            .nextAttacker("-")
            .order("-")
            .damageA(0.0)
            .damageB(0.0)
            .healthA(statsMap.get("A").getHealth())
            .healthB(statsMap.get("B").getHealth())
            .build();
    }


    public void endBattle(BattleService battleService, String battleRoomId) {
        log.info("배틀 종료");
        sessions.values().parallelStream()
            .forEach(session -> battleService.sendMessage(session, endMessage()));
    }
}
