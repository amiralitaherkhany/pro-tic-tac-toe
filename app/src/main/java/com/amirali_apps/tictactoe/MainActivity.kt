package com.amirali_apps.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.amirali_apps.tictactoe.ui.AiLevel
import com.amirali_apps.tictactoe.ui.GameModeSelectionScreen
import com.amirali_apps.tictactoe.ui.GameScreen
import com.amirali_apps.tictactoe.ui.GameViewModel
import com.amirali_apps.tictactoe.ui.theme.TicTacToeTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = resources.configuration
        config.setLocale(Locale.ENGLISH)
        resources.updateConfiguration(
            config,
            resources.displayMetrics
        )
        enableEdgeToEdge()
        setContent {
            TicTacToeTheme {
                MyApp()
            }
        }
    }
}

enum class GameScreens {
    GameModeSelection,
    Game,
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = GameScreens.GameModeSelection.name
    ) {
        composable(GameScreens.GameModeSelection.name) { GameModeSelectionScreen(navController) }
        composable(
            "${GameScreens.Game.name}/{isPro}/{isAi}/{level}",
            arguments = listOf(
                navArgument("isPro") { type = NavType.BoolType },
                navArgument("isAi") { type = NavType.BoolType },
                navArgument("level") { type = NavType.IntType },
            )
        ) { backStackEntry ->
            GameScreen(
                gameViewModel = GameViewModel(
                    isPro = backStackEntry.arguments?.getBoolean("isPro")!!,
                    isAi = backStackEntry.arguments?.getBoolean("isAi")!!,
                    aiLevel = if (backStackEntry.arguments?.getBoolean("isAi")!!) AiLevel.entries[backStackEntry.arguments?.getInt("level")!!] else null
                ),
                navController = navController,
            )
        }
    }
}


