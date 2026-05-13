package com.example.yportfolio.data

import com.example.yportfolio.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) = noteDao.insertNote(note)
    suspend fun update(note: Note) = noteDao.updateNote(note)
    suspend fun delete(noteId: Int) {
        val note = noteDao.getNoteById(noteId)
        if (note != null) noteDao.deleteNote(note)
    }
    suspend fun getNoteById(id: Int): Note? = noteDao.getNoteById(id)
}