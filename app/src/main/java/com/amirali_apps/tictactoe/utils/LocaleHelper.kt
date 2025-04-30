package com.amirali_apps.tictactoe.utils

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

data class Language(
    val code: String,
    val displayLanguage: String
)

val appLanguages = listOf(
    Language(
        "en",
        "English"
    ), // default language
    Language(
        "fa",
        "farsi"
    ),
)

class AppLocaleManager {
    fun changeLanguage(
        context: Context,
        languageCode: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(languageCode)
        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
        }
    }

    fun getLanguageCode(context: Context): String {
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java)
                ?.applicationLocales
                ?.takeIf { it.isEmpty.not() }
                ?.get(0)
        } else {
            AppCompatDelegate.getApplicationLocales()
                .takeIf { it.isEmpty.not() }
                ?.get(0)
        }
        val languageCode = locale?.language ?: Locale.getDefault().language

        return if (appLanguages.any { it.code == languageCode }) {
            languageCode
        } else {
            getDefaultLanguageCode()
        }
    }

    private fun getDefaultLanguageCode(): String {
        return appLanguages.first().code
    }
}