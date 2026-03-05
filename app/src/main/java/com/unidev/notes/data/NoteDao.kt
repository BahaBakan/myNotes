package com.unidev.notes.data

import androidx.room.*
import com.unidev.notes.model.Category
import com.unidev.notes.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object), veritabanına sorgu atmak için kullandığımız arayüzdür.
 */
@Dao
interface NoteDao {

    // Not İşlemleri
    @Query("SELECT * FROM notes ORDER BY timestamp DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    // Kategori İşlemleri
    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: String): Note?
}
