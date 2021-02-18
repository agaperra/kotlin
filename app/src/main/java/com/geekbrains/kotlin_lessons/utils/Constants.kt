package com.geekbrains.kotlin_lessons.utils

import android.annotation.SuppressLint
import java.util.*


class Constants {

    companion object {

         var boolean: Boolean = false

        @SuppressLint("ConstantLocale")
        val locale: String = Locale.getDefault().language

        @SuppressLint("ConstantLocale")
        const val dateFormatYear = "yyyy"
        const val basicURL = "https://api.themoviedb.org"
        const val imageURL = "https://image.tmdb.org/t/p/w400"
        const val popular_movies = "/3/movie/popular"
        const val now_playing_movies = "/3/movie/now_playing"
        const val upcoming_movies = "/3/movie/upcoming"
        const val top_movies = "/3/movie/top_rated"
        const val search_movies = "/3/search/movie/"
        const val show_details = "/3/movie"
        const val popular_tv = "/3/tv/popular"
        const val now_playing_tv = "/3/tv/airing-today"
        const val on_the_air_tv = "/3/tv/on-the-air"
        const val top_tv = "/3/tv/top-rated"
        const val search_tv = "/3/search/tv"
        const val actors = "/3/search/person"
    }
}