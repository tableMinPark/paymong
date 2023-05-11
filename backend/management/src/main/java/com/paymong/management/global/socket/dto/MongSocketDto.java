package com.paymong.management.global.socket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MongSocketDto {

    private Long memberId;
    private Long mongId;
    private WebSocketSession session;
}
