package com.paymong.member.things.entity;

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
@Table(name = "things_history")
public class ThingsHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "things_history_id")
    private Long thingsHistoryId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "things_code")
    private String thingsCode;

    @Column(name = "reg_dt")
    private LocalDateTime regDt;
}
