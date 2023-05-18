package com.paymong.collect.mong.repository;

import com.paymong.collect.mong.entity.MongCollect;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongCollectRepository extends JpaRepository<MongCollect, Long> {

    List<MongCollect> findByMemberIdOrderByMongCodeDesc(Long memberId);

    Optional<MongCollect> findByMemberIdAndMongCode(Long memberID, String mongCode);
}
