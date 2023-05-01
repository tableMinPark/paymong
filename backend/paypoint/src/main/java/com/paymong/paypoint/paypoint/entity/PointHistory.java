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

    @Column(name = "price")
    private int price;

    @Column(name = "code")
    private String code;

    @Column(name = "pay_dt")
    private LocalDateTime payDt;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "mong_id")
    private Long mongId;

}
