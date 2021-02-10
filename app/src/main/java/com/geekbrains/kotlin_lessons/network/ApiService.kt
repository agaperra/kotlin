package com.geekbrains.kotlin_lessons.network

import com.geekbrains.kotlin_lessons.Constants
import com.geekbrains.kotlin_lessons.models.Movie
import com.geekbrains.kotlin_lessons.models.MovieFull
import com.geekbrains.kotlin_lessons.responses.MovieDetailsResponse
import com.geekbrains.kotlin_lessons.responses.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(value = Constants.popular_movies)
    fun getPopular(
        @Query("api_key") key: String,
        @Query("language") lang: String
    ): Call<MovieResponse>

    @GET(value = Constants.now_playing_movies)
    fun getLookNow(
        @Query("api_key") key: String,
        @Query("language") lang: String
    ): Call<MovieResponse>

    @GET(value = Constants.upcoming_movies)
    fun getUpComing(
        @Query("api_key") key: String,
        @Query("language") lang: String
    ): Call<MovieResponse>

    @GET(value = Constants.top_movies)
    fun getTop(@Query("api_key") key: String, @Query("language") lang: String): Call<MovieResponse>

    @GET(value = Constants.search_movies)
    fun searchMovie(
        @Query("api_key") key: String,
        @Query("language") lang: String,
        @Query("query") query: String
    ): Call<MovieResponse>


    @GET(value = "/3/movie/{id}")
    fun showDetails( @Query("id") id: Int,
            @Query("api_key") key: String,
            @Query("language") lang: String
    ): Call<MovieFull>

}