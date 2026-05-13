package com.example.yportfolio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // 0 pour que room genere l'id
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val color: Long = 0xFFFFFFFF
)