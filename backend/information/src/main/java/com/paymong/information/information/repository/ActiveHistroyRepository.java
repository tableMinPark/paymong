package com.paymong.information.information.repository;

import com.paymong.information.information.entity.ActiveHistory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveHistroyRepository extends JpaRepository<ActiveHistory,Long> {
    Optional<ActiveHistory> findTopByMongIdAndActiveCodeOrderByActiveTimeDesc(Long mongId, String activeCode);
}
