package com.amirali_apps.tictactoe.domain.ai

object AiUtils {
    fun evaluate(board: List<MutableList<Char>>): Int {
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

    fun isMovesLeft(board: List<MutableList<Char>>): Boolean {
        for (row in board) {
            for (cell in row) {
                if (cell == '_') return true
            }
        }
        return false
    }
}