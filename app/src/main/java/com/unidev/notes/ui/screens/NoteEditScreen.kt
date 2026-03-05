package com.unidev.notes.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unidev.notes.ui.theme.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    viewModel: NoteViewModel,
    noteId: String? = null,
    onBackClick: () -> Unit
) {
    // Kategori seçimi için yerel state
    var selectedCategoryId by remember { mutableStateOf<String?>(null) }
    
    var showAddCategoryDialog by remember { mutableStateOf(false) }
    var newCategoryName by remember { mutableStateOf("") }

    val categories by viewModel.categories.collectAsState()

    // Ekran açıldığında state'i temizle veya verileri yükle
    LaunchedEffect(noteId) {
        if (noteId == null) {
            viewModel.noteTitle = TextFieldValue("")
            viewModel.noteContent = TextFieldValue("")
            selectedCategoryId = null
        } else {
            val existingNote = viewModel.getNoteById(noteId)
            existingNote?.let {
                viewModel.noteTitle = TextFieldValue(it.title)
                viewModel.noteContent = TextFieldValue(it.content)
                selectedCategoryId = it.categoryId
            }
        }
    }

    if (showAddCategoryDialog) {
        AlertDialog(
            onDismissRequest = { showAddCategoryDialog = false },
            title = { Text("Yeni Kategori Ekle") },
            text = {
                OutlinedTextField(
                    value = newCategoryName,
                    onValueChange = { newCategoryName = it },
                    label = { Text("Kategori Adı") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newCategoryName.isNotBlank()) {
                            viewModel.addCategory(newCategoryName, 0xFF6200EE.toInt())
                            newCategoryName = ""
                            showAddCategoryDialog = false
                        }
                    }
                ) {
                    Text("Ekle")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddCategoryDialog = false }) {
                    Text("İptal")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (noteId == null) "Yeni Not Ekle" else "Notu Düzenle") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (viewModel.noteTitle.text.isNotBlank() || viewModel.noteContent.text.isNotBlank()) {
                                if (noteId == null) {
                                    viewModel.addNote(viewModel.noteTitle.text, viewModel.noteContent.text, selectedCategoryId)
                                } else {
                                    val updatedNote = com.unidev.notes.model.Note(
                                        id = noteId, 
                                        title = viewModel.noteTitle.text, 
                                        content = viewModel.noteContent.text, 
                                        categoryId = selectedCategoryId
                                    )
                                    viewModel.updateNote(updatedNote)
                                }
                                onBackClick()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check, 
                            contentDescription = "Kaydet",
                            tint = if (viewModel.noteTitle.text.isNotBlank() || viewModel.noteContent.text.isNotBlank()) 
                                MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(
                value = viewModel.noteTitle,
                onValueChange = { viewModel.noteTitle = it },
                placeholder = { Text("Başlık", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None, // Türkçe I/İ karmaşasını önlemek için kapatıldı
                    autoCorrect = false, 
                    keyboardType = KeyboardType.Text
                ),
                singleLine = true
            )

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Kategori:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                TextButton(onClick = { showAddCategoryDialog = true }) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Yeni Ekle")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedCategoryId == null,
                    onClick = { selectedCategoryId = null },
                    label = { Text("Genel") }
                )
                categories.forEach { category ->
                    FilterChip(
                        selected = selectedCategoryId == category.id,
                        onClick = { selectedCategoryId = category.id },
                        label = { 
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(category.name)
                                if (selectedCategoryId == category.id) {
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Kategoriyi Sil",
                                        modifier = Modifier
                                            .size(16.dp)
                                            .clickable { viewModel.deleteCategory(category) },
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = viewModel.noteContent,
                onValueChange = { viewModel.noteContent = it },
                placeholder = { Text("Notunuzu yazın...") },
                modifier = Modifier.fillMaxSize(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text
                )
            )
        }
    }
}
