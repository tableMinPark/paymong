package com.paymong.battle.battle.vo.common;

import com.paymong.battle.battle.dto.request.BattleMessageReqDto;
import com.paymong.battle.battle.dto.response.BattleMessageResDto;
import com.paymong.battle.battle.service.BattleService;
import com.paymong.battle.global.code.BattleStateCode;
import com.paymong.battle.global.code.MessageType;
import com.paymong.battle.global.response.ErrorResponse;
import com.paymong.battle.information.dto.response.FindCharacterResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;

@Slf4j
@Getter
public class BattleRoom {
    private Integer maxTurn;
    private String battleRoomId;
    private Map<String, WebSocketSession> sessions;
    private Map<String, CharacterStats> statsMap;
    private BattleLog battleLog;
    private Integer nowTurn = 0;

    @Builder
    public BattleRoom(Integer maxTurn,
                      String battleRoomId,
                      WebSocketSession sessionA,
                      WebSocketSession sessionB,
                      CharacterStats statsA,
                      CharacterStats statsB
    ) {
        this.maxTurn = maxTurn;
        this.battleRoomId = battleRoomId;
        this.sessions = new HashMap<>() {{ put("A", sessionA); put("B", sessionB); }};
        this.statsMap = new HashMap<>() {{ put("A", statsA); put("B", statsB); }};
        this.battleLog = new BattleLog();
        this.nowTurn = 1;
    }

    // A 가 먼저 공격 (A는 기본적으로 먼저 방만든 사람이 됨)
    public void handlerActions(BattleMessageReqDto battleMessageReqDto, BattleService battleService) throws IOException, RuntimeException {

        String order = battleMessageReqDto.getOrder();

        log.info(nowTurn + " : " + order + " 선택완료");
        if (battleMessageReqDto.getType().equals(MessageType.LEFT)){
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
            battleMessageResDto.setBattleRoomId(battleMessageReqDto.getBattleRoomId());
            battleMessageResDto.setNowTurn(nowTurn);
            battleMessageResDto.setHealthA(healthA);
            battleMessageResDto.setHealthB(healthB);

            // 턴 갱신, 현재 체력 갱신
            statsMap.get("A").setHealth(healthA);
            statsMap.get("B").setHealth(healthB);

            battleMessageResDto.setOrder("A");
            battleService.sendMessage(sessions.get("A"), battleMessageResDto);
            battleMessageResDto.setOrder("B");
            battleService.sendMessage(sessions.get("B"), battleMessageResDto);

            // 마지막 턴인지 확인
            if (maxTurn.equals(nowTurn) || healthA <= 0 || healthB <= 0){
                // nowTurn을 -1로 보냄
                endBattle(battleService);
                sessions.values().parallelStream()
                        .forEach(session -> {
                            try {
                                session.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
            } else {
                nowTurn++;
                battleLog.setSelectA(null);
                battleLog.setSelectB(null);
            }
        }
    }

    private BattleMessageResDto endMessage() {
        return BattleMessageResDto.builder()
                .battleRoomId(null)
                .nowTurn(-1)
                .nextAttacker(null)
                .order(null)
                .damageA(0.0)
                .damageB(0.0)
                .healthA(statsMap.get("A").getHealth())
                .healthB(statsMap.get("B").getHealth())
                .build();
    }


    public void endBattle(BattleService battleService) {
        sessions.values().parallelStream()
                .forEach(session -> battleService.sendMessage(session, endMessage()) );
    }

    private <T> void sendMessage(T message, BattleService battleService) {
        sessions.values().parallelStream()
                .forEach(session -> battleService.sendMessage(session, message));
    }
}
