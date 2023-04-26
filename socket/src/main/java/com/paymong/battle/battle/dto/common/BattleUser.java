package com.paymong.battle.battle.dto.common;

import lombok.Data;

import javax.websocket.Session;

@Data
public class BattleUser {
    private Long characterId;
    private Session session;
}
