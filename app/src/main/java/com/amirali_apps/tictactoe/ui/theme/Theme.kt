package com.amirali_apps.tictactoe.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.amirali_apps.tictactoe.utils.AppLocaleManager

private val DarkColorScheme = darkColorScheme(
    background = backgroundColor,
    primary = primary,
    secondary = secondary,
    secondaryContainer = secondaryBackground,
)
//private val LightColorScheme = lightColorScheme(
//)
@Composable
fun TicTacToeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = getTypography(),
        content = content,
    )
}

@Composable
fun getTypography(): Typography {
    return if (AppLocaleManager().getLanguageCode(LocalContext.current) == "en") enTypography else faTypography
}
