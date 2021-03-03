package com.geekbrains.kotlin_lessons.room.dao

import androidx.room.*
import com.geekbrains.kotlin_lessons.room.data.Watched

@Dao
interface WatchedDao {
    @Query("SELECT * FROM Watched")
    fun all(): List<Watched>

    @Query("SELECT COUNT (id) FROM Watched WHERE id LIKE :id")
    fun getDataById(id: Int): Int

    @Query("DELETE FROM Watched WHERE id LIKE :id")
    fun drop(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: Watched)

    @Update
    fun update(entity: Watched)

    @Delete
    fun delete(entity: Watched)
}