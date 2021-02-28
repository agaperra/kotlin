package com.geekbrains.kotlin_lessons.utils

import com.geekbrains.kotlin_lessons.models.MovieFull

sealed class AppState {
    data class Success(val movieData: List<MovieFull>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}