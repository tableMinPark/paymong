package com.paymong.collect.collect.repository;

import com.paymong.collect.collect.entity.MapCollect;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapCollectRepository extends JpaRepository<MapCollect, Long> {
    List<MapCollect> findByMemberIdOrderByMapCodeDesc(Long memberId);
}
