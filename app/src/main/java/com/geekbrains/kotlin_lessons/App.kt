package com.geekbrains.kotlin_lessons

import android.app.Application
import androidx.room.Room
import com.geekbrains.kotlin_lessons.room.MovieDataBase
import com.geekbrains.kotlin_lessons.room.dao.*


class App : Application() {

    override fun onCreate() {
        app =this
        super.onCreate()
    }


    companion object {

        private var app: App? = null
        private var db: MovieDataBase? = null
        private const val DB_NAME = "Movie.db"

        fun getHistoryDao(): HistoryDao {
            if (db == null) {
                synchronized(MovieDataBase::class.java) {
                    if (db == null) {
                        if (app == null) throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            app!!.applicationContext,
                            MovieDataBase::class.java,
                            DB_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }

            return db!!.historyDao()
        }
    }
}