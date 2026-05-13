package com.example.yportfolio.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yportfolio.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteCard(note: Note, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        shape = RoundedCornerShape(20.dp), // Coins plus ronds
        colors = CardDefaults.cardColors(
            containerColor = if (note.color == 0xFFFFFFFF) MaterialTheme.colorScheme.surface else Color(note.color)
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)) // Bordure fine
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            if (note.title.isNotEmpty()) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(8.dp))
            }
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 8,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 20.sp // Meilleure lisibilité
            )
        }
    }
}