package com.amirali_apps.tictactoe.domain.ai

import com.amirali_apps.tictactoe.models.Move

class TicTacToeAi(private val maxDepth: Int) {
    private fun minimax(
        board: List<MutableList<Char>>,
        depth: Int,
        isAI: Boolean
    ): Int {
        val score = AiUtils.evaluate(board)
        if (depth >= maxDepth) return score
        if (score == 10) return score - depth
        if (score == -10) return score + depth
        if (AiUtils.isMovesLeft(board).not()) return 0


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
}