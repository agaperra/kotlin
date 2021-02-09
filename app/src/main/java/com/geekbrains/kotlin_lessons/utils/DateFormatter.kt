package com.geekbrains.kotlin_lessons.utils

import com.geekbrains.kotlin_lessons.Constants.Companion.dateFormatYear

class DateFormatter {
    var INSTANCE: DateFormatter? = null

    companion object {
        @JvmStatic
        fun getDate(date: String?): String {
            return dateFormatYear.format(date)
        }
    }
}