package com.paymong.paypoint.paypoint.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@DynamicInsert
@Table(name = "point_history")
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_id")
    private Long pointHistoryId;

    @Column(name = "point")
    private int point;

    @Column(name = "action")
    private String action;

    @Column(name = "reg_dt")
    private LocalDateTime regDt;

    @Column(name = "member_id")
    private Long memberId;

}
