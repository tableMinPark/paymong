package com.paymong.common.common.repository;

import com.paymong.common.common.entity.CommonCode;
import com.paymong.common.common.entity.GroupCode;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonCodeRepository extends JpaRepository<CommonCode, String> {

    List<CommonCode> findAllByGroupCode(GroupCode groupCode);

    List<CommonCode> findByCodeStartsWith(String code);

    Optional<CommonCode> findByName(String name);

    Optional<CommonCode> findByCode(String code);
}
