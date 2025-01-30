package com.example.tictactoe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class GameViewModel(
    private var isPro: Boolean,
    private var isAi: Boolean
) : ViewModel() {
    private var turnNumber = 0
    var isTurnX by mutableStateOf(false)
    var isGameFinished by mutableStateOf(false)
    var winnerTitle by mutableStateOf("")
    var xWins by mutableIntStateOf(0)
    var oWins by mutableIntStateOf(0)
    private var xoQueue: ArrayDeque<Move> = ArrayDeque()
    var xoList by mutableStateOf(
        List(3) { MutableList(3) { '_' } }
    )
    var isGoingToDeleteList by mutableStateOf(
        List(3) { MutableList(3) { false } }
    )
    val Ai = TicTacToeAi()
    suspend fun performNewMove(
        move: Move
    ) {
        xoList[move.row][move.column] = if (isTurnX) 'X' else 'O'

        if (isPro) {
            isGoingToDeleteList[move.row][move.column] = false
            if (turnNumber < 7) {
                turnNumber++
            }
            xoQueue.add(move)
            if (turnNumber > 6) {
                xoList[xoQueue.first().row][xoQueue.first().column] = '_'
                xoQueue.removeFirst()
            }
            if (turnNumber > 5) {
                isGoingToDeleteList[xoQueue.first().row][xoQueue.first().column] = true
            }
        }


        checkGameStatus()
        isTurnX = !isTurnX
        if (isAi) performAiMove()
    }

    private suspend fun performAiMove() {
        if (!isGameFinished) {
            delay(1000)
            val bestMoveAi = Ai.findBestMove(board = xoList)
            xoList[bestMoveAi.row][bestMoveAi.column] = 'X'

            if (isPro) {
                isGoingToDeleteList[bestMoveAi.row][bestMoveAi.column] = false
                if (turnNumber < 7) {
                    turnNumber++
                }
                xoQueue.add(bestMoveAi)
                if (turnNumber > 6) {
                    xoList[xoQueue.first().row][xoQueue.first().column] = '_'
                    xoQueue.removeFirst()
                }
                if (turnNumber > 5) {
                    isGoingToDeleteList[xoQueue.first().row][xoQueue.first().column] = true
                }
            }
            checkGameStatus()
            isTurnX = !isTurnX
        }
    }

    private fun checkGameStatus() {
        for (row in 0..2) {
            if (xoList[row][0] == xoList[row][1] && xoList[row][1] == xoList[row][2] && xoList[row][0] != '_') {
                isGameFinished = true
                placeWinner(xoList[row][0])
                return
            }
        }
        for (col in 0..2) {
            if (xoList[0][col] == xoList[1][col] && xoList[1][col] == xoList[2][col] && xoList[0][col] != '_') {
                isGameFinished = true
                placeWinner(xoList[0][col])
                return
            }
        }
        if (xoList[0][0] == xoList[1][1] && xoList[1][1] == xoList[2][2] && xoList[0][0] != '_') {
            isGameFinished = true
            placeWinner(xoList[0][0])
            return
        }
        if (xoList[0][2] == xoList[1][1] && xoList[1][1] == xoList[2][0] && xoList[0][2] != '_') {
            isGameFinished = true
            placeWinner(xoList[0][2])
            return
        }
        if (xoList.all { row -> row.all { it != '_' } }) {
            isGameFinished = true
            placeWinner(null)
        }
    }

    private fun placeWinner(winner: Char?) {
        when (winner) {
            'X' -> {
                winnerTitle = if (isAi) "You lost :(" else "Winner is X"
                xWins++
            }

            'O' -> {
                winnerTitle = if (isAi) "You won :D" else "Winner is O"
                oWins++
            }

            null -> {
                winnerTitle = "Draw, play again!"
                xWins++
                oWins++
            }
        }
    }

    suspend fun resetGame() {
        isGameFinished = false
        clearGame()
        winnerTitle = ""
        if (isAi && isTurnX) performAiMove()
    }

    private fun clearGame() {
        xoList = List(3) { MutableList(3) { '_' } }
        xoQueue.clear()
        turnNumber = 0
    }
}

class TicTacToeAi {
    private fun minimax(
        board: List<MutableList<Char>>,
        depth: Int,
        isAI: Boolean
    ): Int {
        val score = evaluate(board)
        if (score == 10) return score - depth
        if (score == -10) return score + depth
        if (isMovesLeft(board).not()) return 0


        if (isAI) {
            var best = Int.MIN_VALUE
            for (i in 0..2) {
                for (j in 0..2) {
                    if (board[i][j] == '_') {
                        board[i][j] = 'X'

                        best = maxOf(
                            best,
                            minimax(
                                board,
                                depth + 1,
                                false
                            )
                        )

                        board[i][j] = '_'
                    }
                }
            }
            return best
        } else {
            var best = Int.MAX_VALUE
            for (i in 0..2) {
                for (j in 0..2) {
                    if (board[i][j] == '_') {
                        board[i][j] = 'O'

                        best = minOf(
                            best,
                            minimax(
                                board,
                                depth + 1,
                                true
                            )
                        )

                        board[i][j] = '_'
                    }
                }
            }
            return best
        }
    }

    fun findBestMove(board: List<MutableList<Char>>): Move {
        var bestVal = Int.MIN_VALUE
        var bestMove = Move(
            -1,
            -1
        )

        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == '_') {
                    board[i][j] = 'X'
                    val moveVal = minimax(
                        board,
                        0,
                        false
                    )

                    board[i][j] = '_'

                    if (moveVal > bestVal) {
                        bestMove = Move(
                            i,
                            j
                        )
                        bestVal = moveVal
                    }
                }
            }
        }
        return bestMove
    }

    private fun evaluate(board: List<MutableList<Char>>): Int {
        for (row in 0..2) {
            if (board[row][0] == board[row][1] &&
                board[row][1] == board[row][2]
            ) {
                if (board[row][0] == 'X') return 10
                if (board[row][0] == 'O') return -10
            }
        }
        for (col in 0..2) {
            if (board[0][col] == board[1][col] &&
                board[1][col] == board[2][col]
            ) {
                if (board[0][col] == 'X') return 10
                if (board[0][col] == 'O') return -10
            }
        }
        if (board[0][0] == board[1][1] &&
            board[1][1] == board[2][2]
        ) {
            if (board[0][0] == 'X') return 10
            if (board[0][0] == 'O') return -10
        }
        if (board[0][2] == board[1][1] &&
            board[1][1] == board[2][0]
        ) {
            if (board[0][2] == 'X') return 10
            if (board[0][2] == 'O') return -10
        }

        return 0
    }

    private fun isMovesLeft(board: List<MutableList<Char>>): Boolean {
        for (row in board) {
            for (cell in row) {
                if (cell == '_') return true
            }
        }
        return false
    }
}