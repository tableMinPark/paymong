package com.paymong.battle.battle.vo.common;

import lombok.Data;

@Data
public class BattleLog {
    public enum FightType {
        LEFT, RIGHT, STAY
    }
    private FightType selectA;
    private FightType selectB;
}

