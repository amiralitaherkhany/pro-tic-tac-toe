package com.amirali_apps.tictactoe.ui.components

import android.view.MotionEvent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MiniCustomButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    var isPressed by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .size(38.dp)
            .pointerInteropFilter { event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isPressed = true
                    }

                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        isPressed = false
                        onClick()
                    }
                }
                true
            },
    ) {
        Surface(
            modifier = Modifier
                .size(36.dp)
                .align(if (isPressed) Alignment.Center else Alignment.BottomStart)
        ) { }
        Box(
            modifier = Modifier
                .size(36.dp)
                .align(if (isPressed) Alignment.Center else Alignment.TopEnd)
                .background(MaterialTheme.colorScheme.secondary)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                ),
        ) {
            Surface(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary,
            ) {
                content()
            }
        }
    }
}