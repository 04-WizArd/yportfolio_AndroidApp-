package com.example.yportfolio.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun NoteActionMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    isPinned: Boolean,
    onPinClick: () -> Unit,
    onShareClick: () -> Unit,
    onDeleteClick: () -> Unit,
    offset: DpOffset = DpOffset(0.dp, 0.dp)
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        offset = offset,
        shape = MaterialTheme.shapes.large,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        MenuOption(
            title = if (isPinned) "Désépingler" else "Épingler",
            icon = Icons.Outlined.PushPin,
            onClick = {
                onPinClick()
                onDismissRequest()
            }
        )
        MenuOption(
            title = "Partager",
            icon = Icons.Outlined.Share,
            onClick = {
                onShareClick()
                onDismissRequest()
            }
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), thickness = 0.5.dp)
        MenuOption(
            title = "Supprimer",
            icon = Icons.Outlined.Delete,
            onClick = {
                onDeleteClick()
                onDismissRequest()
            },
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun MenuOption(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    color: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface
) {
    DropdownMenuItem(
        text = { 
            Text(
                text = title, 
                style = MaterialTheme.typography.bodyMedium,
                color = color
            ) 
        },
        leadingIcon = { 
            Icon(
                imageVector = icon, 
                contentDescription = null,
                tint = color
            ) 
        },
        onClick = onClick
    )
}
