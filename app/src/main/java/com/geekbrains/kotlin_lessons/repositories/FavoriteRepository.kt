package com.geekbrains.kotlin_lessons.repositories

import com.geekbrains.kotlin_lessons.models.Movie

interface FavoriteRepository {
    fun getAllFavorite(): List<Movie>
    fun saveEntity(movie: Movie)
}