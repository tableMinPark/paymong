package com.paymong.management.mong.repository;

import com.paymong.management.mong.entity.Mong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MongRepository extends JpaRepository<Mong, Long> {
    Optional<Mong> findByMongId(Long mongId);

    Optional<Mong> findByMemberIdAndComplete(Long memberId, Boolean complete);
}