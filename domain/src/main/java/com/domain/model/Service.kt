package com.domain.model

import com.domain.enums.ServiceType

data class Service(
    val ownerId: String = "",
    val title: String = "",
    val description: String = "",
    val value: Double = 0.0,
    val type: ServiceType = ServiceType.SERVIÇO
)
