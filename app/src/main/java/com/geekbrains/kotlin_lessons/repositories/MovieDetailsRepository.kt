package com.geekbrains.kotlin_lessons.repositories

import androidx.lifecycle.MutableLiveData
import com.geekbrains.kotlin_lessons.BuildConfig
import com.geekbrains.kotlin_lessons.Constants
import com.geekbrains.kotlin_lessons.models.MovieFull
import com.geekbrains.kotlin_lessons.network.ApiClient
import com.geekbrains.kotlin_lessons.network.ApiService
import com.geekbrains.kotlin_lessons.responses.ActorsResponse
import retrofit2.Call
import retrofit2.Response

class MovieDetailsRepository {

    private var apiService: ApiService = ApiClient.api


    fun getDetailsMovie(id: Int, _observingMovie: MutableLiveData<MovieFull>) {
        apiService.showDetails(id, BuildConfig.FILM_API_KEY, Constants.locale).enqueue(object :
                retrofit2.Callback<MovieFull> {
            override fun onResponse(call: Call<MovieFull>, response: Response<MovieFull>) {
                _observingMovie.value = response.body()
            }

            override fun onFailure(call: Call<MovieFull>, t: Throwable) {
                _observingMovie.value=null
            }
        })
    }

    fun getPeople(id: Int, _observingMovie: MutableLiveData<ActorsResponse>) {
        apiService.showDetailsPeople(id, BuildConfig.FILM_API_KEY, Constants.locale).enqueue(object :
                retrofit2.Callback<ActorsResponse> {
            override fun onResponse(call: Call<ActorsResponse>, response: Response<ActorsResponse>) {
                _observingMovie.value = response.body()
            }

            override fun onFailure(call: Call<ActorsResponse>, t: Throwable) {
                _observingMovie.value=null
            }
        })
    }
}