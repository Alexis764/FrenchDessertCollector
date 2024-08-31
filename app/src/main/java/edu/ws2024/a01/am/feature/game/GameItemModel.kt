package edu.ws2024.a01.am.feature.game

data class GameItemModel(
    val image: Int,
    val score: Int,
    var positionX: Float = 0f,
    val positionY: Float = 0f,
    val time: Int = 0
)
