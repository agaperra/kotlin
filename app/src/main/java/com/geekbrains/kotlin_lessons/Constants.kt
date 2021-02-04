package com.geekbrains.kotlin_lessons

import java.util.*


class Constants {

    enum class THEME{
        THEME_LIGHT,
        THEME_DARK
    }

    enum class TAGS{
        THEME_TAG
    }

    companion object{
        var dateFormat = java.text.SimpleDateFormat("yyy-MM-dd", Locale.forLanguageTag(Locale.getDefault().language))
    }
}