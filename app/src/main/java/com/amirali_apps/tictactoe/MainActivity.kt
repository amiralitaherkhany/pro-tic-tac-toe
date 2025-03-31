package com.amirali_apps.tictactoe

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.amirali_apps.tictactoe.models.UpdateModel
import com.amirali_apps.tictactoe.network.RetrofitClient
import com.amirali_apps.tictactoe.ui.components.UpdateDialog
import com.amirali_apps.tictactoe.ui.game.AiLevel
import com.amirali_apps.tictactoe.ui.game.GameScreen
import com.amirali_apps.tictactoe.ui.game.GameViewModel
import com.amirali_apps.tictactoe.ui.game_mode_selection.GameModeSelectionScreen
import com.amirali_apps.tictactoe.ui.navigation.GameScreens
import com.amirali_apps.tictactoe.ui.theme.TicTacToeTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

object LocaleHelper {
    fun wrap(context: Context): Context {
        val locale = Locale.ENGLISH
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }
}

class MainActivity : ComponentActivity() {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            TicTacToeTheme {
                MyApp()
                var showDialog by remember { mutableStateOf(false) }
                var response by remember { mutableStateOf<UpdateModel?>(null) }
                val context = LocalContext.current
                LaunchedEffect(null) {
                    withContext(Dispatchers.IO) {
                        try {
                            response = RetrofitClient.retrofitService.checkForUpdate()
                            val latestVersion = response?.latestVersion ?: ""
                            if (latestVersion.isNotEmpty() && latestVersion != BuildConfig.VERSION_NAME) {
                                showDialog = true
                            }
                        } catch (_: Exception) {
                        }
                    }
                }


                UpdateDialog(
                    showDialog = showDialog,
                    onDismiss = { showDialog = false },
                    onDownloadClick = {
                        val downloadUrl = response?.downloadUrl ?: ""
                        if (downloadUrl.isNotEmpty()) {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                downloadUrl.toUri()
                            )
                            context.startActivity(intent)
                        }
                        showDialog = false
                    }
                )
            }
        }
    }
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


