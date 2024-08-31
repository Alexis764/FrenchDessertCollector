package edu.ws2024.a01.am.feature.game

import edu.ws2024.a01.am.R
import javax.inject.Inject

class GameProvider @Inject constructor() {

    operator fun invoke(maxPositionX: Float): List<GameItemModel> {
        val amountItems = (10..16).random()

        val itemList = listOf(
            GameItemModel(image = R.drawable.baguette, score = 10),
            GameItemModel(image = R.drawable.macaron, score = 20),
            GameItemModel(image = R.drawable.puff, score = 30, time = 30),
            GameItemModel(image = R.drawable.fishbone, score = 20),
            GameItemModel(image = R.drawable.bone, score = 10)
        )
        itemList.forEach {
            val ejeXRandom = (0..(maxPositionX.toInt() - 50)).random().toFloat()
            it.positionX = ejeXRandom
        }

        val randomItemList = mutableListOf<GameItemModel>()
        randomItemList.addAll(itemList)

        while (randomItemList.size < amountItems) {
            val ejeXRandom = (0..(maxPositionX.toInt() - 50)).random().toFloat()
            randomItemList.add(itemList.random().copy(positionX = ejeXRandom))
        }

        return randomItemList
    }

}