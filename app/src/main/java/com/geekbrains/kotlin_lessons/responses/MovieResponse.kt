package com.geekbrains.kotlin_lessons.responses

import com.geekbrains.kotlin_lessons.models.Movie

data class MovieResponse(
    val page: Int,
    val total_pages: Int,
    val results: ArrayList<Movie>
)