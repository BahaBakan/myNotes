package com.unidev.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.unidev.notes.data.NoteDatabase
import com.unidev.notes.data.NoteRepository
import com.unidev.notes.ui.theme.NoteViewModel
import com.unidev.notes.ui.screens.NoteEditScreen
import com.unidev.notes.ui.screens.NoteListScreen
import com.unidev.notes.ui.theme.UnidevnotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // En başta sistem dilini Türkçe'ye zorluyoruz (Klavye ve Karakter uyumluluğu için)
        val locale = java.util.Locale("tr", "TR")
        java.util.Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java, 
            "notes-db"
        ).fallbackToDestructiveMigration().build()

        val repository = NoteRepository(db.noteDao())
        
        val viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return NoteViewModel(repository) as T
            }
        }

        setContent {
            UnidevnotesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val noteViewModel: NoteViewModel = viewModel(factory = viewModelFactory)

                    NavHost(navController = navController, startDestination = "list") {
                        composable("list") {
                            NoteListScreen(
                                viewModel = noteViewModel,
                                onAddNoteClick = { navController.navigate("edit") },
                                onNoteEditClick = { noteId -> 
                                    navController.navigate("edit?noteId=$noteId") 
                                }
                            )
                        }
                        composable(
                            route = "edit?noteId={noteId}",
                            arguments = listOf(
                                androidx.navigation.navArgument("noteId") {
                                    type = androidx.navigation.NavType.StringType
                                    nullable = true
                                    defaultValue = null
                                }
                            )
                        ) { backStackEntry ->
                            val noteId = backStackEntry.arguments?.getString("noteId")
                            NoteEditScreen(
                                viewModel = noteViewModel,
                                noteId = noteId,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
