package com.amirali_apps.tictactoe.ui.game_mode_selection

import AppLocaleManager
import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GameModeSelectionViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) :
    ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.SelectMode)
    val uiState = _uiState.asStateFlow()
    fun onSelectModeSubmitted(isAi: Boolean) {
        _uiState.value = UiState.Settings(isAi)
    }

    fun onBackPressed() {
        _uiState.value = UiState.SelectMode
    }

    private val appLocaleManager = AppLocaleManager()
    private val _settingState = MutableStateFlow(SettingState())
    val settingState: StateFlow<SettingState> = _settingState

    init {
        loadInitialLanguage()
    }

    fun getLanguage(): String {
        _settingState.value =
            _settingState.value.copy(selectedLanguage = appLocaleManager.getLanguageCode(context))
        return appLocaleManager.getLanguageCode(context)
    }

    private fun loadInitialLanguage() {
        val currentLanguage = appLocaleManager.getLanguageCode(context)
        _settingState.value = _settingState.value.copy(selectedLanguage = currentLanguage)
    }

    fun changeLanguage(languageCode: String) {
        appLocaleManager.changeLanguage(
            context,
            languageCode
        )
        _settingState.value = _settingState.value.copy(selectedLanguage = languageCode)
    }
}

data class SettingState(
    val selectedLanguage: String = ""
)

sealed class UiState {
    data object SelectMode : UiState()
    data class Settings(var isAi: Boolean) : UiState()
}
