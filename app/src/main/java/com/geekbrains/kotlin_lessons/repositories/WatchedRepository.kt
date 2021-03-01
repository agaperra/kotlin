package com.geekbrains.kotlin_lessons.repositories

import com.geekbrains.kotlin_lessons.models.MovieFull

interface WatchedRepository {
    fun getWatched(id: Int): Int
    fun saveEntity(id: Int)
    fun deleteEntity(id: Int)
}