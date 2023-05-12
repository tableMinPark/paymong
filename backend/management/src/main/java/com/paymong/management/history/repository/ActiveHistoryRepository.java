package com.paymong.management.history.repository;

import com.paymong.management.history.entity.ActiveHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveHistoryRepository extends JpaRepository<ActiveHistory, Long> {
}
