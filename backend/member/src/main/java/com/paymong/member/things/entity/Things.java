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
@Table(name = "things")
public class Things {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "things_id")
    private Long thingsId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "things_code")
    private String thingsCode;

    @Column(name = "routine")
    private String routine;

    @Column(name = "things_name")
    private String thingsName;

    @Column(name = "reg_dt")
    private LocalDateTime regDt;
}
