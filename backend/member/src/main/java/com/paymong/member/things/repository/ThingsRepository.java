package com.paymong.member.things.repository;


import com.paymong.member.things.dto.response.FindThingsListResDto;
import com.paymong.member.things.entity.Things;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ThingsRepository extends JpaRepository<Things, Long> {

    List<Things> findAllByMemberId(Long memberId);

    void deleteByMemberIdAndThingsId(Long memberId, Long thingsId);

    Optional<Things> findByMemberIdAndRoutine(Long memberId, String routine);
}
