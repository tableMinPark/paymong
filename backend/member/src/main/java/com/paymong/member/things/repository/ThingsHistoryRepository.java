package com.paymong.member.things.repository;

import com.paymong.member.things.entity.Things;
import com.paymong.member.things.entity.ThingsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThingsHistoryRepository extends JpaRepository<ThingsHistory, Long> {
    ThingsHistory findTopByMemberIdAndThingsCodeOrderByThingsHistoryIdDesc(Long memberId, String thingsCode);
}
