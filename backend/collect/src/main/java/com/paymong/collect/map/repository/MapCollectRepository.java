package com.paymong.collect.map.repository;

import com.paymong.collect.map.entity.MapCollect;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapCollectRepository extends JpaRepository<MapCollect, Long> {

    List<MapCollect> findAllByMemberIdOrderByMapCodeDesc(Long memberId);

    Optional<MapCollect> findByMemberIdAndMapCode(Long memberId, String mapCode);
}
