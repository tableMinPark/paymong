package com.paymong.collect.collect.entity;

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

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicInsert
@Table(name = "map_collect")
public class MapCollect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mapCollectId;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String mapCode;


}
