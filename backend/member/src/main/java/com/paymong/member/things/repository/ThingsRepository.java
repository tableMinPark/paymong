package com.paymong.member.things.repository;


import com.paymong.member.things.dto.response.FindThingsListResDto;
import com.paymong.member.things.entity.Things;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThingsRepository extends JpaRepository<Things, Long> {

    List<Things> findAllByMemberId(Long memberId);

    void deleteByMemberIdAndThingsId(Long memberId, Long thingsId);
}
