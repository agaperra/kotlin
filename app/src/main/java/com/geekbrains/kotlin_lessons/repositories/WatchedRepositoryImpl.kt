package com.geekbrains.kotlin_lessons.repositories

import com.geekbrains.kotlin_lessons.room.dao.NoteDao
import com.geekbrains.kotlin_lessons.room.dao.WatchedDao
import com.geekbrains.kotlin_lessons.room.data.Note
import com.geekbrains.kotlin_lessons.room.data.Watched

class WatchedRepositoryImpl (private val localDataSource: WatchedDao) : WatchedRepository {

    override fun getWatched(id: Int): Int {
        return localDataSource.getDataById(id)
    }

    override fun saveEntity(id: Int) {
        localDataSource.insert(convertMovieToEntity(id))
    }

    override fun deleteEntity(id: Int) {
        localDataSource.drop(id)
    }


    private fun convertMovieToEntity(id: Int): Watched = Watched(id)
}