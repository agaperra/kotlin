package com.geekbrains.kotlin_lessons.repositories

import androidx.lifecycle.MutableLiveData
import com.geekbrains.kotlin_lessons.BuildConfig
import com.geekbrains.kotlin_lessons.models.ActorFull
import com.geekbrains.kotlin_lessons.models.MovieFull
import com.geekbrains.kotlin_lessons.network.ApiClient
import com.geekbrains.kotlin_lessons.network.ApiService
import com.geekbrains.kotlin_lessons.responses.CastResponse
import com.geekbrains.kotlin_lessons.utils.Variables
import retrofit2.Call
import retrofit2.Response

class ActorsDetailsRepository {

    private var apiService: ApiService = ApiClient.api

    fun getDetailsActor(id: Int, _observing: MutableLiveData<ActorFull>) {
        apiService.showDetailsActor(id, BuildConfig.FILM_API_KEY, Variables.LOCALE).enqueue(object :
            retrofit2.Callback<ActorFull> {
            override fun onResponse(call: Call<ActorFull>, response: Response<ActorFull>) {
                _observing.value = response.body()
            }

            override fun onFailure(call: Call<ActorFull>, t: Throwable) {
                _observing.value = null
            }
        })
    }

}