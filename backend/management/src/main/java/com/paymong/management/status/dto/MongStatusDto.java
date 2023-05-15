package com.paymong.management.status.dto;

import com.paymong.management.global.code.WebSocketCode;
import com.paymong.management.mong.dto.MapCodeDto;
import com.paymong.management.mong.dto.MapCodeWsDto;
import com.paymong.management.mong.dto.SendThingsReqDto;
import com.paymong.management.mong.entity.Mong;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MongStatusDto {
    private String code;
    private String message;
    private String mapCode;
    private String mongCode;
    private String stateCode;
    private Integer weight;
    private Double health;
    private Double satiety;
    private Double strength;
    private Double sleep;
    private Integer poopCount;


    public MongStatusDto(WebSocketCode webSocketCode){
        this.code = webSocketCode.getCode();
        this.message = webSocketCode.getMessage();
    }
    public MongStatusDto(MapCodeWsDto mapCodeWsDto, WebSocketCode webSocketCode){
        this.code = webSocketCode.getCode();
        this.message = webSocketCode.getMessage();
        this.mapCode = mapCodeWsDto.getMapCode();
    }
    public MongStatusDto(Mong mong, WebSocketCode webSocketCode){
        this.code = webSocketCode.getCode();
        this.message = webSocketCode.getMessage();
        this.stateCode = mong.getStateCode();
        this.mongCode = mong.getCode();
        this.weight = mong.getWeight();
        this.poopCount = mong.getPoopCount();
        this.mapCode = "MP000";

        int level = Integer.parseInt(mong.getCode().substring(2, 3));

        Double health = 0.0;
        Double satiety = 0.0;
        Double strength = 0.0;
        Double sleep = 0.0;

        if (level == 1) {
            health = mong.getHealth() / 20.0;
            satiety = mong.getSatiety() / 20.0;
            strength = mong.getStrength() / 20.0;
            sleep = mong.getSleep() / 20.0;
        } else if (level == 2) {
            health = mong.getHealth() / 30.0;
            satiety = mong.getSatiety() / 30.0;
            strength = mong.getStrength() / 30.0;
            sleep = mong.getSleep() / 30.0;

        } else if (level == 3) {
            health = mong.getHealth() / 40.0;
            satiety = mong.getSatiety() / 40.0;
            strength = mong.getStrength() / 40.0;
            sleep = mong.getSleep() / 40.0;
        }
        this.health = Math.round(health * 100.0) / 100.0;
        this.satiety = Math.round(satiety * 100.0) / 100.0;
        this.strength = Math.round(strength * 100.0) / 100.0;
        this.sleep = Math.round(sleep * 100.0) / 100.0;
    }
}
