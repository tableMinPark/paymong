package com.paymong.collect.mong.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "mong_collect")
public class MongCollect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mongCollectId;


    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String mongCode;
}
