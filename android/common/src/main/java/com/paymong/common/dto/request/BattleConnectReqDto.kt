package com.paymong.common.dto.request

import com.paymong.common.code.MessageType

data class BattleConnectReqDto(
    val type: MessageType,
    val characterId: Long,
    val latitude: Double,
    val longitude: Double,
)
