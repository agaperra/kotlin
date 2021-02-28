package com.geekbrains.kotlin_lessons.room.dao

import androidx.room.*
import com.geekbrains.kotlin_lessons.room.data.Favorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM Favorite")
    fun all(): List<Favorite>

    @Query("SELECT * FROM Favorite WHERE id LIKE :id")
    fun getDataById(id: Long): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: Favorite)

    @Update
    fun update(entity: Favorite)

    @Delete
    fun delete(entity: Favorite)
}
