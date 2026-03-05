package com.unidev.notes.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unidev.notes.data.NoteRepository
import com.unidev.notes.model.Category
import com.unidev.notes.model.Note
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel, kullanıcı arayüzü (UI) verilerini tutan ve yöneten sınıftır.
 * Telefon ekranı döndüğünde verilerin kaybolmamasını sağlar.
 */
class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    
    // Giriş state'lerini TextFieldValue yapıyoruz (Klavye imleci ve karakter takılmalarını kökten çözmek için)
    var noteTitle by mutableStateOf(TextFieldValue(""))
    var noteContent by mutableStateOf(TextFieldValue(""))

    // Veritabanındaki tüm notları bir "StateFlow" (Veri Akışı) olarak arayüze sunuyoruz.
    val notes: StateFlow<List<Note>> = repository.allNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val categories: StateFlow<List<Category>> = repository.allCategories
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Yeni bir not eklemek için bu fonksiyonu çağıracağız.
    fun addNote(title: String, content: String, categoryId: String? = null) {
        viewModelScope.launch {
            val newNote = Note(title = title, content = content, categoryId = categoryId)
            repository.insertNote(newNote)
        }
    }

    // Bir notu silmek için.
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    // Var olan bir notu güncellemek için.
    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }

    // Belirli bir notu ID ile getirmek için (Düzenleme ekranı için).
    suspend fun getNoteById(id: String): Note? {
        return repository.getNoteById(id)
    }

    // Kategori eklemek için.
    fun addCategory(name: String, color: Int) {
        viewModelScope.launch {
            // Aynı isimde kategori var mı kontrolü (Basit bir önlem)
            val current = repository.allCategories.first()
            if (current.none { it.name.equals(name, ignoreCase = true) }) {
                val category = Category(name = name, color = color)
                repository.insertCategory(category)
            }
        }
    }

    // Kategori silmek için.
    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            // Önce bu kategoriye bağlı notları "Genel"e çekelim (opsiyonel ama iyi bir pratik)
            val linkedNotes = notes.value.filter { it.categoryId == category.id }
            linkedNotes.forEach { note ->
                repository.updateNote(note.copy(categoryId = null))
            }
            // Sonra kategoriyi silelim
            repository.deleteCategory(category)
        }
    }

    init {
        viewModelScope.launch {
            try {
                // Veritabanından veriyi akış olarak (Flow) alıp kontrol ediyoruz.
                // firstOrNull() yerine ilk yayını alıp işlemi tamamlıyoruz.
                repository.allCategories.take(1).collect { current ->
                    if (current.isEmpty()) {
                        repository.insertCategory(Category(id = "cat_is", name = "İş", color = 0xFF6200EE.toInt()))
                        repository.insertCategory(Category(id = "cat_kisisel", name = "Kişisel", color = 0xFF03DAC5.toInt()))
                        repository.insertCategory(Category(id = "cat_fikirler", name = "Fikirler", color = 0xFFFF0266.toInt()))
                    }
                }
            } catch (e: Exception) {
                // Hata durumunda uygulama kilitlenmeden devam eder.
            }
        }
    }
}
