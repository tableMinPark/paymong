package com.paymong.auth.auth.repository;

import com.paymong.auth.auth.entity.Auth;
import com.paymong.auth.auth.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByMember(Member member);
}
