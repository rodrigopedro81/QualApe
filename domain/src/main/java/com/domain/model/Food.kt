package com.domain.model

import com.domain.enums.FoodType

data class Food(
    val ownerId: String = "",
    val title: String = "",
    val description: String = "",
    val value: Double = 0.0,
    val type: FoodType = FoodType.COMIDA
)

