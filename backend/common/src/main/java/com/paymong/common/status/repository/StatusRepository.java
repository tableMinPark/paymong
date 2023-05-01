package com.paymong.common.status.repository;

import com.paymong.common.status.entitiy.Status;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

    Optional<Status> findByCode(String code);
}
