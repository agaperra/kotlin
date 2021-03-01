package com.geekbrains.kotlin_lessons.repositories


interface NoteRepository {
    fun getNote(id :Int): Int
    fun findNote(id :Int): String
    fun saveEntity(id: Int, string: String)
    fun deleteEntity(id: Int)
}