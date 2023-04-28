package com.paymong.management.mong.service;

import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.mong.vo.AddMongReqVo;
import com.paymong.management.mong.vo.AddMongResVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Status;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MongService {
    private final MongRepository mongRepository;

    @Transactional
    public AddMongResVo addMong(AddMongReqVo addMongReqVo) throws Exception{

        // mong의 memberId 받아오기
//        mong.setMemberId(1L);

        // mong의 공통 코드 받아오기
        List<String> mongCodes = new ArrayList<>();
        mongCodes.add("000");
        mongCodes.add("001");
        mongCodes.add("002");
        mongCodes.add("003");
        mongCodes.add("004");
        mongCodes.add("005");

        // 6개의 알중에 랜덤 적용
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int r = random.nextInt(6);

        Mong mong = Mong.builder()
                .name(addMongReqVo.getName())
                .memberId(1L)
                .code(mongCodes.get(r))
                .sleepStart(addMongReqVo.getSleepStart())
                .sleepEnd(addMongReqVo.getSleepEnd())
                .build();

        Mong newMong = mongRepository.save(mong);

        AddMongResVo addMongResVo = new AddMongResVo(newMong);
        // 무슨 이유인진 몰라도 null로 처리됨..
        addMongResVo.setWeight(5);
        addMongResVo.setBorn(LocalDate.now());

        return addMongResVo;
    }
}
