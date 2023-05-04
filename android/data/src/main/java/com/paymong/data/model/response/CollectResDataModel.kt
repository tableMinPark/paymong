package com.paymong.data.model.response

data class MapResDto(
    val isOpen: Boolean,
    val name: String,
    val mapCode: String
)

data class MongList(
    val isOpen: Boolean,
    val name: String,
    val characterCode: String
)

data class MongResDto(
    val map: List<MongList>
)