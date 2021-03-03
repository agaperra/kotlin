package com.geekbrains.kotlin_lessons.repositories

import com.geekbrains.kotlin_lessons.models.MovieFull

interface LocalRepository {
    fun getAllHistory(): List<MovieFull>
    fun saveEntity(movie: MovieFull)
}