package com.unidev.notes.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val color: Int // Arayüzde kategoriyi temsil edecek renk kodu
)

/**
 * [Note] sınıfı, notlarımızı temsil eder.
 */
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val categoryId: String? = null // Not bir kategoriye ait olabilir
)
