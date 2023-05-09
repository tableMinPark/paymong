package com.paymong.member.background.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@DynamicInsert
@Table(name = "mymap")
public class Mymap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mymap_id")
    private Long mymapId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "map_code")
    private String mapCode;

    @Column(name = "upd_dt")
    private LocalDateTime updDt;


}
