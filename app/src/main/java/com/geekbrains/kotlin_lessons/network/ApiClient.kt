package com.geekbrains.kotlin_lessons.network

import com.geekbrains.kotlin_lessons.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.basicURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}