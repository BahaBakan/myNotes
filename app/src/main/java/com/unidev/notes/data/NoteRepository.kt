package com.unidev.notes.data

import com.unidev.notes.model.Category
import com.unidev.notes.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * Repository (Depo), uygulamanın veriye nereden ulaşacağını (veritabanı, internet vb.)
 * yöneten merkezi bir sınıftır. ViewModel sadece Repository ile konuşur.
 */
class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()
    val allCategories: Flow<List<Category>> = noteDao.getAllCategories()

    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    suspend fun insertCategory(category: Category) = noteDao.insertCategory(category)

    suspend fun deleteCategory(category: Category) = noteDao.deleteCategory(category)

    suspend fun updateNote(note: Note) = noteDao.updateNote(note)

    suspend fun getNoteById(id: String): Note? = noteDao.getNoteById(id)
}
