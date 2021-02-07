package com.geekbrains.kotlin_lessons.repositories

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekbrains.kotlin_lessons.BuildConfig
import com.geekbrains.kotlin_lessons.Constants
import com.geekbrains.kotlin_lessons.network.ApiClient
import com.geekbrains.kotlin_lessons.network.ApiService
import com.geekbrains.kotlin_lessons.responses.MovieResponse
import retrofit2.Call
import retrofit2.Response

class MovieRepository {
    private var apiService: ApiService = ApiClient.api

    fun getPopularMovies(): LiveData<MovieResponse> {
        val data= MutableLiveData<MovieResponse>()

        apiService.getPopular(BuildConfig.FILM_API_KEY,Constants.locale).enqueue(object :
            retrofit2.Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, @NonNull response: Response<MovieResponse>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<MovieResponse>, @NonNull t: Throwable) {

            }
        })
        return data
    }

    fun getLookNowMovies(): LiveData<MovieResponse> {
        val data= MutableLiveData<MovieResponse>()

        apiService.getLookNow(BuildConfig.FILM_API_KEY,Constants.locale).enqueue(object :
            retrofit2.Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, @NonNull response: Response<MovieResponse>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<MovieResponse>, @NonNull t: Throwable) {

            }
        })
        return data
    }

    fun getUpComingMovies(): LiveData<MovieResponse> {
        val data= MutableLiveData<MovieResponse>()

        apiService.getUpComing(BuildConfig.FILM_API_KEY,Constants.locale).enqueue(object :
            retrofit2.Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, @NonNull response: Response<MovieResponse>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<MovieResponse>, @NonNull t: Throwable) {

            }
        })
        return data
    }


    fun getTopMovies(): LiveData<MovieResponse> {
        val data= MutableLiveData<MovieResponse>()

        apiService.getTop(BuildConfig.FILM_API_KEY,Constants.locale).enqueue(object :
            retrofit2.Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, @NonNull response: Response<MovieResponse>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<MovieResponse>, @NonNull t: Throwable) {

            }
        })
        return data
    }

}