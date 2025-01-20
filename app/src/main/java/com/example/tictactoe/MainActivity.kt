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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
        composable("gameScreen/{selectedMode}") { backStackEntry ->
            GameScreen(isPro = backStackEntry.arguments?.getString("selectedMode").toBoolean())
        }
    }
}

@Composable
fun GameModeSelectionScreen(navController: NavController) {
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

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(16.dp)
        ) {
            Button(
                onClick = {
                    navController.navigate("gameScreen/false")
                },
                colors = ButtonColors(
                    containerColor = Color(color = 0xff607D8B),
                    contentColor = Color.White,
                    disabledContentColor = Color(color = 0xff607D8B),
                    disabledContainerColor = Color(color = 0xff607D8B),
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp)
            ) {
                Text("Classic")
            }

            Spacer(Modifier.width(10.dp))
            Button(
                onClick = {
                    navController.navigate("gameScreen/true")
                },
                colors = ButtonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    disabledContentColor = Color(color = 0xff607D8B),
                    disabledContainerColor = Color(color = 0xff607D8B),
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp)
            ) {
                Text("Pro")
            }
        }
    }
}
