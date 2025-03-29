package com.amirali_apps.tictactoe.ui.game_mode_selection

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class UiState {
    data object SelectMode : UiState()
    data class Settings(var isAi: Boolean) : UiState()
}

class GameModeSelectionViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.SelectMode)
    val uiState = _uiState.asStateFlow()
    fun onSelectModeSubmitted(isAi: Boolean) {
        _uiState.value = UiState.Settings(isAi)
    }

    fun onBackPressed() {
        _uiState.value = UiState.SelectMode
    }
}