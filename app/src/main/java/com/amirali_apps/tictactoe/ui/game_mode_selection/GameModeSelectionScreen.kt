package com.amirali_apps.tictactoe.ui.game_mode_selection

import android.content.res.Configuration
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
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Elderly
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.amirali_apps.tictactoe.R
import com.amirali_apps.tictactoe.ui.components.MiniCustomButton
import com.amirali_apps.tictactoe.ui.game.AiLevel
import com.amirali_apps.tictactoe.ui.navigation.GameScreens

@Composable
fun LocaleButton(
    modifier: Modifier = Modifier,
    selectedLocale: String,
    onLocaleChanged: (String) -> Unit,
    isActive: Boolean,
) {
    MiniCustomButton(
        onClick = {
            if (isActive) {
                onLocaleChanged(if (selectedLocale == "fa") "en" else "fa")
            }
        },
        modifier = modifier
    ) {
        Text(
            selectedLocale,
            style = MaterialTheme.typography.bodySmall.plus(
                TextStyle(
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 15.sp,
                )
            ),
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Composable
fun GameModeSelectionScreen(
    navController: NavController,
    viewModel: GameModeSelectionViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedLocale by viewModel.settingState.collectAsState()
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val screenHeightDp = configuration.screenHeightDp
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    var isActive by remember { mutableStateOf(true) }
    LaunchedEffect(configuration) {
        viewModel.getLanguage()
    }
    Box {
        BackgroundImage(Modifier.fillMaxSize())

        Scaffold(containerColor = Color.Transparent) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (isPortrait) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .padding(horizontal = if (screenWidthDp > 500) (screenWidthDp * 0.15).dp else 0.dp),
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
                                    },
                                    onBackPressed = {
                                        viewModel.onBackPressed()
                                    },
                                )
                            }
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .padding(vertical = if (screenHeightDp > 500) (screenHeightDp * 0.15).dp else 0.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        GameTitle(
                            modifier = Modifier
                                .weight(0.50f)
                        )

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
                                    },
                                    onBackPressed = {
                                        viewModel.onBackPressed()
                                    },
                                )
                            }
                        }
                    }
                }
                LocaleButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(it)
                        .padding(start = 20.dp),
                    onLocaleChanged = { newLocale ->
                        viewModel.changeLanguage(newLocale)
                        isActive = false // it will back true with activity recreation
                    },
                    selectedLocale = selectedLocale.selectedLanguage,
                    isActive = isActive
                )
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
        Spacer(Modifier.weight(0.50f))
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
            modifier = Modifier.weight(0.30f),
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
            modifier = Modifier.weight(0.30f),
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
        Spacer(Modifier.weight(0.10f))
    }
}

@Composable
fun SecondContent(
    modifier: Modifier = Modifier,
    isAi: Boolean,
    onSubmit: (isPro: Boolean, selectedAiLevel: Int?) -> Unit,
    onBackPressed: () -> Unit
) {
    var isPro by remember { mutableStateOf(false) }
    var selectedAiLevel by remember { mutableIntStateOf(0) }


    Box {
        IconButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 10.dp),
            onClick = onBackPressed
        ) {
            Icon(
                Icons.Filled.ArrowBackIosNew,
                contentDescription = "back button"
            )
        }
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
                        .weight(1f),
                    selected = isPro.not(),
                    onClick = {
                        isPro = false
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.classic),
                            style = MaterialTheme.typography.bodyMedium,
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
                        .weight(1f),
                    border = BorderStroke(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                    ),
                    selected = isPro,
                    onClick = {
                        isPro = true
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.pro),
                            style = MaterialTheme.typography.bodyMedium.plus(
                                TextStyle(
                                    fontSize = 18.sp
                                )
                            ),
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
                        .padding(horizontal = 15.dp)
                        .weight(0.40f)
                ) {
                    AiLevel.entries.forEachIndexed { index, aiLevel ->
                        FilterChip(
                            modifier = Modifier
                                .fillMaxHeight(0.5f)
                                .weight(0.3f)
                                .padding(horizontal = 5.dp),
                            selected = index == selectedAiLevel,
                            onClick = {
                                selectedAiLevel = index
                            },
                            label = {
                                BasicText(
                                    text = stringResource(aiLevel.stringResources),
                                    style = MaterialTheme.typography.bodyMedium.plus(
                                        TextStyle(
                                            color = if (index == selectedAiLevel) Color.White else Color.Black,
                                        )
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    autoSize = TextAutoSize.StepBased(
                                        maxFontSize = 15.sp,
                                        minFontSize = 5.sp,
                                    )
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
}

@Composable
private fun BackgroundImage(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(R.drawable.xo_background),
        contentDescription = "background_image",
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun GameTitle(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.align(Alignment.Center)
        ) {
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
                    .wrapContentSize(Alignment.Center)
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
                    .wrapContentSize(Alignment.Center)
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
                    .wrapContentSize(Alignment.Center)
            )
        }
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
