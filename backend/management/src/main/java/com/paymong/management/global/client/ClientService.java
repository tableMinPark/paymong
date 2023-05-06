package com.paymong.management.global.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.management.global.code.ManagementStateCode;
import com.paymong.management.global.dto.AddPointDto;
import com.paymong.management.global.dto.CommonCodeDto;
import com.paymong.management.global.dto.FindCommonCodeDto;
import com.paymong.management.global.exception.GatewayException;
import com.paymong.management.global.exception.UnknownException;
import com.paymong.management.global.response.ErrorResponse;
import com.paymong.management.mong.dto.FindRandomEggDto;
import com.paymong.management.status.dto.FindStatusReqDto;
import com.paymong.management.status.dto.FindStatusResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientService {
    private final MemberServiceClient memberServiceClient;
    private final CommonServiceClient commonServiceClient;

    public void addPoint(String memberId, AddPointDto addPayDto) throws GatewayException {
        try {
            memberServiceClient.addPoint(memberId, addPayDto);
        }catch (Exception e){
            throw new GatewayException();
        }
    }

    public FindStatusResDto findStatus(FindStatusReqDto findStatusReqDto) throws GatewayException {
        try {
            ObjectMapper om = new ObjectMapper();
            FindStatusResDto response = om.convertValue(commonServiceClient.findStatus(findStatusReqDto).getBody(), FindStatusResDto.class);
            return response;
        } catch (Exception e){
            throw new GatewayException();
        }
    }

    public CommonCodeDto findCommonCode(FindCommonCodeDto findCommonCodeDto) throws GatewayException {
        try {
            ObjectMapper om = new ObjectMapper();
            CommonCodeDto response = om.convertValue(commonServiceClient.findCommonCode(findCommonCodeDto).getBody(), CommonCodeDto.class);
            return response;
        }catch (Exception e){
            throw new GatewayException();
        }
    }

}
