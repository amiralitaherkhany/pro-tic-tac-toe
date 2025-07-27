package com.amirali_apps.tictactoe.ui.game

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.amirali_apps.tictactoe.R
import com.amirali_apps.tictactoe.domain.ai.ProTicTacToeAi
import com.amirali_apps.tictactoe.domain.ai.TicTacToeAi
import com.amirali_apps.tictactoe.models.Move
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

enum class AiLevel(
    val depth: Int,
    @StringRes val stringResources: Int
) {
    EASY(
        2,
        R.string.easy
    ),
    MEDIUM(
        4,
        R.string.medium
    ),
    HARD(
        8,
        R.string.hard
    ),
}

@HiltViewModel
class GameViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val isPro = savedStateHandle.get<Boolean>("isPro")!!
    val isAi = savedStateHandle.get<Boolean>("isAi")!!
    private val aiLevel: AiLevel? =
        if (savedStateHandle.get<Boolean>("isAi")!!) AiLevel.entries[savedStateHandle.get<Int>("level")!!] else null

    ///
    private var _turnNumber = 0
    private var _xoQueue: ArrayDeque<Move> = ArrayDeque()

    ///
    private val _isTurnX = MutableStateFlow(false)
    val isTurnX = _isTurnX.asStateFlow()
    private val _isGameFinished = MutableStateFlow(false)
    val isGameFinished = _isGameFinished.asStateFlow()
    private val _winnerTitle = MutableStateFlow<Int?>(null)
    val winnerTitle = _winnerTitle.asStateFlow()
    private val _xWins = MutableStateFlow(0)
    val xWins = _xWins.asStateFlow()
    private val _oWins = MutableStateFlow(0)
    val oWins = _oWins.asStateFlow()
    private val _xoList = MutableStateFlow(List(3) { MutableList(3) { '_' } })
    val xoList = _xoList.asStateFlow()
    private val _isGoingToDeleteList = MutableStateFlow(List(3) { MutableList(3) { false } })
    val isGoingToDeleteList = _isGoingToDeleteList.asStateFlow()

    ///
    suspend fun performNewMove(
        move: Move
    ) {
        updateXOList(
            row = move.row,
            col = move.column,
            value = if (_isTurnX.value) 'X' else 'O'
        )


        if (isPro) {
            updateIsGoingToDeleteList(
                row = move.row,
                col = move.column,
                value = false,
            )

            if (_turnNumber < 7) {
                _turnNumber++
            }
            _xoQueue.add(move)
            if (_turnNumber > 6) {
                updateXOList(
                    row = _xoQueue.first().row,
                    col = _xoQueue.first().column,
                    value = '_'
                )
                _xoQueue.removeFirst()
            }
            if (_turnNumber > 5) {
                updateIsGoingToDeleteList(
                    row = _xoQueue.first().row,
                    col = _xoQueue.first().column,
                    value = true,
                )
            }
        }


        checkGameStatus()
        _isTurnX.value = !_isTurnX.value
        if (isAi) performAiMove()
    }

    private suspend fun performAiMove() {
        if (!_isGameFinished.value) {
            delay(1000)
            val bestMoveAi = withContext(Dispatchers.Default) {
                if (isPro) ProTicTacToeAi(
                    numberOfMoves = _turnNumber,
                    moves = _xoQueue,
                    board = _xoList.value,
                    maxDepth = aiLevel!!.depth
                ).findBestMove() else TicTacToeAi(maxDepth = aiLevel!!.depth).findBestMove(board = _xoList.value)
            }
            updateXOList(
                row = bestMoveAi.row,
                col = bestMoveAi.column,
                value = 'X'
            )


            if (isPro) {
                updateIsGoingToDeleteList(
                    row = bestMoveAi.row,
                    col = bestMoveAi.column,
                    value = false,
                )
                if (_turnNumber < 7) {
                    _turnNumber++
                }
                _xoQueue.add(bestMoveAi)
                if (_turnNumber > 6) {
                    updateXOList(
                        row = _xoQueue.first().row,
                        col = _xoQueue.first().column,
                        value = '_'
                    )
                    _xoQueue.removeFirst()
                }
                if (_turnNumber > 5) {
                    updateIsGoingToDeleteList(
                        row = _xoQueue.first().row,
                        col = _xoQueue.first().column,
                        value = true,
                    )
                }
            }
            checkGameStatus()
            _isTurnX.value = !_isTurnX.value
        }
    }

    private fun updateXOList(
        row: Int,
        col: Int,
        value: Char
    ) {
        val currentList = _xoList.value.map { it.toMutableList() }
        currentList[row][col] = value
        _xoList.value = currentList
    }

    private fun updateIsGoingToDeleteList(
        row: Int,
        col: Int,
        value: Boolean
    ) {
        val currentList = _isGoingToDeleteList.value.map { it.toMutableList() }
        currentList[row][col] = value
        _isGoingToDeleteList.value = currentList
    }

    private fun checkGameStatus() {
        for (row in 0..2) {
            if (_xoList.value[row][0] == _xoList.value[row][1] && _xoList.value[row][1] == _xoList.value[row][2] && _xoList.value[row][0] != '_') {
                _isGameFinished.value = true
                placeWinner(_xoList.value[row][0])
                return
            }
        }
        for (col in 0..2) {
            if (_xoList.value[0][col] == _xoList.value[1][col] && _xoList.value[1][col] == _xoList.value[2][col] && _xoList.value[0][col] != '_') {
                _isGameFinished.value = true
                placeWinner(_xoList.value[0][col])
                return
            }
        }
        if (_xoList.value[0][0] == _xoList.value[1][1] && _xoList.value[1][1] == _xoList.value[2][2] && _xoList.value[0][0] != '_') {
            _isGameFinished.value = true
            placeWinner(_xoList.value[0][0])
            return
        }
        if (_xoList.value[0][2] == _xoList.value[1][1] && _xoList.value[1][1] == _xoList.value[2][0] && _xoList.value[0][2] != '_') {
            _isGameFinished.value = true
            placeWinner(_xoList.value[0][2])
            return
        }
        if (_xoList.value.all { row -> row.all { it != '_' } }) {
            _isGameFinished.value = true
            placeWinner(null)
        }
    }

    private fun placeWinner(winner: Char?) {
        when (winner) {
            'X' -> {
                _winnerTitle.value = if (isAi) R.string.you_lost else R.string.winner_is_x
                _xWins.value++
            }

            'O' -> {
                _winnerTitle.value = if (isAi) R.string.you_won_d else R.string.winner_is_o
                _oWins.value++
            }

            null -> {
                _winnerTitle.value = R.string.draw_play_again
                _xWins.value++
                _oWins.value++
            }
        }
    }

    suspend fun resetGame() {
        _isGameFinished.value = false
        clearGame()
        _winnerTitle.value = null
        if (isAi && _isTurnX.value) performAiMove()
    }

    private fun clearGame() {
        _xoList.value = MutableList(3) { MutableList(3) { '_' } }
        _xoQueue.clear()
        _turnNumber = 0
    }
}
