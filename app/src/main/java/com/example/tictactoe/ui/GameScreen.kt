package com.example.tictactoe.ui

import android.view.MotionEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tictactoe.GameViewModel
import com.example.tictactoe.Move
import com.example.tictactoe.R
import com.example.tictactoe.ui.theme.accent1
import com.example.tictactoe.ui.theme.accent2
import com.example.tictactoe.ui.theme.accent3
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    gameViewModel: GameViewModel,
    navController: NavController,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            MainLayout(
                modifier = Modifier
                    .padding(innerPadding),
                viewModel = gameViewModel,
                navController = navController
            )

            AnimatedVisibility(
                visible = gameViewModel.isGameFinished,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { }
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.75f))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        gameViewModel.winnerTitle,
                        style = MaterialTheme.typography.headlineMedium.plus(
                            TextStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 50.sp,
                            )
                        ),
                        textAlign = TextAlign.Center,
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        MiniCustomButton(
                            onClick = {
                                CoroutineScope(Dispatchers.Main).launch {
                                    gameViewModel.resetGame()
                                }
                            },
                            icon = Icons.Filled.Replay,
                        )
                        Spacer(Modifier.width(24.dp))
                        MiniCustomButton(
                            onClick = {
                                navController.navigate("gameModeSelection") {
                                    popUpTo(0) { inclusive = true }
                                }
                            },
                            icon = Icons.Filled.Home,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MiniCustomButton(
    onClick: () -> Unit,
    icon: ImageVector,
    modifier: Modifier = Modifier
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
                Icon(
                    imageVector = icon,
                    contentDescription = "Replay",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun MainLayout(
    viewModel: GameViewModel,
    modifier: Modifier,
    navController: NavController,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Board(
            modifier = Modifier.align(Alignment.Center),
            viewModel = viewModel,
        )




        AnimatedVisibility(
            visible = viewModel.isTurnX.not() or viewModel.isAi,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxHeight(0.15f)
            ) {
                Text(
                    if (viewModel.isTurnX) (if (viewModel.isAi) "X" else "O") else "O",
                    style = MaterialTheme.typography.headlineMedium.plus(
                        TextStyle(
                            fontSize = 50.sp
                        )
                    ),
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    if (viewModel.isAi.not()) "Your Move" else (if (viewModel.isTurnX) "AI’s Move" else "Your Move"),
                    style = MaterialTheme.typography.bodyLarge.plus(
                        TextStyle(
                            fontSize = 20.sp,
                        )
                    ),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        if (viewModel.isAi.not()) {
            AnimatedVisibility(
                visible = viewModel.isTurnX,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .fillMaxHeight(0.15f)
                ) {
                    Text(
                        "Your Move",
                        style = MaterialTheme.typography.bodyLarge.plus(
                            TextStyle(
                                fontSize = 20.sp,
                            )
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.rotate(180f)
                    )

                    Text(
                        if (viewModel.isTurnX) "X" else (if (viewModel.isAi) "O" else "X"),
                        style = MaterialTheme.typography.headlineMedium.plus(
                            TextStyle(
                                fontSize = 50.sp
                            )
                        ),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    start = 24.dp,
                    top = 20.dp
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MiniCustomButton(
                icon = Icons.Filled.Close,
                onClick = {
                    navController.navigate("gameModeSelection") {
                        popUpTo(0) { inclusive = true }
                    }
                },
            )
            Spacer(Modifier.height(15.dp))
            MiniCustomButton(
                icon = Icons.Filled.Replay,
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        if (viewModel.isAi.not() or (viewModel.isAi and viewModel.isTurnX.not())) viewModel.resetGame()
                    }
                },
            )
        }
    }
}

@Composable
fun XoElement(
    isBlinking: Boolean,
    isX: Boolean,
    isAi: Boolean,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "",
    )
    val currentAlpha = if (isBlinking) alpha else 1f



    Text(
        if (isX) "X" else "O",
        style = MaterialTheme.typography.headlineLarge.plus(
            TextStyle(
                fontSize = 60.sp,
            )
        ),
        color = if (isX) (if (isAi) accent2 else MaterialTheme.colorScheme.primary) else (if (isAi) accent3 else accent1),
        modifier = Modifier
            .graphicsLayer(alpha = currentAlpha)
            .wrapContentSize()
    )
}

@Composable
fun Board(
    modifier: Modifier,
    viewModel: GameViewModel,
) {
    Box(
        modifier = modifier
            .fillMaxHeight(0.65f),
    ) {
        if (viewModel.isAi.not()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .alpha(if (viewModel.isTurnX) 0.25f else 1f),
            ) {
                Text(
                    "Player 1 - O",
                    style = MaterialTheme.typography.bodyMedium.plus(
                        TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    ),
                )
                Image(
                    painter = painterResource(R.drawable.self_image),
                    contentDescription = "Self Image",
                    modifier = Modifier
                        .size(
                            100.dp,
                            100.dp
                        ),
                    contentScale = ContentScale.Crop,
                )
            }
        }


        if (viewModel.isAi.not()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .alpha(if (viewModel.isTurnX.not()) 0.25f else 1f)
            ) {
                Image(
                    painter = painterResource(R.drawable.opponent_image),
                    contentDescription = "Opponent Image",
                    modifier = Modifier
                        .size(
                            100.dp,
                            100.dp
                        ),
                    contentScale = ContentScale.Crop,
                )
                Text(
                    "Player 2 - X",
                    modifier = Modifier.rotate(180f),
                    style = MaterialTheme.typography.bodyMedium.plus(
                        TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    ),
                )
            }
        }
        if (viewModel.isAi) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .alpha(if (viewModel.isTurnX) 0.25f else 1f)
            ) {
                Text(
                    "You - O",
                    style = MaterialTheme.typography.bodyMedium.plus(
                        TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    ),
                )
                Image(
                    painter = painterResource(R.drawable.self_ai_image),
                    contentDescription = "Self Image",
                    modifier = Modifier
                        .size(
                            100.dp,
                            100.dp
                        ),
                    contentScale = ContentScale.Crop,
                )
            }
        }
        if (viewModel.isAi) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .alpha(if (viewModel.isTurnX.not()) 0.25f else 1f)
            ) {
                Text(
                    "Game AI - X",
                    style = MaterialTheme.typography.bodyMedium.plus(
                        TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    ),
                )
                Image(
                    painter = painterResource(R.drawable.ai_image),
                    contentDescription = "Self Image",
                    modifier = Modifier
                        .size(
                            100.dp,
                            100.dp
                        ),
                    contentScale = ContentScale.Crop,
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .aspectRatio(1f)
                .align(Alignment.Center)
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth(0.96f)
                    .aspectRatio(1f)
                    .align(Alignment.BottomStart)
            ) {
            }

            LazyVerticalGrid(
                verticalArrangement = Arrangement.spacedBy(1.dp),
                horizontalArrangement = Arrangement.spacedBy(1.dp),
                columns = GridCells.Fixed(count = 3),
                userScrollEnabled = false,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxWidth(0.96f)
                    .aspectRatio(1f)
                    .align(Alignment.TopEnd)
                    .border(
                        border = BorderStroke(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                        ),
                    ),
            ) {
                items(viewModel.xoList.flatten().size) { index ->
                    Surface(
                        color = MaterialTheme.colorScheme.secondary,
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                        ),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clickable {
                                val row = index / 3
                                val column = index % 3
                                if (viewModel.xoList[row][column] != '_' || viewModel.isGameFinished || (if (viewModel.isAi) viewModel.isTurnX else false)) {
                                    return@clickable
                                }
                                CoroutineScope(Dispatchers.Main).launch {
                                    viewModel.performNewMove(
                                        Move(
                                            row = row,
                                            column = column
                                        )
                                    )
                                }
                            },
                    ) {
                        val row = index / 3
                        val column = index % 3
                        when (viewModel.xoList[row][column]) {
                            'X' -> {
                                XoElement(
                                    isX = true,
                                    isBlinking = viewModel.isGoingToDeleteList[row][column],
                                    isAi = viewModel.isAi,
                                )
                            }

                            'O' -> {
                                XoElement(
                                    isX = false,
                                    isBlinking = viewModel.isGoingToDeleteList[row][column],
                                    isAi = viewModel.isAi,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
