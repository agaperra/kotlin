package com.geekbrains.kotlin_lessons.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geekbrains.kotlin_lessons.room.dao.*
import com.geekbrains.kotlin_lessons.room.data.*

@Database(entities = [HistoryEntity::class, Favorite::class, Watched::class, Note::class], version = 3, exportSchema = false)
abstract class MovieDataBase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun watchedDao(): WatchedDao
    abstract fun noteDao(): NoteDao
}
