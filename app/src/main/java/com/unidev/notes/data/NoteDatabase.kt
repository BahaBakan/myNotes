package com.unidev.notes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.unidev.notes.model.Category
import com.unidev.notes.model.Note


@Database(entities = [Note::class, Category::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
