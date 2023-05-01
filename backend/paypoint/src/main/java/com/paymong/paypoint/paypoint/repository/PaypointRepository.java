package com.paymong.paypoint.paypoint.repository;

import com.paymong.paypoint.paypoint.entity.Paypoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaypointRepository extends JpaRepository<Paypoint, Long> {
}
