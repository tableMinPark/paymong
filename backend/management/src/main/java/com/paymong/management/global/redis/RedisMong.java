package com.paymong.management.global.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RedisMong {

    @Id
    private Long mongId;

    private LocalDateTime startTime;
    private LocalDateTime stopTime;


}
