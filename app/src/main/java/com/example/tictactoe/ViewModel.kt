package com.example.tictactoe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GameViewModel(var isPro: Boolean) : ViewModel() {
    private var turnNumber = 0
    var isTurnX by mutableStateOf(true)
    var isGameFinished by mutableStateOf(false)
    var winnerTitle by mutableStateOf("")
    var xWins by mutableIntStateOf(0)
    var oWins by mutableIntStateOf(0)
    private var xoQueue: ArrayDeque<Move> = ArrayDeque()
    var xoList by mutableStateOf(
        List(3) { MutableList(3) { "" } }
    )
    var isGoingToDeleteList by mutableStateOf(
        List(3) { MutableList(3) { false } }
    )

    fun performNewMove(
        move: Move
    ) {
        xoList[move.row][move.column] = if (isTurnX) "X" else "O"

        if (isPro) {
            isGoingToDeleteList[move.row][move.column] = false
            if (turnNumber < 7) {
                turnNumber++
            }
            xoQueue.add(move)
            if (turnNumber > 6) {
                xoList[xoQueue.first().row][xoQueue.first().column] = ""
                xoQueue.removeFirst()
            }
            if (turnNumber > 5) {
                isGoingToDeleteList[xoQueue.first().row][xoQueue.first().column] = true
            }
        }


        checkGameStatus()
        isTurnX = !isTurnX
    }

    private fun checkGameStatus() {
        for (row in 0..2) {
            if (xoList[row][0] == xoList[row][1] && xoList[row][1] == xoList[row][2] && xoList[row][0].isNotEmpty()) {
                isGameFinished = true
                placeWinner(xoList[row][0])
                return
            }
        }
        for (col in 0..2) {
            if (xoList[0][col] == xoList[1][col] && xoList[1][col] == xoList[2][col] && xoList[0][col].isNotEmpty()) {
                isGameFinished = true
                placeWinner(xoList[0][col])
                return
            }
        }
        if (xoList[0][0] == xoList[1][1] && xoList[1][1] == xoList[2][2] && xoList[0][0].isNotEmpty()) {
            isGameFinished = true
            placeWinner(xoList[0][0])
            return
        }
        if (xoList[0][2] == xoList[1][1] && xoList[1][1] == xoList[2][0] && xoList[0][2].isNotEmpty()) {
            isGameFinished = true
            placeWinner(xoList[0][2])
            return
        }
        if (xoList.all { row -> row.all { it.isNotEmpty() } }) {
            isGameFinished = true
            placeWinner("draw")
        }
    }

    private fun placeWinner(winner: String) {
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

    fun clearGame() {
        xoList = List(3) { MutableList(3) { "" } }
        xoQueue.clear()
        turnNumber = 0
    }
}
