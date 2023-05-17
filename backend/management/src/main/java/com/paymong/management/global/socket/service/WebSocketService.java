package com.paymong.management.global.socket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.management.global.code.WebSocketCode;
import com.paymong.management.global.socket.dto.PointDto;
import com.paymong.management.mong.dto.MapCodeWsDto;
import com.paymong.management.mong.dto.SendPointResDto;
import com.paymong.management.mong.dto.SendThingsResDto;
import com.paymong.management.status.dto.MongStatusDto;
import com.paymong.management.global.socket.dto.MongSocketDto;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.global.socket.dto.ThingsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final ObjectMapper objectMapper;

    private Map<Long, List<MongSocketDto>> members;
    @PostConstruct
    private void init(){
        members = new HashMap<>();
    }

    public void connect(MongSocketDto mongSocketDto){

        if(members.containsKey(mongSocketDto.getMemberId())){
            log.info("해당 id에 세션을 추가합니다. memberId : {} 세션 개수 : {}", mongSocketDto.getMemberId(), members.get(mongSocketDto.getMemberId()).size()+1);
            members.get(mongSocketDto.getMemberId()).add(mongSocketDto);
        }else{
            log.info("새로운 세션을 등록합니다. {}", mongSocketDto.getMemberId());
            List<MongSocketDto> list = new ArrayList<>();
            list.add(mongSocketDto);
            members.put(mongSocketDto.getMemberId(), list);
        }
    }

    public void disconnect(MongSocketDto mongSocketDto){
        if(members.containsKey(mongSocketDto.getMemberId())){
            log.info("세션을 삭제합니다. memberId : {} 세션 개수 : {}", mongSocketDto.getMemberId(), members.get(mongSocketDto.getMemberId()).size() -1);
            if(members.get(mongSocketDto.getMemberId()).size() == 1){
                members.remove(mongSocketDto.getMemberId());
            }else{
                members.get(mongSocketDto.getMemberId()).removeIf( s -> s.getSession().getId().equals(mongSocketDto.getSession().getId()));
            }

        }else{
            log.info("매칭되는 세션이 없습니다.");
        }
    }

    public void sendStatus(Mong mong, WebSocketCode webSocketCode) {
        try {
            if(!members.containsKey(mong.getMemberId())){
                log.info("{}와 연결된 소켓이 없습니다.", mong.getMemberId());
            }else{
                TextMessage message = new TextMessage(objectMapper.writeValueAsString(new MongStatusDto(mong, webSocketCode)));
                log.info("연결된 세션수 : {} memberId : {}",members.get(mong.getMemberId()).size(), mong.getMemberId());

                members.get(mong.getMemberId()).stream()
                        .filter(s -> s.getSession().isOpen())
                        .forEach(s ->
                        {
                            try {
                                log.info("{}에 상태 메세지를 보냅니다.", mong.getMemberId());
                                s.getSession().sendMessage(message);
                            } catch (IOException e) {
                                log.info("응 못보내");
                            }
                        });
                members.get(mong.getMemberId()).removeIf(s-> !s.getSession().isOpen());

            }
        }catch (IOException e){
            log.info("메세지 생성 실패");
        }

    }

    public void sendMap(MapCodeWsDto mapCodeWsDto, WebSocketCode webSocketCode) {
        try {
            if(!members.containsKey(mapCodeWsDto.getMemberId())){
                log.info("{}와 연결된 소켓이 없습니다.", mapCodeWsDto.getMemberId());
            }else{
                TextMessage message = new TextMessage(objectMapper.writeValueAsString(new MongStatusDto(mapCodeWsDto, webSocketCode)));
                log.info("연결된 세션수 : {} memberId : {}",members.get(mapCodeWsDto.getMemberId()).size(), mapCodeWsDto.getMemberId());
                members.get(mapCodeWsDto.getMemberId()).stream()
                        .filter(s -> s.getSession().isOpen())
                        .forEach(s ->
                        {
                            try {
                                log.info("{}에 맵 메세지를 보냅니다.", mapCodeWsDto.getMemberId());
                                s.getSession().sendMessage(message);
                            } catch (IOException e) {
                                log.info("응 못보내");
                            }
                        });
                members.get(mapCodeWsDto.getMemberId()).removeIf(s-> !s.getSession().isOpen());
            }
        }catch (IOException e){
            log.info("메세지 생성 실패");
        }

    }

    public void sendThings(SendThingsResDto sendThingsResDto, WebSocketCode webSocketCode) {
        try {
            if(!members.containsKey(sendThingsResDto.getMemberId())){
                log.info("{}와 연결된 소켓이 없습니다.", sendThingsResDto.getMemberId());
            }else{
                TextMessage message = new TextMessage(objectMapper.writeValueAsString(new ThingsDto(sendThingsResDto, webSocketCode)));
                log.info("연결된 세션수 : {} memberId : {}",members.get(sendThingsResDto.getMemberId()).size(), sendThingsResDto.getMemberId());
                members.get(sendThingsResDto.getMemberId()).stream()
                        .filter(s -> s.getSession().isOpen())
                        .forEach(s ->
                        {
                            try {
                                log.info("{}에 띵스 메세지를 보냅니다.", sendThingsResDto.getMemberId());
                                s.getSession().sendMessage(message);
                            } catch (IOException e) {
                                log.info("응 못보내");
                            }
                        });
                members.get(sendThingsResDto.getMemberId()).removeIf(s-> !s.getSession().isOpen());
            }
        }catch (IOException e){
            log.info("메세지 생성 실패");
        }

    }

    public void sendPoint(SendPointResDto sendPointResDto, WebSocketCode webSocketCode) {
        try {
            if(!members.containsKey(sendPointResDto.getMemberId())){
                log.info("{}와 연결된 소켓이 없습니다.", sendPointResDto.getMemberId());
            }else{
                TextMessage message = new TextMessage(objectMapper.writeValueAsString(new PointDto(sendPointResDto, webSocketCode)));
                log.info("연결된 세션수 : {} meberId : {}",members.get(sendPointResDto.getMemberId()).size(), sendPointResDto.getMemberId());
                members.get(sendPointResDto.getMemberId()).stream()
                        .filter(s -> s.getSession().isOpen())
                        .forEach(s ->
                        {
                            try {
                                log.info("{}에 포인트 메세지를 보냅니다.", sendPointResDto.getMemberId());
                                s.getSession().sendMessage(message);
                            } catch (IOException e) {
                                log.info("응 못보내");
                            }
                        });
                members.get(sendPointResDto.getMemberId()).removeIf(s-> !s.getSession().isOpen());
            }
        }catch (IOException e){
            log.info("메세지 생성 실패");
        }

    }

}
