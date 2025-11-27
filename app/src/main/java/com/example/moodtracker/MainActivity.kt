package com.example.moodtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moodtracker.data.MoodDatabase
import com.example.moodtracker.data.MoodRepository
import com.example.moodtracker.ui.MainScreen
import com.example.moodtracker.ui.MoodDetailScreen
import com.example.moodtracker.ui.WeeklyReportScreen
import com.example.moodtracker.ui.theme.MoodTrackerTheme
import com.example.moodtracker.ui.viewmodel.MoodViewModel

class MainActivity : ComponentActivity() {
    private val database by lazy { MoodDatabase.getDatabase(this) }
    private val repository by lazy { MoodRepository(database.moodDao()) }
    private val viewModel: MoodViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(MoodViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return MoodViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoodTrackerTheme {
                val navController = rememberNavController()
                val moodEntries = viewModel.moodEntries.collectAsState(initial = emptyList())
                val selectedMoodEntry = viewModel.selectedMoodEntry.collectAsState()
                val weeklyData = viewModel.getWeeklyEntries().collectAsState(initial = emptyMap())

                NavHost(
                    navController = navController,
                    startDestination = "main"
                ) {
                    composable("main") {
                        MainScreen(
                            viewModel = viewModel,
                            navController = navController,
                            moodEntries = moodEntries.value
                        )
                    }
                    composable("moodDetail") {
                        MoodDetailScreen(
                            viewModel = viewModel,
                            navController = navController,
                            selectedMoodEntry = selectedMoodEntry.value
                        )
                    }
                    composable("weeklyReport") {
                        WeeklyReportScreen(
                            viewModel = viewModel,
                            navController = navController,
                            weeklyData = weeklyData.value
                        )
                    }
                }
            }
        }
    }
}
