package com.example.moodtracker.data

import kotlinx.coroutines.flow.Flow

class MoodRepository(private val moodDao: MoodDao) {
    fun getAllEntries(): Flow<List<MoodEntry>> = moodDao.getAllEntries()

    suspend fun getEntryById(id: Long): MoodEntry? = moodDao.getEntryById(id)

    suspend fun insertEntry(entry: MoodEntry) = moodDao.insertEntry(entry)

    suspend fun updateEntry(entry: MoodEntry) = moodDao.updateEntry(entry)

    suspend fun deleteEntry(entry: MoodEntry) = moodDao.deleteEntry(entry)

    fun getEntriesBetweenDates(startDate: String, endDate: String): Flow<List<MoodEntry>> =
        moodDao.getEntriesBetweenDates(startDate, endDate)
}