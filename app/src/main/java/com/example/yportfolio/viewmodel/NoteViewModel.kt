package com.example.yportfolio.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yportfolio.data.NoteRepository
import com.example.yportfolio.model.Note
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    // On transforme le Flow du repository en StateFlow pour Compose
    val notes: StateFlow<List<Note>> = repository.allNotes
        .map { list -> 
            list.sortedWith(compareByDescending<Note> { it.isPinned }.thenByDescending { it.timestamp }) 
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repository.insert(Note(title = title, content = content))
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.update(note)
        }
    }

    fun togglePin(note: Note) {
        updateNote(note.copy(isPinned = !note.isPinned))
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.delete(note.id)
        }
    }

    fun restoreNote(note: Note) {
        viewModelScope.launch {
            repository.insert(note)
        }
    }

    suspend fun getNoteById(id: Int): Note? = repository.getNoteById(id)
}