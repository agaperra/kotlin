package com.geekbrains.kotlin_lessons.repositories

import com.geekbrains.kotlin_lessons.models.MovieFull

interface FavoriteRepository {
    fun getAllFavorite(): List<MovieFull>
    fun getFavoriteMovie(id: Int): Int
    fun saveEntity(movie: MovieFull)
    fun deleteEntity(id: Int)
}