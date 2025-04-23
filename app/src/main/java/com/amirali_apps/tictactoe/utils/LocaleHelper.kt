import android.app.LocaleManager
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.core.os.LocaleListCompat

object LocaleHelper {
    fun getSavedLanguage(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(
            "language_prefs",
            MODE_PRIVATE
        )
        return sharedPreferences.getString(
            "language",
            null
        )
    }

    fun saveLanguage(
        context: Context,
        languageCode: String,
    ) {
        val sharedPreferences = context.getSharedPreferences(
            "language_prefs",
            MODE_PRIVATE
        )
        sharedPreferences.edit() {
            putString(
                "language",
                languageCode
            )
            apply()
        }
    }

    fun setLanguage(
        context: Context,
        languageCode: String,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(languageCode)
        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
        }
        saveLanguage(
            context,
            languageCode
        )
    }
}