package com.geekbrains.kotlin_lessons

import java.util.*

class Constants {

    companion object {
        const val THEME_TAG="TAG_THEME"
        const val THEME_LIGHT=0
        const val THEME_DARK=1
        var dateFormat = java.text.SimpleDateFormat("YYYY-MM-DD",
            Locale.forLanguageTag(Locale.getDefault().language))
    }
}