package com.paymong.management.mong.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class Mong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mong_id")
    private Long mongId;
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "code")
    private String code;
    @Column(name = "state_code")
    private String stateCode;
    @Column(name = "name")
    private String name;
    @Column(name = "reg_dt")
    private LocalDate regDt;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "age")
    private Integer age;
    @Column(name = "strength")
    private Integer strength;
    @Column(name = "satiety")
    private Integer satiety;
    @Column(name = "health")
    private Integer health;
    @Column(name = "sleep")
    private Integer sleep;
    @Column(name = "penalty")
    private Integer penalty;
    @Column(name = "training_count")
    private Integer trainingCount;
    @Column(name = "stroke_count")
    private Integer strokeCount;
    @Column(name = "poop_count")
    private Integer poopCount;
    @Column(name = "sleep_start")
    private LocalTime sleepStart;
    @Column(name = "sleep_end")
    private LocalTime sleepEnd;
    @Column(name = "active")
    private Boolean active;
}
