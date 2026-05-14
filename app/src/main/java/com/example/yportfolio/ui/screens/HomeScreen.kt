package com.example.yportfolio.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.yportfolio.R
import com.example.yportfolio.ui.components.ModernSearchBar
import com.example.yportfolio.ui.components.NoteCard
import com.example.yportfolio.viewmodel.NoteViewModel

import androidx.compose.material.icons.filled.Settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: NoteViewModel,
    onNoteClick: (Int) -> Unit,
    onAddNoteClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val notes by viewModel.notes.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery
    val filteredNotes = notes.filter {
        it.title.contains(searchQuery, true) || it.content.contains(searchQuery, true)
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Mes Notes",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-1).sp
                        )
                    )
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(R.string.settings),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                ModernSearchBar(searchQuery, viewModel::onSearchQueryChange)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNoteClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(18.dp),
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.ajouter_une_note), modifier = Modifier.size(28.dp))
            }
        }
    ) { padding ->
        Box(modifier = Modifier
            .padding(padding)
            .fillMaxSize()) {
            if (filteredNotes.isEmpty()) {
                // État vide élégant si aucune note n'est trouvée
                EmptyState(isSearching = searchQuery.isNotEmpty())
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalItemSpacing = 16.dp,
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Animation apparition et suppression des notes
                    items(filteredNotes, key = { it.id }) { note ->
                        Box(modifier = Modifier.animateItem()) { // Animation automatique de mouvement/suppression
                            NoteCard(
                                note = note,
                                onClick = { onNoteClick(note.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyState(isSearching: Boolean) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.Description,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (isSearching) "Aucun résultat trouvé" else "Commencez à noter vos idées",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}