package com.geekbrains.kotlin_lessons

import android.annotation.SuppressLint
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

        @SuppressLint("ConstantLocale")
        val locale: String =Locale.getDefault().language
        @SuppressLint("ConstantLocale")
        val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd",Locale.forLanguageTag( locale))
        public var dateFormatYear = java.text.SimpleDateFormat("yyyy",Locale.forLanguageTag( locale))
        const val basicURLSearch="https://api.themoviedb.org"
        const val basicURL="https://api.themoviedb.org"
        const val languageURL="&language="
        const val apiURL="?api_key="+BuildConfig.FILM_API_KEY
        const val imageURL="https://image.tmdb.org/t/p/w400"
        const val queryURL="&query="
        const val pageURL="&page="
        const val popular="/3/movie/popular"
        const val now_playing="/3/movie/now_playing"
        const val upcoming="/3/movie/upcoming"
        const val top="/3/movie/top_rated"
        const val search="/3/search/movie/"
        //var finalURL= basicURLSearch+ apiURL+ languageURL+ queryURL
    }
}