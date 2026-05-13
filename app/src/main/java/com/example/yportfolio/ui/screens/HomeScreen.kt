package com.example.yportfolio.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.yportfolio.ui.components.ModernSearchBar
import com.example.yportfolio.ui.components.NoteCard
import com.example.yportfolio.viewmodel.NoteViewModel

@Composable
fun HomeScreen(viewModel: NoteViewModel, onNoteClick: (String) -> Unit, onAddNoteClick: () -> Unit) {
    val notes by viewModel.notes.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery
    val filteredNotes = notes.filter { it.title.contains(searchQuery, true) || it.content.contains(searchQuery, true) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNoteClick, shape = RoundedCornerShape(16.dp)) {
                Icon(Icons.Default.Add, "Ajouter")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            ModernSearchBar(searchQuery, viewModel::onSearchQueryChange)
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(filteredNotes) { note ->
                    NoteCard(note) { onNoteClick(note.id) }
                }
            }
        }
    }
}