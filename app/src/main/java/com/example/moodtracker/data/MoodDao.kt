package com.example.moodtracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDao {
    @Query("SELECT * FROM mood_entries ORDER BY id DESC")
    fun getAllEntries(): Flow<List<MoodEntry>>

    @Query("SELECT * FROM mood_entries WHERE id = :id")
    suspend fun getEntryById(id: Long): MoodEntry?

    @Insert
    suspend fun insertEntry(entry: MoodEntry)

    @Update
    suspend fun updateEntry(entry: MoodEntry)

    @Delete
    suspend fun deleteEntry(entry: MoodEntry)

    @Query("SELECT * FROM mood_entries WHERE timestamp >= :startDate AND timestamp <= :endDate ORDER BY timestamp ASC")
    fun getEntriesBetweenDates(startDate: String, endDate: String): Flow<List<MoodEntry>>
}