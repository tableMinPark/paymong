package com.paymong.common.status.entitiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
