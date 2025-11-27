package com.example.moodtracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodtracker.data.MoodEntry
import com.example.moodtracker.data.MoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MoodViewModel(private val repository: MoodRepository) : ViewModel() {
    val moodEntries = repository.getAllEntries()

    private val _selectedMoodEntry = MutableStateFlow<MoodEntry?>(null)
    val selectedMoodEntry: StateFlow<MoodEntry?> = _selectedMoodEntry.asStateFlow()

    val availableMoods = listOf(
        "Happy 😊" to 0xFF4CAF50,
        "Calm 🙂" to 0xFF2196F3,
        "Neutral 😐" to 0xFF9E9E9E,
        "Sad 😟" to 0xFF607D8B,
        "Anxious 😬" to 0xFFF44336
    )

    fun addMoodEntry(mood: String) {
        viewModelScope.launch {
            repository.insertEntry(MoodEntry(mood = mood))
        }
    }

    fun deleteMoodEntry(entry: MoodEntry) {
        viewModelScope.launch {
            repository.deleteEntry(entry)
        }
    }

    fun updateMoodEntry(entry: MoodEntry) {
        viewModelScope.launch {
            repository.updateEntry(entry)
        }
    }

    fun selectMoodEntry(entry: MoodEntry) {
        _selectedMoodEntry.value = entry
    }

    fun clearSelectedMoodEntry() {
        _selectedMoodEntry.value = null
    }

    // For weekly report
    fun getWeeklyEntries() = repository.getEntriesBetweenDates(
        getStartOfWeek(),
        getEndOfWeek()
    ).map { entries ->
        entries.groupBy { it.mood }.mapValues { it.value.size }
    }

    private fun getStartOfWeek(): String {
        val calendar = java.util.Calendar.getInstance()
        calendar.set(java.util.Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        return java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a", java.util.Locale.getDefault()).format(calendar.time)
    }

    private fun getEndOfWeek(): String {
        val calendar = java.util.Calendar.getInstance()
        calendar.set(java.util.Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        calendar.add(java.util.Calendar.DAY_OF_WEEK, 6)
        return java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a", java.util.Locale.getDefault()).format(calendar.time)
    }
}