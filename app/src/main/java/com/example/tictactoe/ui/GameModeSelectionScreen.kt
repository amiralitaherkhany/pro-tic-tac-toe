package com.example.tictactoe.ui

import android.view.MotionEvent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Elderly
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tictactoe.R

@Composable
fun GameModeSelectionScreen(navController: NavController) {
    var selectedModeChip by remember { mutableStateOf("Classic") }
    var selectedOponentChip by remember { mutableStateOf("Friend") }

    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.xo_background),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )
        Scaffold(containerColor = Color.Transparent) {
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .weight(0.50f)
                    ) {
                        Text(
                            "Tic",
                            style = MaterialTheme.typography.titleLarge.plus(
                                TextStyle(
                                    color = Color.Black,
                                    fontSize = 66.sp,
                                )
                            ),
                        )
                        Text(
                            "Tac",
                            style = MaterialTheme.typography.titleLarge.plus(
                                TextStyle(
                                    color = Color.Black,
                                    fontSize = 86.sp,
                                )
                            ),
                        )
                        Text(
                            "Toe",
                            style = MaterialTheme.typography.titleLarge.plus(
                                TextStyle(
                                    color = Color.Black,
                                    fontSize = 92.sp,
                                )
                            ),
                        )
                    }


                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(0.35f)
                    ) {
                        Text(
                            stringResource(R.string.select_mode),
                            style = MaterialTheme.typography.bodyMedium.plus(
                                TextStyle(
                                    fontSize = 18.sp
                                )
                            ),
                            modifier = Modifier.weight(0.5f)
                        )
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .weight(1f)
                        ) {
                            FilterChip(
                                modifier = Modifier
                                    .size(
                                        100.dp,
                                        50.dp
                                    )
                                    .weight(1f)
                                    .scale(1.1f),
                                selected = selectedModeChip == "Classic",
                                onClick = {
                                    selectedModeChip = "Classic"
                                },
                                label = { Text(text = stringResource(R.string.classic)) },
                                colors = FilterChipDefaults.filterChipColors(
                                    containerColor = Color.White,
                                    selectedLabelColor = Color.White,
                                    selectedContainerColor = Color.Black,
                                    selectedLeadingIconColor = Color.White,
                                    iconColor = Color.Black,
                                    labelColor = Color.Black,
                                ),
                                leadingIcon = {
                                    Icon(
                                        contentDescription = "Classic Icon",
                                        imageVector = Icons.Filled.Elderly,
                                        modifier = Modifier.size(
                                            width = 30.dp,
                                            height = 30.dp
                                        )
                                    )
                                },
                                border = BorderStroke(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            )
                            Spacer(Modifier.weight(0.5f))
                            FilterChip(
                                modifier = Modifier
                                    .size(
                                        100.dp,
                                        50.dp
                                    )
                                    .weight(1f)
                                    .scale(1.1f),
                                border = BorderStroke(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                ),
                                selected = selectedModeChip == "Pro",
                                onClick = {
                                    selectedModeChip = "Pro"
                                },
                                label = { Text(text = stringResource(R.string.pro)) },
                                colors = FilterChipDefaults.filterChipColors(
                                    containerColor = Color.White,
                                    selectedLabelColor = Color.White,
                                    selectedContainerColor = Color.Black,
                                    selectedLeadingIconColor = Color.White,
                                    iconColor = Color.Black,
                                    labelColor = Color.Black,
                                ),
                                leadingIcon = {
                                    Icon(
                                        contentDescription = "Pro Icon",
                                        imageVector = Icons.Filled.Person4,
                                        modifier = Modifier.size(
                                            width = 30.dp,
                                            height = 30.dp
                                        ),
                                    )
                                }
                            )
                        }
                        Text(
                            stringResource(R.string.select_opponent),
                            style = MaterialTheme.typography.bodyMedium.plus(
                                TextStyle(
                                    fontSize = 18.sp
                                )
                            ),
                            modifier = Modifier.weight(0.5f)
                        )

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(
                                    horizontal = 20.dp
                                )
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            FilterChip(
                                modifier = Modifier
                                    .size(
                                        100.dp,
                                        50.dp
                                    )
                                    .weight(1f)
                                    .scale(1.1f),
                                border = BorderStroke(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                ),
                                selected = selectedOponentChip == "Friend",
                                onClick = {
                                    selectedOponentChip = "Friend"
                                },
                                label = { Text(text = stringResource(R.string.friend)) },
                                colors = FilterChipDefaults.filterChipColors(
                                    containerColor = Color.White,
                                    selectedLabelColor = Color.White,
                                    selectedContainerColor = Color.Black,
                                    selectedLeadingIconColor = Color.White,
                                    iconColor = Color.Black,
                                    labelColor = Color.Black,
                                ),
                                leadingIcon = {
                                    Icon(
                                        contentDescription = "Pro Icon",
                                        imageVector = Icons.Filled.GroupAdd,
                                        modifier = Modifier.size(
                                            width = 30.dp,
                                            height = 30.dp
                                        ),
                                    )
                                }
                            )
                            Spacer(Modifier.weight(0.5f))

                            FilterChip(
                                modifier = Modifier
                                    .size(
                                        100.dp,
                                        50.dp
                                    )
                                    .weight(1f)
                                    .scale(1.1f),
                                border = BorderStroke(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                ),
                                selected = selectedOponentChip == "Ai",
                                onClick = {
                                    selectedOponentChip = "Ai"
                                },
                                label = { Text(text = stringResource(R.string.ai)) },
                                colors = FilterChipDefaults.filterChipColors(
                                    containerColor = Color.White,
                                    selectedLabelColor = Color.White,
                                    selectedContainerColor = Color.Black,
                                    selectedLeadingIconColor = Color.White,
                                    iconColor = Color.Black,
                                    labelColor = Color.Black,
                                ),
                                leadingIcon = {
                                    Icon(
                                        contentDescription = "Pro Icon",
                                        imageVector = Icons.Filled.SmartToy,
                                        modifier = Modifier.size(
                                            width = 30.dp,
                                            height = 30.dp
                                        ),
                                    )
                                }
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.weight(0.15f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        CustomButton(
                            navController = navController,
                            selectedModeChip = selectedModeChip,
                            selectedOponentChip = selectedOponentChip,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomButton(
    selectedModeChip: String,
    selectedOponentChip: String,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    var isPressed by remember { mutableStateOf(false) }
    Box(
        modifier
            .height(
                62.dp
            )
            .fillMaxWidth(
                0.89f
            )
            .pointerInteropFilter { event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isPressed = true
                    }

                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        isPressed = false
                        val isPro = selectedModeChip == "Pro"
                        val isAi = selectedOponentChip == "Ai"
                        navController.navigate("gameScreen/${isPro}/${isAi}")
                    }
                }
                true
            },
    ) {
        Surface(
            modifier = Modifier
                .height(
                    56.dp
                )
                .fillMaxWidth(0.98f)
                .align(if (isPressed.not()) Alignment.BottomStart else Alignment.Center)
        ) {
        }
        Surface(
            modifier = Modifier
                .height(
                    56.dp
                )
                .fillMaxWidth(0.98f)
                .align(if (isPressed.not()) Alignment.TopEnd else Alignment.Center),
            color = MaterialTheme.colorScheme.secondary,
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            Surface(
                modifier = Modifier
                    .size(
                        width = 344.dp,
                        height = 56.dp
                    )
                    .align(Alignment.TopEnd)
                    .padding(4.dp),
            ) {
                Text(
                    stringResource(R.string.start_game),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }
        }
    }
}
