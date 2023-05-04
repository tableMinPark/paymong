package com.paymong.member.paypoint.repository;

import com.paymong.member.paypoint.entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaypointRepository extends JpaRepository<PointHistory, Long> {
    List<PointHistory> findAllByMemberIdOrderByPointHistoryIdDesc(Long memberId);


}
