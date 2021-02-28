package com.geekbrains.kotlin_lessons.room.dao

import androidx.room.*
import com.geekbrains.kotlin_lessons.room.data.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note")
    fun all(): List<Note>

    @Query("SELECT note FROM Note WHERE id LIKE :id")
    fun getDataById(id: Long): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: Note)

    @Update
    fun update(entity: Note)

    @Delete
    fun delete(entity: Note)
}