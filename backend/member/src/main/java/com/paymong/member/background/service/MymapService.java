package com.paymong.member.background.service;

import com.paymong.member.background.dto.response.FindMymapResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.paymong.member.background.entity.Mymap;
import com.paymong.member.background.repository.MymapRepository;
import com.paymong.member.global.exception.NotFoundMymapException;


@Slf4j
@Service
@RequiredArgsConstructor
public class MymapService {
	
	private final MymapRepository mymapRepository;

    public FindMymapResDto findMymap(String memberIdStr) throws RuntimeException{
		Long memberId = Long.parseLong(memberIdStr);
		LocalDateTime aHourAgo = LocalDateTime.now().minusHours(1L);
		System.out.println("한시간전:"+aHourAgo);
		Mymap mymap = mymapRepository.getByMemberId(memberId).orElseThrow(() -> new NotFoundMymapException());
		if( mymap.getUpdDt().isBefore(aHourAgo )){
			mymap.setMapCode("MP000");
			mymap.setUpdDt(LocalDateTime.now());
		}
		FindMymapResDto ret = new FindMymapResDto(mymap.getMapCode());
		return ret;
    }
    
    public void setMymap(Long memberId, String mapCode) throws RuntimeException {
    	Mymap mymap = mymapRepository.getByMemberId(memberId).orElseThrow(() -> new NotFoundMymapException());
    	mymap.setMapCode(mapCode);
    	LocalDateTime now = LocalDateTime.now();
    	mymap.setUpdDt(now);

    }

}
