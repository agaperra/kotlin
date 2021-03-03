package com.geekbrains.kotlin_lessons.room.dao

import androidx.room.*
import com.geekbrains.kotlin_lessons.room.data.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note")
    fun all(): List<Note>

    @Query("SELECT * FROM Note WHERE id LIKE :id")
    fun getDataById(id: Int): Note

    @Query("SELECT COUNT (id) FROM Note WHERE id LIKE :id")
    fun getId(id: Int): Int


    @Query("DELETE FROM Note WHERE id LIKE :id")
    fun drop(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: Note)

    @Update
    fun update(entity: Note)

    @Delete
    fun delete(entity: Note)
}