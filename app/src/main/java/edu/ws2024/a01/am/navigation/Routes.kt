package edu.ws2024.a01.am.navigation

sealed class Routes(val route: String) {

    data object HomeScreen : Routes("HomeScreen")

    data object UsernameScreen : Routes("UsernameScreen")

    data object GameScreen : Routes("GameScreen/{username}") {
        fun createRoute(username: String) = "GameScreen/$username"
    }

    data object GameOverScreen : Routes(
        "GameOverScreen/{baguettePoints}/{macaronPoints}/{puffPoints}/{fihshbonePoints}/{bonePoints}/{secondsTime}/{totalScore}/{username}"
    ) {
        fun createRoute(
            baguettePoints: Int,
            macaronPoints: Int,
            puffPoints: Int,
            fihshbonePoints: Int,
            bonePoints: Int,
            secondsTime: Int,
            totalScore: Int,
            username: String
        ) = "GameOverScreen/$baguettePoints/$macaronPoints/$puffPoints/$fihshbonePoints/$bonePoints/$secondsTime/$totalScore/$username"
    }

    data object RankingScreen : Routes("RankingScreen")

}