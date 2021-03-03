package com.geekbrains.kotlin_lessons.room.dao

import com.geekbrains.kotlin_lessons.room.data.HistoryEntity
import androidx.room.*


@Dao
interface HistoryDao {
    @Query("SELECT * FROM HistoryEntity ")
    fun all(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE id LIKE :id")
    fun getDataById(id: Int): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: HistoryEntity)

    @Update
    fun update(entity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)
}