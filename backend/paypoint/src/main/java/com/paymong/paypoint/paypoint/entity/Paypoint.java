package com.paymong.paypoint.paypoint.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Builder
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@DynamicInsert
@Table(name = "point_history")
public class Paypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poinit_history_id")
    private Long pointHistoryId;

    @Column(name = "price")
    private String price;

    @Column(name = "code")
    private String code;

    @Column(name = "pay_dt")
    private String payDt;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "mong_id")
    private String mongId;

}
