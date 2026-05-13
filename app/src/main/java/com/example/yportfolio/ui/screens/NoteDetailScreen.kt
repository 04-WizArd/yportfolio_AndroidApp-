package com.example.yportfolio.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack  // Import standard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.yportfolio.viewmodel.NoteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(noteId: String?, viewModel: NoteViewModel, onBack: () -> Unit) {
    val note = remember { noteId?.let { viewModel.getNoteById(it) } }
    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (noteId == null) "Nouvelle note" else "Modifier") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (title.isNotEmpty() || content.isNotEmpty()) {
                            if (noteId == null) viewModel.addNote(title, content)
                            else viewModel.updateNote(note!!.copy(title = title, content = content))
                        }
                        onBack()
                    }) {
                        // Utilisation de l'icône standard sans AutoMirrored pour éviter les conflits
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                },
                actions = {
                    if (noteId != null) {
                        IconButton(onClick = {
                            viewModel.deleteNote(noteId)
                            onBack()
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Supprimer")
                        }
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
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Titre", style = MaterialTheme.typography.headlineSmall) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = content,
                onValueChange = { content = it },
                placeholder = { Text("Contenu") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}