package com.geekbrains.kotlin_lessons.room.dao

import androidx.room.*
import com.geekbrains.kotlin_lessons.room.data.WatchLater

@Dao
interface WatchLaterDao {
    @Query("SELECT * FROM WatchLater")
    fun all(): List<WatchLater>

    @Query("SELECT * FROM WatchLater WHERE id LIKE :id")
    fun getDataById(id: Long): WatchLater

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: WatchLater)

    @Update
    fun update(entity: WatchLater)

    @Delete
    fun delete(entity: WatchLater)
}
