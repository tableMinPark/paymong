package com.paymong.member.background.service;

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

    public void findMap() throws RuntimeException{
    	
    }
    
    public void setMymap(Long memberId, String mapCode) throws RuntimeException {
    	Mymap mymap = mymapRepository.getByMemberId(memberId).orElseThrow(() -> new NotFoundMymapException());
    	mymap.setMapCode(mapCode);
    	LocalDateTime now = LocalDateTime.now();
    	mymap.setUpdDt(now);
    }

}
