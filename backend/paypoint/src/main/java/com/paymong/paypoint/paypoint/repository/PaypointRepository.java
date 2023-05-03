package com.paymong.paypoint.paypoint.repository;

import com.paymong.paypoint.paypoint.entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaypointRepository extends JpaRepository<PointHistory, Long> {
    List<PointHistory> findAllByMemberIdOrderByPointHistoryIdDesc(Long memberId);


}
