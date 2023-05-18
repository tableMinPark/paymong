package com.paymong.management.history.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@DynamicInsert
@DynamicUpdate
public class ActiveHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "active_history_id")
    private Long activeHistoryId;
    @Column(name = "active_code")
    private String activeCode;
    @Column(name = "active_time")
    private LocalDateTime activeTime;
    @Column(name = "mong_id")
    private Long mongId;
}
