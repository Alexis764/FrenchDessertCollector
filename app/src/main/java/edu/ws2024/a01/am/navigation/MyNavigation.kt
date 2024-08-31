package edu.ws2024.a01.am.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.ws2024.a01.am.feature.game.GameScreen
import edu.ws2024.a01.am.feature.game_over.GameOverScreen
import edu.ws2024.a01.am.feature.home.HomeScreen
import edu.ws2024.a01.am.feature.ranking.RankingScreen
import edu.ws2024.a01.am.feature.username.UsernameScreen

@Composable
fun MyNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen.route,
        modifier = modifier
    ) {
        composable(Routes.HomeScreen.route) {
            HomeScreen(navController)
        }

        composable(Routes.UsernameScreen.route) {
            UsernameScreen(navController)
        }

        composable(
            Routes.GameScreen.route,
            arguments = listOf(
                navArgument("username") { type = NavType.StringType }
            )
        ) {
            val username = it.arguments?.getString("username").orEmpty()
            GameScreen(username, navController)
        }

        composable(
            Routes.GameOverScreen.route,
            arguments = listOf(
                navArgument("baguettePoints") { type = NavType.IntType },
                navArgument("macaronPoints") { type = NavType.IntType },
                navArgument("puffPoints") { type = NavType.IntType },
                navArgument("fihshbonePoints") { type = NavType.IntType },
                navArgument("bonePoints") { type = NavType.IntType },
                navArgument("secondsTime") { type = NavType.IntType },
                navArgument("totalScore") { type = NavType.IntType },
                navArgument("username") { type = NavType.StringType }
            )
        ) {
            val baguettePoints = it.arguments?.getInt("baguettePoints") ?: 0
            val macaronPoints = it.arguments?.getInt("macaronPoints") ?: 0
            val puffPoints = it.arguments?.getInt("puffPoints") ?: 0
            val fihshbonePoints = it.arguments?.getInt("fihshbonePoints") ?: 0
            val bonePoints = it.arguments?.getInt("bonePoints") ?: 0
            val secondsTime = it.arguments?.getInt("secondsTime") ?: 0
            val totalScore = it.arguments?.getInt("totalScore") ?: 0
            val username = it.arguments?.getString("username").orEmpty()

            GameOverScreen(
                baguettePoints,
                macaronPoints,
                puffPoints,
                fihshbonePoints,
                bonePoints,
                secondsTime,
                totalScore,
                username,
                navController
            )
        }

        composable(Routes.RankingScreen.route) {
            RankingScreen(navController)
        }
    }
}