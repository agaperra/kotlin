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
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }

            return db!!.historyDao()
        }

        fun getFavoriteDao(): FavoriteDao {
            if (db == null) {
                synchronized(MovieDataBase::class.java) {
                    if (db == null) {
                        if (app == null) throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            app!!.applicationContext,
                            MovieDataBase::class.java,
                            DB_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }

            return db!!.favoriteDao()
        }

        fun getWatchedDao(): WatchedDao {
            if (db == null) {
                synchronized(MovieDataBase::class.java) {
                    if (db == null) {
                        if (app == null) throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            app!!.applicationContext,
                            MovieDataBase::class.java,
                            DB_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }

            return db!!.watchedDao()
        }

        fun getNoteDao(): NoteDao {
            if (db == null) {
                synchronized(MovieDataBase::class.java) {
                    if (db == null) {
                        if (app == null) throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            app!!.applicationContext,
                            MovieDataBase::class.java,
                            DB_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }

            return db!!.noteDao()
        }
    }
}