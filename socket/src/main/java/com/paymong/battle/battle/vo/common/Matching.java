package com.paymong.battle.battle.vo.common;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;

@Data
@Builder
public class Matching {
    private Long mongId;
    private String mongCode;
    private WebSocketSession session;
    private LocalDateTime matchingStart;
}
