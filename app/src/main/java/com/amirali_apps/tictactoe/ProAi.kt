package com.amirali_apps.tictactoe

class TicTacToeProAi(
    var board: List<MutableList<Char>>,
    var numberOfMoves: Int,
    var moves: ArrayDeque<Move>,
    private val maxDepth: Int
) {
    private fun minimax(
        inputBoard: List<MutableList<Char>>,
        inputMoves: ArrayDeque<Move>,
        depth: Int,
        isAI: Boolean
    ): Int {
        var myBoard = deepCopyBoard(inputBoard)
        var myMoves = deepCopyMoves(inputMoves)
        val score = evaluate(myBoard)
        if (depth >= maxDepth) return score
        if (score == 10) return score - depth
        if (score == -10) return score + depth
        if (isMovesLeft(myBoard).not()) return 0


        if (isAI) {
            var best = Int.MIN_VALUE
            for (i in 0..2) {
                for (j in 0..2) {
                    if (myBoard[i][j] == '_') {
                        myBoard[i][j] = 'X'
                        numberOfMoves++
                        myMoves.add(
                            Move(
                                i,
                                j
                            )
                        )
                        if (numberOfMoves > 6) {
                            val deletedMove = myMoves[0]
                            myBoard[deletedMove.row][deletedMove.column] = '_'
                            myMoves.removeFirst()
                        }
                        best = maxOf(
                            best,
                            minimax(
                                myBoard,
                                myMoves,
                                depth + 1,
                                false
                            )
                        )
                        myBoard = deepCopyBoard(inputBoard)
                        myMoves = deepCopyMoves(inputMoves)
                        numberOfMoves--
                    }
                }
            }
            return best
        } else {
            var best = Int.MAX_VALUE
            for (i in 0..2) {
                for (j in 0..2) {
                    if (myBoard[i][j] == '_') {
                        myBoard[i][j] = 'O'
                        numberOfMoves++
                        myMoves.add(
                            Move(
                                i,
                                j
                            )
                        )
                        if (numberOfMoves > 6) {
                            val deletedMove = myMoves[0]
                            myBoard[deletedMove.row][deletedMove.column] = '_'
                            myMoves.removeFirst()
                        }
                        best = minOf(
                            best,
                            minimax(
                                myBoard,
                                myMoves,
                                depth + 1,
                                true
                            )
                        )
                        myBoard = deepCopyBoard(inputBoard)
                        myMoves = deepCopyMoves(inputMoves)
                        numberOfMoves--
                    }
                }
            }
            return best
        }
    }

    fun findBestMove(): Move {
        var myBoard = deepCopyBoard(board)
        var myMoves = deepCopyMoves(moves)
        var bestVal = Int.MIN_VALUE
        var bestMove = Move(
            -1,
            -1
        )

        for (i in 0..2) {
            for (j in 0..2) {
                if (myBoard[i][j] == '_') {
                    myBoard[i][j] = 'X'
                    numberOfMoves++
                    myMoves.add(
                        Move(
                            i,
                            j
                        )
                    )
                    if (numberOfMoves > 6) {
                        val deletedMove = myMoves[0]
                        myBoard[deletedMove.row][deletedMove.column] = '_'
                        myMoves.removeFirst()
                    }
                    val moveVal = minimax(
                        myBoard,
                        myMoves,
                        0,
                        false
                    )
                    myBoard = deepCopyBoard(board)
                    myMoves = deepCopyMoves(moves)
                    numberOfMoves--

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
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                if (board[row][0] == 'X') return 10
                if (board[row][0] == 'O') return -10
            }
        }
        for (col in 0..2) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                if (board[0][col] == 'X') return 10
                if (board[0][col] == 'O') return -10
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == 'X') return 10
            if (board[0][0] == 'O') return -10
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
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

    private fun deepCopyBoard(board: List<MutableList<Char>>): List<MutableList<Char>> {
        return board.map { it.toMutableList() }
    }

    private fun deepCopyMoves(moves: ArrayDeque<Move>): ArrayDeque<Move> {
        return ArrayDeque(moves.map {
            Move(
                it.row,
                it.column
            )
        })
    }
}