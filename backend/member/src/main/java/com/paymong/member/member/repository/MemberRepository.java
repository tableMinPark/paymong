package com.paymong.member.member.repository;
;
import java.util.Optional;

import com.paymong.member.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByPlayerId(String playerId);

    Optional<Member> findByMemberId(Long memberId);
}
