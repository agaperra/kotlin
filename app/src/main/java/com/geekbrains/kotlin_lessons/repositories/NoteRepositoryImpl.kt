package com.geekbrains.kotlin_lessons.repositories


import com.geekbrains.kotlin_lessons.room.dao.NoteDao
import com.geekbrains.kotlin_lessons.room.data.Note

class NoteRepositoryImpl(private val localDataSource: NoteDao) : NoteRepository {

    override fun getNote(id: Int): Int {
        return localDataSource.getId(id)
    }

    override fun findNote(id: Int): String {
        return convertNoteEntityToString(localDataSource.getDataById(id))
    }

    override fun saveEntity(id: Int, string: String) {
        localDataSource.insert(convertMovieToEntity(id, string))
    }

    override fun deleteEntity(id: Int) {
        localDataSource.drop(id)
    }

    private fun convertNoteEntityToString(entityList: Note): String = entityList.note

    private fun convertMovieToEntity(id: Int, string: String): Note = Note(id, string)
}