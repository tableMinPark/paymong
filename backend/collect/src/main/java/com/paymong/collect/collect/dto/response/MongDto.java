package com.paymong.collect.collect.dto.response;

import com.paymong.collect.global.vo.response.CommonCodeVo;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MongDto {

    private Boolean isOpen;
    private String name;
    private String characterCode;

    public static MongDto of(CommonCodeVo commonCodeResVo) {
        return MongDto.builder()
            .isOpen(false)
            .name(commonCodeResVo.getName())
            .characterCode(commonCodeResVo.getCode())
            .build();
    }

    public MongDto isContain(List<String> mongCollect) {
        if (mongCollect.contains(this.characterCode)) {
            this.setIsOpen(true);
        } else {
            this.setIsOpen(false);
            this.setName(null);
            this.setCharacterCode(null);
        }
        return this;
    }

}
