package com.paymong.battle.battle.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.battle.battle.dto.request.AddPointReqDto;
import com.paymong.battle.battle.dto.request.FindMongBattleReqDto;
import com.paymong.battle.battle.dto.request.FindMongMasterReqDto;
import com.paymong.battle.battle.dto.response.BattleMessageResDto;
import com.paymong.battle.battle.dto.response.FindMongBattleResDto;
import com.paymong.battle.battle.dto.response.FindMongMasterResDto;
import com.paymong.battle.battle.vo.common.BattleLog;
import com.paymong.battle.battle.vo.common.BattleRoom;
import com.paymong.battle.battle.vo.common.MongStats;
import com.paymong.battle.global.client.InformationServiceClient;
import com.paymong.battle.global.client.MemberServiceClient;
import com.paymong.battle.global.exception.NotFoundException;
import com.paymong.battle.global.redis.LocationRepository;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Service
@RequiredArgsConstructor
public class BattleService {

    private static final Integer money = 500;

    private final InformationServiceClient informationServiceClient;
    private final MemberServiceClient memberServiceClient;
    private final LocationRepository locationRepository;
    private final ObjectMapper objectMapper;
    private Map<String, BattleRoom> battleRoomMap;

    @PostConstruct
    private void init() {
        locationRepository.removeAll();
        battleRoomMap = new LinkedHashMap<>();
    }

    public BattleRoom findBattleRoom(String battleRoomId) throws NotFoundException {
        return battleRoomMap.get(battleRoomId);
    }

    public void addBattleRoom(String battleRoomId, BattleRoom battleRoom) {
        battleRoomMap.put(battleRoomId, battleRoom);
    }

    public void removeBattleRoom(String battleRoomId) {
        battleRoomMap.remove(battleRoomId);
    }

    public BattleMessageResDto battleActive(Integer nowTurn, MongStats statsA, MongStats statsB, BattleLog battleLog) {

        /*
            - 몸무게 = 방어력  ///  근력 = 공격
            - 서로의 피를 알 수 있음
            - 받는 데미지 : 상대 데미지 * 100/(100+나의 방어력)
            - 주는 데미지 : 100 + 나의 공격력
            - 최대 10번 턴 제
            - 체력 500
        */

        String nextAttacker;
        Double damageA = 0.0;
        Double damageB = 0.0;

        if (nowTurn % 2 != 0) {
            // A 공격
            if (!isSameDirection(battleLog)) {
                // B가 A의 공격을 받음
                log.info("A 공격 : 다른방향");
                Double powerA = 100.0 + statsA.getStrength();
                Double defenseB = 100.0 + statsB.getWeight();
                damageB = powerA * 100 / defenseB;
            } else {
                log.info("A 공격 : 같은방향");
                // B가 A의 공격을 회피함
                damageA = 0.0;
                damageB = 0.0;
            }
            nextAttacker = "B";

        } else {
            // B 공격
            if (!isSameDirection(battleLog)) {
                // A가 B의 공격을 받음
                log.info("B 공격 : 다른방향");
                Double powerB = 100.0 + statsB.getStrength();
                Double defenseA = 100.0 + statsA.getWeight();
                damageA = powerB * 100 / defenseA;
            } else {
                // A가 B의 공격을 회피함
                log.info("B 공격 : 같은방향");
                damageA = 0.0;
                damageB = 0.0;
            }
            nextAttacker = "A";
        }
        return BattleMessageResDto.builder()
            .nextAttacker(nextAttacker)
            .damageA(damageA)
            .damageB(damageB)
            .build();
    }

    private boolean isSameDirection(BattleLog battleLog) {
        if (battleLog.getSelectA().equals(battleLog.getSelectB())) {
            return true;
        } else {
            return false;
        }
    }

    public MongStats findCharacterStats(Long mongId, Double defaultHealth) {
        ObjectMapper om = new ObjectMapper();
        FindMongBattleResDto findMongBattleResDto = om.convertValue(
            informationServiceClient.findMongBattle(FindMongBattleReqDto
                .builder()
                .mongId(mongId)
                .build()).getBody(), FindMongBattleResDto.class);

        return MongStats.builder()
            .mongId(findMongBattleResDto.getMongId())
            .health(defaultHealth)
            .strength(findMongBattleResDto.getStrength())
            .weight(findMongBattleResDto.getWeight())
            .build();
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            System.out.println(message);
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
            }
        } catch (IOException e) {
            log.error("세션 만료");
        }
    }

    public void earnMoney(Long mongId) {
        log.info("earnMoney Call : mongId - {}", mongId);
        ObjectMapper om = new ObjectMapper();
        FindMongMasterResDto findMongMasterResDto =
            om.convertValue(informationServiceClient.findMongMaster(new FindMongMasterReqDto(mongId)).getBody(),
                FindMongMasterResDto.class);

        AddPointReqDto addPointReqDto = AddPointReqDto.builder()
            .point(money)
            .content("배틀")
            .code("AT012")
            .build();

        memberServiceClient.addPoint(String.valueOf(findMongMasterResDto.getMemberId()), addPointReqDto);
    }

    public void spendMoney(Long mongId) {
        log.info("spendMoney Call : mongId - {}", mongId);
        ObjectMapper om = new ObjectMapper();
        FindMongMasterResDto findMongMasterResDto =
            om.convertValue(informationServiceClient.findMongMaster(new FindMongMasterReqDto(mongId)).getBody(),
                FindMongMasterResDto.class);

        AddPointReqDto addPointReqDto = AddPointReqDto.builder()
            .point(money*-1)
            .content("배틀")
            .code("AT012")
            .build();

        memberServiceClient.addPoint(String.valueOf(findMongMasterResDto.getMemberId()), addPointReqDto);
    }

    public void keepMoney(Long mongId){
        log.info("keepMoney Call : mongId - {}", mongId);
        ObjectMapper om = new ObjectMapper();
        FindMongMasterResDto findMongMasterResDto =
            om.convertValue(informationServiceClient.findMongMaster(new FindMongMasterReqDto(mongId)).getBody(),
                FindMongMasterResDto.class);

        // point history 에 0원이라고 기록
        AddPointReqDto addPointReqDto = AddPointReqDto.builder()
            .point(0)
            .content("배틀")
            .code("AT012")
            .build();

        memberServiceClient.addPoint(String.valueOf(findMongMasterResDto.getMemberId()), addPointReqDto);
    }
}
