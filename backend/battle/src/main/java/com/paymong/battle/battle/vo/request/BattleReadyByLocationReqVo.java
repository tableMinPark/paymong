package com.paymong.battle.battle.vo.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
@Builder
public class BattleReadyByLocationReqVo {
    private Long characterId;
    private Double latitude;
    private Double longitude;
    private WebSocketSession session;
}
