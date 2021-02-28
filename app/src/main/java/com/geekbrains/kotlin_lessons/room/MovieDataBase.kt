package com.geekbrains.kotlin_lessons.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geekbrains.kotlin_lessons.room.dao.*
import com.geekbrains.kotlin_lessons.room.data.*

@Database(entities = [HistoryEntity::class,], version = 1, exportSchema = false)
abstract class MovieDataBase : RoomDatabase() {
//
//    private lateinit var movieDataBase: MovieDataBase
//
//    fun getMovieDataBase(context: Context): MovieDataBase{
//        if (movieDataBase == null) {
//            synchronized(MovieDataBase::class.java) {
//                if (movieDataBase == null) {
//                    movieDataBase= Room.databaseBuilder(
//                       context,
//                        MovieDataBase::class.java,
//                        "MovieDataBase"
//                    ).build()
//                }
//            }
//        }
//        return movieDataBase
//    }

    abstract fun historyDao(): HistoryDao
}
