package com.geekbrains.kotlin_lessons.room.dao

import androidx.room.*
import com.geekbrains.kotlin_lessons.room.data.Favorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM Favorite")
    fun all(): List<Favorite>


    @Query("SELECT COUNT (id) FROM Favorite WHERE id LIKE :id")
    fun find(id: Int): Int

    @Query("DELETE FROM Favorite WHERE id LIKE :id")
    fun drop(id: Int): Int

    @Query("SELECT * FROM Favorite WHERE id LIKE :id")
    fun getDataById(id: Int): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: Favorite)

    @Update
    fun update(entity: Favorite)

    @Delete
    fun delete(entity: Favorite)
}
