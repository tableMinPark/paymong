package com.paymong.collect.collect.repository;

import com.paymong.collect.collect.entity.MongCollect;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongCollectRepository extends JpaRepository<MongCollect, Long> {

    List<MongCollect> findByMemberIdOrderBMongCodeDesc(Long memberId);
}
