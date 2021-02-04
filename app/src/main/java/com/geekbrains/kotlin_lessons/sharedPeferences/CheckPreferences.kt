package com.geekbrains.kotlin_lessons.sharedPeferences

import androidx.appcompat.app.AppCompatDelegate
import com.geekbrains.kotlin_lessons.Constants

class CheckPreferences() {

    companion object {
        fun checkTheme(sPrefs: SharedPreferencesManager) {
            val t: String = sPrefs.retrieveString(
                    Constants.TAGS.THEME_TAG.toString(),
                    Constants.THEME.THEME_LIGHT.toString()
            )
            when (t) {
                Constants.THEME.THEME_LIGHT.toString() -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Constants.THEME.THEME_DARK.toString() -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}