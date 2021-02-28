package com.geekbrains.kotlin_lessons.repositories

import androidx.lifecycle.MutableLiveData
import com.geekbrains.kotlin_lessons.BuildConfig
import com.geekbrains.kotlin_lessons.utils.Constants
import com.geekbrains.kotlin_lessons.network.ApiClient
import com.geekbrains.kotlin_lessons.network.ApiService
import com.geekbrains.kotlin_lessons.responses.ActorsResponse
import com.geekbrains.kotlin_lessons.responses.MovieResponse
import retrofit2.Call
import retrofit2.Response

class SearchRepository {

    private var apiService: ApiService = ApiClient.api


    fun searchMovies(query: String, _observingMovies: MutableLiveData<MovieResponse>) {
        apiService.searchMovie(
                key = BuildConfig.FILM_API_KEY,
                lang = Constants.LOCALE,
                Constants.ADULT,
                query = query
        )
                .enqueue(object : retrofit2.Callback<MovieResponse> {
                    override fun onResponse(
                            call: Call<MovieResponse>, response: Response<MovieResponse>
                    ) {
                        _observingMovies.value = response.body()
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        _observingMovies.value = null
                    }
                })
    }

    fun searchActors(query: String, _observingActors: MutableLiveData<ActorsResponse>) {
        apiService.searchActor(
                key = BuildConfig.FILM_API_KEY,
                lang = Constants.LOCALE,
                query = query
        )
                .enqueue(object : retrofit2.Callback<ActorsResponse> {
                    override fun onResponse(
                            call: Call<ActorsResponse>, response: Response<ActorsResponse>
                    ) {
                        _observingActors.value = response.body()
                    }

                    override fun onFailure(call: Call<ActorsResponse>, t: Throwable) {
                        _observingActors.value = null
                    }
                })
    }
}