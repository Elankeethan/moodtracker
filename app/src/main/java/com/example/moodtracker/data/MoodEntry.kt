package com.example.moodtracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mood_entries")
data class MoodEntry(
    @PrimaryKey
    val id: Long = System.currentTimeMillis(),
    val mood: String,
    val note: String = "",
    val timestamp: String = java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a", java.util.Locale.getDefault()).format(java.util.Date())
)