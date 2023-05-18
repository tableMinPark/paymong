package com.paymong.management.global.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.management.global.dto.*;
import com.paymong.management.global.exception.GatewayException;
import com.paymong.management.status.dto.FindStatusReqDto;
import com.paymong.management.status.dto.FindStatusResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientService {
    private final MemberServiceClient memberServiceClient;
    private final CommonServiceClient commonServiceClient;
    private final CollectServiceClient collectServiceClient;

    public void addMong(String memberid, FindCommonCodeDto findCommonCodeDto) throws GatewayException {
        try {
            collectServiceClient.addMong(memberid, findCommonCodeDto);
        }catch (Exception e){
            throw new GatewayException();
        }
    }

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

    public CommonCodeDto findMongLevelCode(FindMongLevelCodeDto findMongLevelCodeDto) throws GatewayException{
        try {
            ObjectMapper om = new ObjectMapper();
            CommonCodeDto response = om.convertValue(commonServiceClient.findMongLevelCode(findMongLevelCodeDto).getBody(), CommonCodeDto.class);
            return response;
        }catch (Exception e){
            throw new GatewayException();
        }
    }

    public TotalPointDto findTotalPay(String memberId, FindTotalPayDto findTotalPayDto) throws GatewayException {
        try {
            ObjectMapper om = new ObjectMapper();
            TotalPointDto response = om.convertValue(memberServiceClient.findTotalPay(memberId, findTotalPayDto).getBody(), TotalPointDto.class);
            return response;
        } catch (Exception e){
            throw new GatewayException();
        }
    }
}
