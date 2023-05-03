package com.paymong.data.model.request

import com.paymong.common.code.MessageType

data class BattleConnectReqDto(
    val type: MessageType,
    val characterId: Long,
    val latitude: Double,
    val longitude: Double,
)

data class BattleMessageReqDto(
    val type: MessageType,
    val characterId: Long,
    val battleRoomId: String,
    val order: String
)
