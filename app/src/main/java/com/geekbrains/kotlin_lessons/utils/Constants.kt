package com.geekbrains.kotlin_lessons.utils

import android.annotation.SuppressLint
import java.util.*


class Constants {

    companion object {
        @SuppressLint("ConstantLocale")
        const val PREF_ADULT: String = "PREF_ADULT"
        const val BASIC_URL: String = "https://api.themoviedb.org"
        const val IMAGE_URL: String = "https://image.tmdb.org/t/p/w400"
        const val POPULAR_MOVIES: String = "/3/movie/popular"
        const val NOW_PLAYING_MOVIES = "/3/movie/now_playing"
        const val UPCOMING_MOVIES = "/3/movie/upcoming"
        const val TOP_MOVIES = "/3/movie/top_rated"
        const val SEARCH_MOVIES = "/3/search/movie/"
        const val SEARCH_ACTORS = "/3/search/person"
        const val ACTOR_PLACE_OF_BIRTH = "ActorPlaceOfBirth"
        const val ACTOR_NAME = "ActorName"
        const val ACTOR_PHOTO = "PhotoPath"

        const val TAG_THEME = "TAG_THEME"
        const val THEME_DARK = 0
        const val THEME_LIGHT = 1
    }
}