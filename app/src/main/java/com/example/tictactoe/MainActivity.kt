package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tictactoe.ui.GameModeSelectionScreen
import com.example.tictactoe.ui.GameScreen
import com.example.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TicTacToeTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = "gameModeSelection"
    ) {
        composable("gameModeSelection") { GameModeSelectionScreen(navController) }
        composable(
            "gameScreen/{isPro}/{isAi}",
            arguments = listOf(
                navArgument("isPro") { type = NavType.BoolType },
                navArgument("isAi") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            GameScreen(
                gameViewModel = GameViewModel(
                    isPro = backStackEntry.arguments?.getBoolean("isPro")!!,
                    isAi = backStackEntry.arguments?.getBoolean("isAi")!!,
                ),
                navController = navController,
            )
        }
    }
}
