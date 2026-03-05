package com.unidev.notes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unidev.notes.model.Note
import com.unidev.notes.ui.theme.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    viewModel: NoteViewModel,
    onNoteEditClick: (String) -> Unit,
    onAddNoteClick: () -> Unit
) {
    val notes by viewModel.notes.collectAsState()
    val categories by viewModel.categories.collectAsState()
    
    // Filtreleme için seçili kategori ID'si
    var selectedCategoryId by remember { mutableStateOf<String?>(null) }

    // Filtrelenmiş notlar
    val filteredNotes = if (selectedCategoryId == null) {
        notes
    } else {
        notes.filter { it.categoryId == selectedCategoryId }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Unidev Notes") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNoteClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Not Ekle")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Kategori Filtreleme Barı
            ScrollableTabRow(
                selectedTabIndex = if (selectedCategoryId == null) 0 else 
                    categories.indexOfFirst { it.id == selectedCategoryId } + 1,
                edgePadding = 16.dp,
                divider = {},
                containerColor = Color.Transparent
            ) {
                Tab(
                    selected = selectedCategoryId == null,
                    onClick = { selectedCategoryId = null },
                    text = { Text("Hepsi") }
                )
                categories.forEach { category ->
                    Tab(
                        selected = selectedCategoryId == category.id,
                        onClick = { selectedCategoryId = category.id },
                        text = { Text(category.name) }
                    )
                }
            }

            if (filteredNotes.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background), 
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Henüz bir not eklenmemiş.\nNot eklemek için + butonuna basın.", 
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredNotes) { note ->
                        val category = categories.find { it.id == note.categoryId }
                        NoteItem(
                            note = note,
                            categoryName = category?.name,
                            categoryColor = category?.color,
                            onNoteClick = { onNoteEditClick(note.id) },
                            onDeleteClick = { viewModel.deleteNote(note) }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteItem(
    note: Note,
    categoryName: String?,
    categoryColor: Int?,
    onNoteClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        onClick = onNoteClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    if (categoryName != null) {
                        Text(
                            text = categoryName.uppercase(java.util.Locale("tr")),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(categoryColor ?: 0xFF6200EE.toInt()),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                    Text(text = note.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Sil", tint = Color.LightGray)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
                maxLines = 3,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}
