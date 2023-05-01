package com.paymong.gateway.global.repository;

import com.paymong.gateway.global.entity.Mong;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongRepository extends JpaRepository<Mong, Long> {

    Optional<Mong> findByMemberId(Long memberId);
}
