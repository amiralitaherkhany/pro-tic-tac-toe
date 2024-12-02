package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val gameViewModel = MyViewModel()
            TicTacToeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Black,
                    topBar = {
                        CenterAlignedTopAppBar(actions = {
                            IconButton(
                                colors = IconButtonColors(
                                    containerColor = Color.Black,
                                    contentColor = Color.White,
                                    disabledContentColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                ),
                                onClick = {
                                    if (!gameViewModel.isGameFinished) gameViewModel.clearGame()
                                },
                                content = {
                                    Icon(
                                        contentDescription = "Refresh Game",
                                        tint = Color.White,
                                        imageVector = Icons.Filled.Refresh,
                                        modifier = Modifier.size(
                                            width = 30.dp,
                                            height = 30.dp
                                        )
                                    )
                                },
                                enabled = true,
                            )
                        },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Black,
                                titleContentColor = Color.White,
                            ),
                            title = {
                                Text(
                                    "TicTacToe",
                                    color = Color.White,
                                )
                            })
                    },
                ) { innerPadding ->
                    MainLayout(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = gameViewModel,
                    )
                }
            }
        }
    }
}

@Composable
fun MainLayout(
    viewModel: MyViewModel,
    modifier: Modifier,
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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row {
                        Text(
                            text = "Player ",
                            fontSize = 25.sp,
                            color = Color.White,
                        )
                        Text(
                            text = "O",
                            fontSize = 25.sp,
                            color = Color.Red
                        )
                    }
                    Spacer(
                        Modifier.height(20.dp)
                    )
                    Text(
                        text = viewModel.oWins.toString(),
                        color = Color.White,
                        fontSize = 25.sp,
                    )
                }
                Spacer(
                    modifier = Modifier.width(100.dp)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Player X",
                        color = Color.White,
                        fontSize = 25.sp,
                    )
                    Spacer(
                        Modifier.height(20.dp)
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
                        viewModel.resetGame()
                    },
                    border = BorderStroke(
                        width = 3.dp,
                        color = Color.White,
                    ),
                    colors = ButtonColors(
                        contentColor = Color.White,
                        disabledContentColor = Color.Transparent,
                        containerColor = Color.Black,
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 3),
                verticalArrangement = Arrangement.Center,
                horizontalArrangement = Arrangement.Center,
            ) {
                itemsIndexed(items = viewModel.xoList) { index, item ->
                    Surface(
                        color = Color.Transparent,
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color.White
                        ),
                        modifier = Modifier
                            .height(100.dp)
                            .clickable {
                                if (viewModel.xoList[index] != "" || viewModel.isGameFinished) {
                                    return@clickable
                                }
                                viewModel.xoList[index] = if (viewModel.isTurnX) "X" else "O"
                                viewModel.checkGameStatus()
                                viewModel.isTurnX = !viewModel.isTurnX
                            },
                    ) {
                        Box {
                            Text(
                                text = item,
                                fontSize = 70.sp,
                                modifier = Modifier.align(Alignment.Center),
                                textAlign = TextAlign.Center,
                                color = if (item == "X") Color.White else Color.Red
                            )
                        }
                    }
                }
            }
        }
        Text(
            if (viewModel.isTurnX) "Turn X" else "Turn O",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        )
    }
}

