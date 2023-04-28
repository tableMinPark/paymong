package com.paymong.management.status.entitiy;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Long statusId;
    @Column(name = "code")
    private String code;
    @Column(name = "point")
    private Integer point;
    @Column(name = "strength")
    private Integer strength;
    @Column(name = "health")
    private Integer health;
    @Column(name = "satiety")
    private Integer satiety;
    @Column(name = "sleep")
    private Integer sleep;
    @Column(name = "weight")
    private Integer weight;
}
