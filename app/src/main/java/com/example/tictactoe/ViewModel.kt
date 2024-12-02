package com.example.tictactoe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    var xoList = mutableStateListOf<String>().apply {
        repeat(9) { add("") }
    }
    var isTurnX by mutableStateOf<Boolean>(true)
    var isGameFinished by mutableStateOf<Boolean>(false)
    var winnerTitle by mutableStateOf<String>("")
    var xWins by mutableStateOf<Int>(0)
    var oWins by mutableStateOf<Int>(0)
    fun clearGame() {
        xoList.clear()
        xoList.addAll(List(9) { "" })
    }

    fun checkGameStatus() {
        if (xoList[0] == xoList[1] && xoList[1] == xoList[2] && xoList[0].isNotEmpty()) {
            isGameFinished = true

            if (xoList[0] == "X") {
                changeWinnerTitile("X")
            } else {
                changeWinnerTitile("O")
            }
        } else if (xoList[2] == xoList[5] && xoList[5] == xoList[8] && xoList[2].isNotEmpty()) {
            isGameFinished = true

            if (xoList[2] == "X") {
                changeWinnerTitile("X")
            } else {
                changeWinnerTitile("O")
            }
        } else if (xoList[0] == xoList[3] && xoList[3] == xoList[6] && xoList[0].isNotEmpty()) {
            isGameFinished = true

            if (xoList[0] == "X") {
                changeWinnerTitile("X")
            } else {
                changeWinnerTitile("O")
            }
        } else if (xoList[6] == xoList[7] && xoList[7] == xoList[8] && xoList[6].isNotEmpty()) {
            isGameFinished = true

            if (xoList[6] == "X") {
                changeWinnerTitile("X")
            } else {
                changeWinnerTitile("O")
            }
        } else if (xoList[0] == xoList[4] && xoList[4] == xoList[8] && xoList[0].isNotEmpty()) {
            isGameFinished = true

            if (xoList[0] == "X") {
                changeWinnerTitile("X")
            } else {
                changeWinnerTitile("O")
            }
        } else if (xoList[2] == xoList[4] && xoList[4] == xoList[6] && xoList[2].isNotEmpty()) {
            isGameFinished = true

            if (xoList[2] == "X") {
                changeWinnerTitile("X")
            } else {
                changeWinnerTitile("O")
            }
        } else if (xoList[1] == xoList[4] && xoList[4] == xoList[7] && xoList[1].isNotEmpty()) {
            isGameFinished = true

            if (xoList[1] == "X") {
                changeWinnerTitile("X")
            } else {
                changeWinnerTitile("O")
            }
        } else if (xoList[3] == xoList[4] && xoList[4] == xoList[5] && xoList[3].isNotEmpty()) {
            isGameFinished = true

            if (xoList[3] == "X") {
                changeWinnerTitile("X")
            } else {
                changeWinnerTitile("O")
            }
        } else {
            if (xoList.all({ it.isNotEmpty() })) {
                isGameFinished = true
                changeWinnerTitile("draw")
            }
        }
    }

    fun changeWinnerTitile(winner: String) {
        when (winner) {
            "X" -> {
                winnerTitle = "Winner is X, play again!"
                xWins++
            }

            "O" -> {
                winnerTitle = "Winner is O, play again!"
                oWins++
            }

            "draw" -> {
                winnerTitle = "Draw, play again!"
                xWins++
                oWins++
            }
        }
    }

    fun resetGame() {
        isGameFinished = false
        clearGame()
        winnerTitle = ""
    }
}
