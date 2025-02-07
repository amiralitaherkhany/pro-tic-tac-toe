package com.example.tictactoe

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