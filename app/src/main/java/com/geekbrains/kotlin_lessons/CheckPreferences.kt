package com.geekbrains.kotlin_lessons

import androidx.appcompat.app.AppCompatDelegate

class CheckPreferences() {

    companion object {
        fun checkTheme(sPrefs: SharedPreferencesManager) {
            val t: Int = sPrefs.retrieveInt(
                Constants.THEME_TAG,
                Constants.THEME_LIGHT
            )
            when (t) {
                0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}