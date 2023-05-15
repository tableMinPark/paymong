package com.paymong.member.background.repository;

import com.paymong.member.background.entity.Mymap;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MymapRepository extends JpaRepository<Mymap, Long> {

	Optional<Mymap> getByMemberId(Long memberId);
}
