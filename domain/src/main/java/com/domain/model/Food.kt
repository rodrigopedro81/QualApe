package com.domain.model

data class Food(
    val ownerId: String = "",
    val title: String = "",
    val description: String = "",
    val value: Double = 0.0,
    val type: FoodType = FoodType.MASSA
)

enum class FoodType {
    DOCE,
    MASSA,
    BOLO,
    TORTA,
    PIZZA,
    HAMBURGUER,
    ESFIRRA,
    CACHORRO_QUENTE
}
