package com.geekbrains.kotlin_lessons.network

import com.geekbrains.kotlin_lessons.Constants
import com.geekbrains.kotlin_lessons.responses.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(value = Constants.popular)
    fun getPopular(@Query("api_key") key: String, @Query("language") lang: String): Call<MovieResponse>

    @GET(value = Constants.now_playing)
    fun getLookNow(@Query("api_key") key: String, @Query("language") lang: String): Call<MovieResponse>

    @GET(value = Constants.upcoming)
    fun getUpComing(@Query("api_key") key: String, @Query("language") lang: String): Call<MovieResponse>

    @GET(value = Constants.top)
    fun getTop(@Query("api_key") key: String, @Query("language") lang: String): Call<MovieResponse>
}