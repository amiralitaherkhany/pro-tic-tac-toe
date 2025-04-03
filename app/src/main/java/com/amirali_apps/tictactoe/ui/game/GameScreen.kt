package com.amirali_apps.tictactoe.ui.game

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.amirali_apps.tictactoe.R
import com.amirali_apps.tictactoe.models.Move
import com.amirali_apps.tictactoe.ui.components.MiniCustomButton
import com.amirali_apps.tictactoe.ui.navigation.GameScreens
import com.amirali_apps.tictactoe.ui.theme.accent1
import com.amirali_apps.tictactoe.ui.theme.accent2
import com.amirali_apps.tictactoe.ui.theme.accent3
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun GameScreen(
    gameViewModel: GameViewModel = hiltViewModel(),
    navController: NavController,
) {
    val isGameFinished by gameViewModel.isGameFinished.collectAsState()
    val winnerTitle by gameViewModel.winnerTitle.collectAsState()
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
                visible = isGameFinished,
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
                        stringResource(winnerTitle ?: R.string.empty_string),
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
                                navController.navigate(GameScreens.GameModeSelection.name) {
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

@Composable
fun MainLayout(
    viewModel: GameViewModel,
    modifier: Modifier,
    navController: NavController,
) {
    val isTurnX by viewModel.isTurnX.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            AnimatedVisibility(
                visible = if (viewModel.isAi.not()) isTurnX else false,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        stringResource(R.string.your_move),
                        style = MaterialTheme.typography.bodyLarge.plus(
                            TextStyle(
                                fontSize = 20.sp,
                            )
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.rotate(180f)
                    )

                    Text(
                        if (isTurnX) "X" else (if (viewModel.isAi) "O" else "X"),
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
        Board(
            viewModel = viewModel,
            modifier = Modifier.align(Alignment.Center)
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = isTurnX.not() or viewModel.isAi,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        if (isTurnX) (if (viewModel.isAi) "X" else "O") else "O",
                        style = MaterialTheme.typography.headlineMedium.plus(
                            TextStyle(
                                fontSize = 50.sp
                            )
                        ),
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        if (viewModel.isAi.not()) "Your Move" else (if (isTurnX) stringResource(R.string.ai_s_move) else stringResource(R.string.your_move)),
                        style = MaterialTheme.typography.bodyLarge.plus(
                            TextStyle(
                                fontSize = 20.sp,
                            )
                        ),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .padding(
                    start = 20.dp,
                    top = 20.dp
                )
        ) {
            MiniCustomButton(
                icon = Icons.Filled.Close,
                onClick = {
                    navController.navigate(GameScreens.GameModeSelection.name) {
                        popUpTo(0) { inclusive = true }
                    }
                },
            )
            Spacer(Modifier.height(15.dp))
            MiniCustomButton(
                icon = Icons.Filled.Replay,
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        if (viewModel.isAi.not() or (viewModel.isAi and isTurnX.not())) viewModel.resetGame()
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
    val density = LocalConfiguration.current.densityDpi


    Text(
        if (isX) "X" else "O",
        style = MaterialTheme.typography.headlineLarge.plus(
            TextStyle(
                fontSize = (density * 0.17).sp,
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
    modifier: Modifier = Modifier,
    viewModel: GameViewModel,
) {
    val isTurnX by viewModel.isTurnX.collectAsState()
    val board by viewModel.xoList.collectAsState()
    val isGameFinished by viewModel.isGameFinished.collectAsState()
    val isGoingToDeleteList by viewModel.isGoingToDeleteList.collectAsState()
    val xWins by viewModel.xWins.collectAsState()
    val oWins by viewModel.oWins.collectAsState()
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    Column(
        modifier = modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.75f)
        ) {
            if (viewModel.isAi) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .alpha(if (isTurnX) 0.25f else 1f)
                ) {
                    Text(
                        stringResource(
                            R.string.you_o,
                            oWins
                        ),
                        style = MaterialTheme.typography.bodyMedium.plus(
                            TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = (screenWidthDp * 0.042).sp,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        ),
                    )
                    Image(
                        painter = painterResource(R.drawable.self_ai_image),
                        contentDescription = "Self Image",
                        modifier = Modifier
                            .size(
                                (screenWidthDp * 0.26).dp,
                                (screenWidthDp * 0.26).dp
                            ),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            if (viewModel.isAi.not()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .alpha(if (isTurnX) 0.25f else 1f),
                ) {
                    Text(
                        stringResource(
                            R.string.player_o,
                            oWins
                        ),
                        style = MaterialTheme.typography.bodyMedium.plus(
                            TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = (screenWidthDp * 0.042).sp,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        ),
                    )
                    Image(
                        painter = painterResource(R.drawable.self_image),
                        contentDescription = "Self Image",
                        modifier = Modifier
                            .size(
                                (screenWidthDp * 0.26).dp,
                                (screenWidthDp * 0.26).dp
                            )
                            .graphicsLayer(
                                scaleX = -1f
                            ),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            if (viewModel.isAi) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .alpha(if (isTurnX.not()) 0.25f else 1f)
                ) {
                    Text(
                        stringResource(
                            R.string.game_ai,
                            xWins
                        ),
                        style = MaterialTheme.typography.bodyMedium.plus(
                            TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = (screenWidthDp * 0.042).sp,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        ),
                    )
                    Image(
                        painter = painterResource(R.drawable.ai_image),
                        contentDescription = "Ai Image",
                        modifier = Modifier
                            .size(
                                (screenWidthDp * 0.26).dp,
                                (screenWidthDp * 0.26).dp
                            )
                            .graphicsLayer(
                                scaleX = -1f
                            ),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .width((screenWidthDp * 0.75).dp)
                .aspectRatio(1f)
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
                items(board.flatten().size) { index ->
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
                                if (board[row][column] != '_' || isGameFinished || (if (viewModel.isAi) isTurnX else false)) {
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
                        when (board[row][column]) {
                            'X' -> {
                                XoElement(
                                    isX = true,
                                    isBlinking = isGoingToDeleteList[row][column],
                                    isAi = viewModel.isAi,
                                )
                            }

                            'O' -> {
                                XoElement(
                                    isX = false,
                                    isBlinking = isGoingToDeleteList[row][column],
                                    isAi = viewModel.isAi,
                                )
                            }
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(0.75f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .alpha(if (viewModel.isAi.not()) (if (isTurnX.not()) 0.25f else 1f) else 0f)
            ) {
                Image(
                    painter = painterResource(R.drawable.opponent_image),
                    contentDescription = "Opponent Image",
                    modifier = Modifier
                        .size(
                            (screenWidthDp * 0.26).dp,
                            (screenWidthDp * 0.26).dp
                        )
                        .graphicsLayer(
                            rotationZ = 180f,
                            scaleX = -1f,
                        ),
                    contentScale = ContentScale.Crop,
                )
                Text(
                    stringResource(
                        R.string.player_x,
                        xWins
                    ),
                    modifier = Modifier.rotate(180f),
                    style = MaterialTheme.typography.bodyMedium.plus(
                        TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = (screenWidthDp * 0.042).sp,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    ),
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
