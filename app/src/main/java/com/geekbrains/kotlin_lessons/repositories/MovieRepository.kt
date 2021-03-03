package com.geekbrains.kotlin_lessons.repositories

import androidx.lifecycle.MutableLiveData
import com.geekbrains.kotlin_lessons.BuildConfig
import com.geekbrains.kotlin_lessons.utils.Constants
import com.geekbrains.kotlin_lessons.network.ApiClient
import com.geekbrains.kotlin_lessons.network.ApiService
import com.geekbrains.kotlin_lessons.responses.MovieResponse
import com.geekbrains.kotlin_lessons.utils.Variables
import retrofit2.Call
import retrofit2.Response

class MovieRepository {
    private var apiService: ApiService = ApiClient.api

    fun getPopularMovies(_observingMovies: MutableLiveData<MovieResponse>) {
        apiService.getPopular(BuildConfig.FILM_API_KEY, Variables.LOCALE, Variables.ADULT).enqueue(object :
                retrofit2.Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                _observingMovies.value = response.body()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _observingMovies.value = null
            }
        })
    }

    fun getLookNowMovies(_observingMovies: MutableLiveData<MovieResponse>) {
        apiService.getLookNow(BuildConfig.FILM_API_KEY, Variables.LOCALE, Variables.ADULT).enqueue(object :
                retrofit2.Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                _observingMovies.value = response.body()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _observingMovies.value = null
            }
        })
    }

    fun getUpComingMovies(_observingMovies: MutableLiveData<MovieResponse>) {
        apiService.getUpComing(BuildConfig.FILM_API_KEY,Variables.LOCALE, Variables.ADULT).enqueue(object :
                retrofit2.Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                _observingMovies.value = response.body()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _observingMovies.value = null
            }
        })

    }


    fun getTopMovies(_observingMovies: MutableLiveData<MovieResponse>) {
        apiService.getTop(BuildConfig.FILM_API_KEY, Variables.LOCALE, Variables.ADULT).enqueue(object :
                retrofit2.Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                _observingMovies.value = response.body()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _observingMovies.value = null
            }
        })
    }

}