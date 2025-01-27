package com.example.tictactoe

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    isPro: Boolean,
    isAi: Boolean
) {
    val gameViewModel = GameViewModel(
        isPro,
        isAi
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(color = 0xff607D8B),
        topBar = {
            CenterAlignedTopAppBar(
                actions = {
                    IconButton(
                        colors = IconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Transparent,
                            disabledContentColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                        ),
                        onClick = {
                            CoroutineScope(Dispatchers.Main).launch {
                                if (!gameViewModel.isGameFinished && !gameViewModel.isTurnX) gameViewModel.resetGame()
                            }
                        },
                        enabled = true,
                    ) {
                        Icon(
                            contentDescription = "Refresh Game",
                            tint = Color(0xff212121),
                            imageVector = Icons.Filled.Refresh,
                            modifier = Modifier.size(
                                width = 30.dp,
                                height = 30.dp
                            )
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(color = 0xff607D8B),
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(
                        "TicTacToe",
                        color = Color.White,
                    )
                },
            )
        },
    ) { innerPadding ->
        MainLayout(
            modifier = Modifier.padding(innerPadding),
            viewModel = gameViewModel,
            isAI = isAi,
        )
    }
}

@Composable
fun MainLayout(
    viewModel: GameViewModel,
    modifier: Modifier,
    isAI: Boolean
) {
    Box {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(
                Modifier.height(30.dp)
            )
            Row {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(
                        0.5f,
                        fill = true,
                    ),
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Player ")
                            withStyle(style = SpanStyle(color = Color(0xffF44336))) {
                                append("O")
                            }
                        },
                        fontSize = 25.sp,
                        color = Color.White,
                    )
                    Spacer(
                        Modifier.height(20.dp)
                    )
                    Text(
                        text = viewModel.oWins.toString(),
                        color = Color.White,
                        fontSize = 25.sp,
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(
                        0.5f,
                        fill = true,
                    )
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Player ")
                            withStyle(style = SpanStyle(color = Color(0xff212121))) {
                                append("X")
                            }
                        },
                        color = Color.White,
                        fontSize = 25.sp,
                    )
                    Spacer(
                        Modifier.height(20.dp),
                    )
                    Text(
                        text = viewModel.xWins.toString(),
                        fontSize = 25.sp,
                        color = Color.White,
                    )
                }
            }
            Spacer(
                Modifier.height(25.dp)
            )
            AnimatedVisibility(visible = viewModel.isGameFinished) {
                Button(
                    onClick = {
                        CoroutineScope(Dispatchers.Main).launch {
                            viewModel.resetGame()
                        }
                    },
                    border = BorderStroke(
                        width = 2.dp,
                        color = Color(0xff212121),
                    ),
                    colors = ButtonColors(
                        contentColor = Color(0xff212121),
                        disabledContentColor = Color.Transparent,
                        containerColor = Color(0xffBDBDBD),
                        disabledContainerColor = Color.Transparent,
                    ),
                    modifier = Modifier.size(
                        width = 300.dp,
                        height = 50.dp
                    )
                ) {
                    Text(
                        viewModel.winnerTitle,
                        fontSize = 20.sp
                    )
                }
            }
            Spacer(
                Modifier.height(25.dp)
            )

            Surface(
                border = BorderStroke(
                    width = 5.dp,
                    color = Color(0xff212121),
                ),
                color = Color.Transparent,
                modifier = Modifier.padding(10.dp),
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(count = 3),
                    verticalArrangement = Arrangement.Center,
                    userScrollEnabled = false,
                    horizontalArrangement = Arrangement.Center,
                    contentPadding = PaddingValues(0.dp),
                ) {
                    items(viewModel.xoList.flatten().size) { index ->
                        Surface(
                            color = Color.Transparent,
                            border = BorderStroke(
                                width = 2.dp,
                                color = Color(0xff212121),
                            ),
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clickable {
                                    val row = index / 3
                                    val column = index % 3
                                    if (viewModel.xoList[row][column] != '_' || viewModel.isGameFinished || (if (isAI) viewModel.isTurnX else false)) {
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
                                }
                                .padding(0.dp),
                        ) {
                            val row = index / 3
                            val column = index % 3
                            when (viewModel.xoList[row][column]) {
                                'X' -> {
                                    GameImage(
                                        isX = true,
                                        isBlinking = viewModel.isGoingToDeleteList[row][column]
                                    )
                                }

                                'O' -> {
                                    GameImage(
                                        isX = false,
                                        isBlinking = viewModel.isGoingToDeleteList[row][column]
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        Text(
            if (viewModel.isTurnX) buildAnnotatedString {
                append("Turn ")
                withStyle(style = SpanStyle(color = Color(0xff212121))) {
                    append("X")
                }
            } else buildAnnotatedString {
                append("Turn ")
                withStyle(style = SpanStyle(color = Color(0xffF44336))) {
                    append("O")
                }
            },
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        )
    }
}

@Composable
fun GameImage(
    isBlinking: Boolean,
    isX: Boolean
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )
    val currentAlpha = if (isBlinking) alpha else 1f



    Image(
        painter = painterResource(if (isX) R.drawable.x_icon else R.drawable.o_icon),
        contentDescription = null,
        contentScale = ContentScale.Inside,
        alignment = Alignment.Center,
        modifier = Modifier
            .graphicsLayer(alpha = currentAlpha)
            .scale(0.5f)
    )
}

