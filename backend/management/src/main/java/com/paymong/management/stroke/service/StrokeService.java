package com.paymong.management.stroke.service;

import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.stroke.vo.StrokeMongReqVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class StrokeService {
    private final MongRepository mongRepository;

    @Transactional
    public void strokeMong(StrokeMongReqVo strokeMongReqVo) throws Exception{
        Mong mong = mongRepository.findByMongId(strokeMongReqVo.getMongId())
                .orElseThrow(() -> new NotFoundMongException());
        mong.setStrokeCount(mong.getStrokeCount() + 1);

    }
}
