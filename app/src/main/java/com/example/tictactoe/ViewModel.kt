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
    var xoMap: MutableMap<Int, Int?> = (1..7).associateWith { null }.toMutableMap()
    var isGoingToDeleteList = mutableStateListOf<Boolean>().apply {
        repeat(9) { add(false) }
    }
    var i = 0
    fun clearGame() {
        xoList.clear()
        xoMap.clear()
        i = 0
        xoList.addAll(List(9) { "" })
        xoMap = (1..7).associateWith { null }.toMutableMap()
    }

    fun performNewMove(index: Int) {
        xoList[index] = if (isTurnX) "X" else "O"
        isGoingToDeleteList[index] = false
        if (i < 7) {
            i++
        }
        xoMap[i] = index

        if (i > 6) {
            xoList[xoMap[1]!!] = ""
            shiftMapValues(map = xoMap)
        }
        if (i > 5) {
            isGoingToDeleteList[xoMap[1]!!] = true
        }
        checkGameStatus()
        isTurnX = !isTurnX
    }

    fun shiftMapValues(map: MutableMap<Int, Int?>) {
        val keys = map.keys.sorted()
        for (i in 0 until keys.size - 1) {
            map[keys[i]] = map[keys[i + 1]]
        }
        map[keys.last()] = null
    }

    fun checkGameStatus() {
        if (xoList[0] == xoList[1] && xoList[1] == xoList[2] && xoList[0].isNotEmpty()) {
            isGameFinished = true

            if (xoList[0] == "X") {
                changeWinnerTitle("X")
            } else {
                changeWinnerTitle("O")
            }
        } else if (xoList[2] == xoList[5] && xoList[5] == xoList[8] && xoList[2].isNotEmpty()) {
            isGameFinished = true

            if (xoList[2] == "X") {
                changeWinnerTitle("X")
            } else {
                changeWinnerTitle("O")
            }
        } else if (xoList[0] == xoList[3] && xoList[3] == xoList[6] && xoList[0].isNotEmpty()) {
            isGameFinished = true

            if (xoList[0] == "X") {
                changeWinnerTitle("X")
            } else {
                changeWinnerTitle("O")
            }
        } else if (xoList[6] == xoList[7] && xoList[7] == xoList[8] && xoList[6].isNotEmpty()) {
            isGameFinished = true

            if (xoList[6] == "X") {
                changeWinnerTitle("X")
            } else {
                changeWinnerTitle("O")
            }
        } else if (xoList[0] == xoList[4] && xoList[4] == xoList[8] && xoList[0].isNotEmpty()) {
            isGameFinished = true

            if (xoList[0] == "X") {
                changeWinnerTitle("X")
            } else {
                changeWinnerTitle("O")
            }
        } else if (xoList[2] == xoList[4] && xoList[4] == xoList[6] && xoList[2].isNotEmpty()) {
            isGameFinished = true

            if (xoList[2] == "X") {
                changeWinnerTitle("X")
            } else {
                changeWinnerTitle("O")
            }
        } else if (xoList[1] == xoList[4] && xoList[4] == xoList[7] && xoList[1].isNotEmpty()) {
            isGameFinished = true

            if (xoList[1] == "X") {
                changeWinnerTitle("X")
            } else {
                changeWinnerTitle("O")
            }
        } else if (xoList[3] == xoList[4] && xoList[4] == xoList[5] && xoList[3].isNotEmpty()) {
            isGameFinished = true

            if (xoList[3] == "X") {
                changeWinnerTitle("X")
            } else {
                changeWinnerTitle("O")
            }
        } else {
            if (xoList.all({ it.isNotEmpty() })) {
                isGameFinished = true
                changeWinnerTitle("draw")
            }
        }
    }

    private fun changeWinnerTitle(winner: String) {
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
