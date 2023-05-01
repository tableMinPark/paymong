package com.paymong.common.common.repository;

import com.paymong.common.common.entity.CommonCode;
import com.paymong.common.common.entity.GroupCode;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonCodeRepository extends JpaRepository<CommonCode, String> {

    Optional<List<CommonCode>> findAllByGroupCode(GroupCode groupCode);
}
