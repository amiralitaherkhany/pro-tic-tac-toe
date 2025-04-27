package com.amirali_apps.tictactoe

import AppLocaleManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.core.content.edit
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
import com.amirali_apps.tictactoe.ui.game.GameScreen
import com.amirali_apps.tictactoe.ui.game_mode_selection.GameModeSelectionScreen
import com.amirali_apps.tictactoe.ui.navigation.GameScreens
import com.amirali_apps.tictactoe.ui.theme.TicTacToeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun attachBaseContext(newBase: Context) {
        val currentLang = AppLocaleManager().getLanguageCode(newBase)
        newBase.resources.configuration.setLocale(Locale.forLanguageTag(currentLang))

        applyOverrideConfiguration(newBase.resources.configuration)
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var keepOnScreenCondition = mutableStateOf(true)
        installSplashScreen().setKeepOnScreenCondition {
            keepOnScreenCondition.value
        }
        enableEdgeToEdge()
        setContent {
            when (BuildConfig.FLAVOR) {
                "myket" -> {
                    Log.d(
                        "MainActivity",
                        "onCreate: myket"
                    )
                }

                "bazaar" -> {
                    Log.d(
                        "MainActivity",
                        "onCreate: bazaar"
                    )
                }
            }
            LaunchedEffect(null) {
                delay(1000L)
                keepOnScreenCondition.value = false
            }
            TicTacToeTheme {
                AppNavHost()
                var showUpdateDialog by remember { mutableStateOf(false) }
                var showRateDialog by remember { mutableStateOf(increaseLaunchCount(this)) }
                var response by remember { mutableStateOf<UpdateModel?>(null) }
                val context = LocalContext.current
                LaunchedEffect(null) {
                    withContext(Dispatchers.IO) {
                        try {
                            response = RetrofitClient.retrofitService.checkForUpdate()
                            val latestVersion = response?.latestVersion ?: ""
                            if (latestVersion.isNotEmpty() && latestVersion != BuildConfig.VERSION_NAME) {
                                showUpdateDialog = true
                            }
                        } catch (_: Exception) {
                        }
                    }
                }


                UpdateDialog(
                    showDialog = showUpdateDialog,
                    onDismiss = { showUpdateDialog = false },
                    onDownloadClick = {
                        val downloadUrl = response?.downloadUrl ?: ""
                        if (downloadUrl.isNotEmpty()) {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                downloadUrl.toUri()
                            )
                            context.startActivity(intent)
                        }
                        showUpdateDialog = false
                    }
                )
                RateDialog(
                    showDialog = showRateDialog,
                    onDismiss = {
                        showRateDialog = false
                    },
                    onRateClick = {
                        when (BuildConfig.FLAVOR) {
                            "myket" -> {
                                val intent = context.packageManager
                                    .getLaunchIntentForPackage("ir.mservices.market")

                                if (intent != null) {
                                    val url = "myket://comment?id=$packageName"
                                    val intent = Intent()
                                    intent.action = Intent.ACTION_VIEW
                                    intent.data = url.toUri()
                                    context.startActivity(intent)
                                } else {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        ("https://myket.ir").toUri()
                                    )
                                    context.startActivity(intent)
                                }
                            }

                            "bazaar" -> {
                                val intent = context.packageManager
                                    .getLaunchIntentForPackage("com.farsitel.bazaar")
                                if (intent != null) {
                                    val intent = Intent(Intent.ACTION_EDIT)
                                    intent.data = ("bazaar://details?id=$packageName").toUri()
                                    intent.setPackage("com.farsitel.bazaar")
                                    context.startActivity(intent)
                                } else {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        ("https://cafebazaar.ir").toUri()
                                    )
                                    context.startActivity(intent)
                                }
                            }
                        }
                        showRateDialog = false
                    },
                )
            }
        }
    }
}

@Composable
fun AppNavHost() {
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
                navController = navController,
            )
        }
    }
}

fun increaseLaunchCount(
    context: Context,
): Boolean {
    val sharedPreferences = context.getSharedPreferences(
        "app_prefs",
        Context.MODE_PRIVATE
    )
    val launchCount = sharedPreferences.getInt(
        "launch_count",
        0
    )
    val isIncrementing = sharedPreferences.getBoolean(
        "is_incrementing",
        true
    )
    if (isIncrementing) {
        val newLaunchCount = launchCount + 1
        sharedPreferences.edit() {
            putInt(
                "launch_count",
                newLaunchCount
            )
        }
        if (newLaunchCount == 3) {
            sharedPreferences.edit {
                putBoolean(
                    "is_incrementing",
                    false
                )
            }
            return true
        }
    }

    return false
}

@Composable
fun RateDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onRateClick: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            title = {
                Text(
                    text = stringResource(R.string.do_you_enjoy_the_game),
                    style = MaterialTheme.typography.bodyLarge.plus(
                        TextStyle(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                )
            },
            text = {
                Text(
                    text = stringResource(
                        R.string.rate_us_on,
                        when (BuildConfig.FLAVOR) {
                            "myket" -> stringResource(R.string.myket)
                            "bazaar" -> stringResource(R.string.bazaar)
                            else -> stringResource(R.string.store)
                        }
                    ),
                    style = MaterialTheme.typography.bodyMedium.plus(
                        TextStyle(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                )
            },
            confirmButton = {
                Button(onClick = onRateClick) {
                    Text(
                        stringResource(R.string.rate),
                        style = MaterialTheme.typography.bodyMedium.plus(
                            TextStyle(
                                color = MaterialTheme.colorScheme.secondary
                            )
                        )
                    )
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text(
                        stringResource(R.string.never),
                        style = MaterialTheme.typography.bodyMedium.plus(
                            TextStyle(
                                color = MaterialTheme.colorScheme.secondary
                            )
                        )
                    )
                }
            }
        )
    }
}
