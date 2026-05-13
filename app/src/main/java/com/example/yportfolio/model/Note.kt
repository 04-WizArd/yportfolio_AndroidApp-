package com.example.yportfolio.model

import java.util.UUID

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val color: Long = 0xFFFFFFFF // Blanc par défaut
)