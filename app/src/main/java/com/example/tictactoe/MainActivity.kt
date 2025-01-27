package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Elderly
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TicTacToeTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = "gameModeSelection"
    ) {
        composable("gameModeSelection") { GameModeSelectionScreen(navController) }
        composable(
            "gameScreen/{isPro}/{isAi}",
            arguments = listOf(
                navArgument("isPro") { type = NavType.BoolType },
                navArgument("isAi") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            GameScreen(
                isPro = backStackEntry.arguments?.getBoolean("isPro")!!,
                isAi = backStackEntry.arguments?.getBoolean("isAi")!!,
            )
        }
    }
}

@Composable
fun GameModeSelectionScreen(navController: NavController) {
    var selectedModeChip by remember { mutableStateOf("Classic") }
    var selectedOponentChip by remember { mutableStateOf("Friend") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Select Game Mode",
            style = MaterialTheme.typography.headlineMedium,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                FilterChip(
                    modifier = Modifier.padding(10.dp),
                    selected = selectedModeChip == "Classic",
                    onClick = {
                        selectedModeChip = "Classic"
                    },
                    label = { Text(text = "Classic") },
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
                    }
                )
                FilterChip(
                    modifier = Modifier.padding(10.dp),
                    selected = selectedModeChip == "Pro",
                    onClick = {
                        selectedModeChip = "Pro"
                    },
                    label = { Text(text = "Pro") },
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
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                FilterChip(
                    modifier = Modifier.padding(10.dp),
                    selected = selectedOponentChip == "Friend",
                    onClick = {
                        selectedOponentChip = "Friend"
                    },
                    label = { Text(text = "vs " + "Friend") },
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
                FilterChip(
                    modifier = Modifier.padding(10.dp),
                    selected = selectedOponentChip == "Ai",
                    onClick = {
                        selectedOponentChip = "Ai"
                    },
                    label = { Text(text = "vs " + "Ai") },
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
            Spacer(Modifier.height(100.dp))
            Button(
                onClick = {
                    val isPro = selectedModeChip == "Pro"
                    val isAi = selectedOponentChip == "Ai"
                    navController.navigate("gameScreen/${isPro}/${isAi}")
                },
                colors =
                ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color.Black,
                ),
                modifier = Modifier.size(
                    300.dp,
                    50.dp
                )
            ) { Text("Start") }
        }
    }
}

