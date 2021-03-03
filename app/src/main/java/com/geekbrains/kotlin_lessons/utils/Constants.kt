package com.geekbrains.kotlin_lessons.utils

import android.annotation.SuppressLint
import java.util.*


class Constants {

    companion object {

        var BOOLEAN: Boolean = false

        var TIME_EXIT: Int = 2000

        @SuppressLint("ConstantLocale")
        val LOCALE: String = Locale.getDefault().language
        var ADULT: Boolean = false
        lateinit var sPrefs: SharedPreferencesManager
        const val PREF_ADULT: String = "PREF_ADULT"
        const val BASIC_URL: String = "https://api.themoviedb.org"
        const val IMAGE_URL: String = "https://image.tmdb.org/t/p/w400"
        const val POPULAR_MOVIES: String = "/3/movie/popular"
        const val NOW_PLAYING_MOVIES = "/3/movie/now_playing"
        const val UPCOMING_MOVIES = "/3/movie/upcoming"
        const val TOP_MOVIES = "/3/movie/top_rated"
        const val SEARCH_MOVIES = "/3/search/movie/"
        const val SEARCH_ACTORS = "/3/search/person"
    }
}