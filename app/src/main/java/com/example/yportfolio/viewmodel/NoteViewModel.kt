package com.example.yportfolio.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.yportfolio.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NoteViewModel : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    val colors = listOf(
        0xFFFFFFFF, // Blanc
        0xFFF8BBD0, // Rose pâle
        0xFFE1BEE7, // Violet clair
        0xFFB3E5FC, // Bleu ciel
        0xFFC8E6C9, // Vert menthe
        0xFFFFF9C4  // Jaune crème
    )

    init {
        // Données de démonstration
        _notes.value = listOf(
            Note(title = "Apprendre Jetpack Compose", content = "C'est vraiment puissant pour l'UI moderne !"),
            Note(title = "Course à faire", content = "Lait, Oeufs, Farine, Café, Chocolat noir."),
            Note(title = "Projet yPortfolio", content = "Finir l'implémentation de la navigation et du thème M3."),
            Note(title = "Idée d'App", content = "Une app de méditation avec des sons de la nature.", color = 0xFFFFD180)
        )
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun addNote(title: String, content: String) {
        _notes.value = _notes.value + Note(title = title, content = content)
    }

    fun updateNote(updatedNote: Note) {
        _notes.value = _notes.value.map { if (it.id == updatedNote.id) updatedNote else it }
    }

    fun deleteNote(noteId: String) {
        _notes.value = _notes.value.filter { it.id != noteId }
    }

    fun getNoteById(id: String): Note? = _notes.value.find { it.id == id }
}