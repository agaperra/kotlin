package com.geekbrains.kotlin_lessons.utils

import android.annotation.SuppressLint
import java.util.*

class Variables {

    companion object{
        var BOOLEAN: Boolean = false
        @SuppressLint("ConstantLocale")
        val LOCALE: String = Locale.getDefault().language
        var ADULT: Boolean = false
    }
}