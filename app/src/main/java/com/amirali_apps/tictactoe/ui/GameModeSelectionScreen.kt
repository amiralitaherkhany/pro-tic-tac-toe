package com.amirali_apps.tictactoe.ui

import android.view.MotionEvent
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Elderly
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.amirali_apps.tictactoe.GameScreens
import com.amirali_apps.tictactoe.R

@Composable
fun GameModeSelectionScreen(
    navController: NavController,
    viewModel: GameModeSelectionViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Box {
        BackgroundImage(Modifier.fillMaxSize())
        Scaffold(containerColor = Color.Transparent) {
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    GameTitle(modifier = Modifier.weight(0.50f))

                    AnimatedContent(
                        targetState = uiState,
                        modifier = Modifier.weight(0.50f)
                    ) { it ->
                        when (it) {
                            is UiState.SelectMode -> FirstContent(onSubmit = { isAi ->
                                viewModel.onSelectModeSubmitted(isAi)
                            })

                            is UiState.Settings -> SecondContent(
                                isAi = it.isAi,
                                onSubmit = { isPro, selectedAiLevel ->
                                    navController.navigate("${GameScreens.Game.name}/$isPro/${it.isAi}/$selectedAiLevel")
                                })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FirstContent(
    modifier: Modifier = Modifier,
    onSubmit: (isAi: Boolean) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Spacer(Modifier.weight(0.70f))
        Text(
            stringResource(R.string.how_do_you_want_to_play),
            style = MaterialTheme.typography.bodyMedium.plus(
                TextStyle(
                    fontSize = 18.sp
                )
            ),
            modifier = Modifier
                .weight(0.25f)
                .wrapContentSize()
        )
        Row(
            modifier = Modifier.weight(0.40f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            CustomButton(
                title = stringResource(R.string.single_player),
                onPressed = {
                    onSubmit(true)
                }
            )
        }
        Row(
            modifier = Modifier.weight(0.40f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            CustomButton(
                title = stringResource(R.string.play_with_a_friend),
                onPressed = {
                    onSubmit(false)
                },
            )
        }
    }
}

@Composable
fun SecondContent(
    modifier: Modifier = Modifier,
    isAi: Boolean,
    onSubmit: (isPro: Boolean, selectedAiLevel: Int?) -> Unit
) {
    var isPro by remember { mutableStateOf(false) }
    var selectedAiLevel by remember { mutableStateOf(0) }


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(if (isAi) 0.15f else 0.50f))
        Text(
            stringResource(R.string.select_mode),
            style = MaterialTheme.typography.bodyMedium.plus(
                TextStyle(
                    fontSize = 18.sp
                )
            ),
            modifier = Modifier.weight(0.10f)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .weight(0.40f)
        ) {
            FilterChip(
                modifier = Modifier
                    .size(
                        100.dp,
                        50.dp
                    )
                    .weight(1f)
                    .scale(1.1f),
                selected = isPro.not(),
                onClick = {
                    isPro = false
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
                selected = isPro,
                onClick = {
                    isPro = true
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

        if (isAi) {
            val iconList = listOf<ImageVector>(
                Icons.Filled.ChildCare,
                Icons.Filled.SmartToy,
                Icons.Filled.Psychology
            )
            Text(
                stringResource(R.string.select_ai_difficulty),
                style = MaterialTheme.typography.bodyMedium.plus(
                    TextStyle(
                        fontSize = 18.sp
                    )
                ),
                modifier = Modifier.weight(0.10f)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .weight(0.40f)
            ) {
                AiLevel.entries.forEachIndexed { index, aiLevel ->
                    FilterChip(
                        modifier = Modifier
                            .weight(0.30f)
                            .padding(5.dp)
                            .fillMaxHeight(0.70f),
                        selected = index == selectedAiLevel,
                        onClick = {
                            selectedAiLevel = index
                        },
                        label = {
                            Text(
                                text = AiLevel.entries[index].name,
                                fontSize = 10.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                contentDescription = "",
                                imageVector = iconList[index]
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Color.White,
                            selectedLabelColor = Color.White,
                            selectedContainerColor = Color.Black,
                            selectedLeadingIconColor = Color.White,
                            iconColor = Color.Black,
                            labelColor = Color.Black,
                        ),
                        border = BorderStroke(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                        ),
                    )
                }
            }
        }
        Row(
            modifier = Modifier.weight(0.40f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            CustomButton(
                title = stringResource(R.string.start_game),
                onPressed = {
                    onSubmit(
                        isPro,
                        if (isAi) selectedAiLevel else -1
                    )
                },
            )
        }
    }
}

@Composable
private fun BackgroundImage(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(R.drawable.xo_background),
        contentDescription = "background_image",
        contentScale = ContentScale.FillBounds
    )
}

@Composable
private fun GameTitle(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Spacer(Modifier.weight(0.10f))
        Text(
            "Tic",
            style = MaterialTheme.typography.titleLarge.plus(
                TextStyle(
                    color = Color.Black,
                    fontSize = 66.sp,
                )
            ),
            modifier = Modifier
                .weight(0.25f)
                .wrapContentSize(Alignment.BottomCenter)
        )
        Text(
            "Tac",
            style = MaterialTheme.typography.titleLarge.plus(
                TextStyle(
                    color = Color.Black,
                    fontSize = 86.sp,
                )
            ),
            modifier = Modifier
                .weight(0.25f)
                .wrapContentSize(Alignment.BottomCenter)
        )
        Text(
            "Toe",
            style = MaterialTheme.typography.titleLarge.plus(
                TextStyle(
                    color = Color.Black,
                    fontSize = 96.sp,
                )
            ),
            modifier = Modifier
                .weight(0.25f)
                .wrapContentSize(Alignment.BottomCenter)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomButton(
    title: String,
    onPressed: () -> Unit,
) {
    var isPressed by remember { mutableStateOf(false) }
    Box(
        Modifier
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
                        onPressed()
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
                    title,
                    style = MaterialTheme.typography.bodyLarge.plus(
                        TextStyle(
                            color = MaterialTheme.colorScheme.background
                        )
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }
        }
    }
}
