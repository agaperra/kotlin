package com.geekbrains.kotlin_lessons.responses

import com.geekbrains.kotlin_lessons.models.Movie
import java.io.Serializable

data class MovieResponse(
    val page: Int,
    val total_pages: Int,
    val results: ArrayList<Movie>
) : Serializable