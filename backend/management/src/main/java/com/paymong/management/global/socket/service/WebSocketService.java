package com.paymong.management.global.socket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.management.global.socket.dto.MongStatusDto;
import com.paymong.management.global.socket.dto.MongSocketDto;
import com.paymong.management.mong.entity.Mong;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final ObjectMapper objectMapper;

    private Map<Long, MongSocketDto> members;
    @PostConstruct
    private void init(){
        members = new HashMap<>();
    }

    public void connect(MongSocketDto mongSocketDto){

        if(members.containsKey(mongSocketDto.getMemberId())){
            log.info("이미 존재하는 session입니다.");
        }else{
            log.info("새로운 세션을 등록합니다. {}", mongSocketDto.getMemberId());
            members.put(mongSocketDto.getMemberId(), mongSocketDto);
        }
    }

    public void disconnect(MongSocketDto mongSocketDto){
        if(members.containsKey(mongSocketDto.getMemberId())){
            log.info("세션을 삭제합니다. {}", mongSocketDto.getMemberId());
            members.remove(mongSocketDto.getMemberId());
        }else{
            log.info("매칭되는 세션이 없습니다.");
        }
    }

    public void sendTest(Mong mong) {
        try {
            WebSocketSession session = members.get(mong.getMemberId()).getSession();
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(new MongStatusDto(mong))));
        }catch (IOException e){
            log.info("응 못보내");
        }

    }

}
