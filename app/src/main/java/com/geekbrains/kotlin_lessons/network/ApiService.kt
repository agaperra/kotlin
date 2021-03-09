package com.geekbrains.kotlin_lessons.network

import com.geekbrains.kotlin_lessons.models.ActorFull
import com.geekbrains.kotlin_lessons.utils.Constants
import com.geekbrains.kotlin_lessons.models.MovieFull
import com.geekbrains.kotlin_lessons.responses.ActorsResponse
import com.geekbrains.kotlin_lessons.responses.CastResponse
import com.geekbrains.kotlin_lessons.responses.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(value = Constants.POPULAR_MOVIES)
    fun getPopular(
            @Query("api_key") key: String,
            @Query("language") lang: String,
            @Query("adult") adult: Boolean
    ): Call<MovieResponse>

    @GET(value = Constants.NOW_PLAYING_MOVIES)
    fun getLookNow(
            @Query("api_key") key: String,
            @Query("language") lang: String,
            @Query("adult") adult: Boolean
    ): Call<MovieResponse>

    @GET(value = Constants.UPCOMING_MOVIES)
    fun getUpComing(
            @Query("api_key") key: String,
            @Query("language") lang: String,
            @Query("adult") adult: Boolean
    ): Call<MovieResponse>

    @GET(value = Constants.TOP_MOVIES)
    fun getTop(@Query("api_key") key: String,
               @Query("language") lang: String,
               @Query("adult") adult: Boolean
    ): Call<MovieResponse>

    @GET(value = Constants.SEARCH_MOVIES)
    fun searchMovie(
            @Query("api_key") key: String,
            @Query("language") lang: String,
            @Query("adult") adult: Boolean,
            @Query("query") query: String
    ): Call<MovieResponse>

    @GET(value = Constants.SEARCH_ACTORS)
    fun searchActor(
            @Query("api_key") key: String,
            @Query("language") lang: String,
            @Query("query") query: String
    ): Call<ActorsResponse>


    @GET(value = "/3/movie/{id}")
    fun showDetails(@Path("id") id: Int,
                    @Query("api_key") key: String,
                    @Query("language") lang: String
    ): Call<MovieFull>

    @GET(value = "/3/person/{id}")
    fun showDetailsActor(@Path("id") id: Int,
                    @Query("api_key") key: String,
                    @Query("language") lang: String
    ): Call<ActorFull>

    @GET(value = "/3/movie/{id}/credits")
    fun showDetailsPeople(@Path("id") id: Int,
                          @Query("api_key") key: String,
                          @Query("language") lang: String
    ): Call<CastResponse>

}