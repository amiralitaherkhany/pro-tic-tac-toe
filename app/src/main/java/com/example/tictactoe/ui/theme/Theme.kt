package com.example.tictactoe.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = backgroundColor,
    primary = primary,
    secondary = secondary,
    secondaryContainer = secondaryBackground,
    )
private val LightColorScheme = lightColorScheme(
)

@Composable
fun TicTacToeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content,
    )
}