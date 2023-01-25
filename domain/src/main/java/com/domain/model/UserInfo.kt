package com.domain.model

data class UserInfo(
    val name: String = "",
    val email: String = "",
    val wpp: String = "",
    val block: String = "",
    val apartment: String = "",
    var foodIdList: List<String> = listOf(),
    var serviceIdList: List<String> = listOf(),
    var productIdList: List<String> = listOf()
)