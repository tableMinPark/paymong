package com.paymong.data.dto.request

import com.paymong.common.code.MessageType

data class BattleMessageReqDto(
    val type: MessageType,
    val characterId: Long,
    val battleRoomId: String,
    val order: String
)
