package com.example.moodtracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtracker.data.MoodEntry
import com.example.moodtracker.ui.viewmodel.MoodViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MoodViewModel,
    navController: NavController,
    moodEntries: List<MoodEntry>
) {
    // Custom color scheme
    val colorScheme = lightColorScheme(
        primary = Color(0xFF009688),
        onPrimary = Color.White,
        surface = Color(0xFFF5F7FA),
        onSurface = Color(0xFF8BC34A),
        surfaceVariant = Color(0xFFC8E6C9), // Changed to light mint green
        onSurfaceVariant = Color(0xFF4A5568)
    )

    MaterialTheme(colorScheme = colorScheme) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Mood Tracker",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF009688),
                        titleContentColor = Color.White
                    ),
                    modifier = Modifier.shadow(
                        elevation = 16.dp,
                        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate("weeklyReport") },
                    containerColor = Color(0xFF8BC34A),
                    modifier = Modifier
                        .shadow(8.dp, CircleShape)
                        .size(60.dp)
                ) {
                    Icon(
                        Icons.Filled.BarChart,
                        contentDescription = "Weekly Report",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SurfaceGradient)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Welcome Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .shadow(4.dp, RoundedCornerShape(16.dp)),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFC8E6C9)), // Changed to light mint green
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Welcome Daily Mood Tracker! 👋",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2D3748)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "How are you feeling today?",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF4A5568)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    MoodSelector(viewModel = viewModel)

                    Spacer(modifier = Modifier.height(32.dp))

                    // Mood History Section
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .shadow(4.dp, RoundedCornerShape(24.dp)),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFC8E6C9)), // Changed to light mint green
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "📊 Mood History",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2D3748),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            MoodHistory(
                                entries = moodEntries,
                                onDeleteEntry = { viewModel.deleteMoodEntry(it) },
                                onEntryClick = {
                                    viewModel.selectMoodEntry(it)
                                    navController.navigate("moodDetail")
                                },
                                getMoodColor = { mood ->
                                    getMoodColor(mood)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MoodSelector(viewModel: MoodViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFC8E6C9)), // Changed to light mint green
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Select Your Mood",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF2D3748),
                modifier = Modifier.padding(bottom = 12.dp, start = 8.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(viewModel.availableMoods) { (mood, color) ->
                    MoodButton(
                        mood = mood,
                        color = getMoodColor(mood),
                        onClick = { viewModel.addMoodEntry(mood) }
                    )
                }
            }
        }
    }
}

@Composable
fun MoodButton(mood: String, color: Color, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.15f))
                .shadow(2.dp, CircleShape)
                .padding(12.dp)
        ) {
            Text(
                text = mood.split(" ").last(),
                fontSize = 28.sp,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = mood.split(" ").first(),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF4A5568),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MoodHistory(
    entries: List<MoodEntry>,
    onDeleteEntry: (MoodEntry) -> Unit,
    onEntryClick: (MoodEntry) -> Unit,
    getMoodColor: (String) -> Color
) {
    if (entries.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "🎭",
                    fontSize = 48.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "No moods logged yet",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2D3748),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Select a mood to begin your journey!",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF718096),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(entries, key = { it.id }) { entry ->
                MoodEntryItem(
                    entry = entry,
                    onDelete = { onDeleteEntry(entry) },
                    onClick = { onEntryClick(entry) },
                    moodColor = getMoodColor(entry.mood)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodEntryItem(entry: MoodEntry, onDelete: () -> Unit, onClick: () -> Unit, moodColor: Color) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFC8E6C9)) // Changed to light mint green
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Color indicator with icon
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(moodColor.copy(alpha = 0.15f))
            ) {
                Text(
                    text = entry.mood.split(" ").last(),
                    fontSize = 20.sp,
                    color = moodColor
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = entry.mood.split(" ").first(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2D3748)
                )
                Text(
                    text = entry.timestamp,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF718096)
                )
            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFED7D7).copy(alpha = 0.3f))
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete entry",
                    tint = Color(0xFFF30707),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

// Custom Color Palette
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Mood-specific colors
val HappyColor = Color(0xFFFFD54F) // Amber
val SadColor = Color(0xFF4FC3F7)   // Light Blue
val AngryColor = Color(0xFFE57373) // Red
val AnxiousColor = Color(0xFFBA68C8) // Purple
val TiredColor = Color(0xFF4DB6AC)  // Teal
val ExcitedColor = Color(0xFF81C784) // Green
val CalmColor = Color(0xFF64B5F6)   // Blue
val NeutralColor = Color(0xFF90A4AE) // Blue Grey

// Background gradients
val PrimaryGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF667EEA),
        Color(0xFF764BA2)
    )
)

val SurfaceGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFF5F7FA),
        Color(0xFFC3CFE2)
    )
)

val CardGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFFFFFFF),
        Color(0xFFF8F9FA)
    )
)

// Helper function to get mood colors
fun getMoodColor(mood: String): Color {
    return when (mood.split(" ").first().lowercase()) {
        "happy" -> HappyColor
        "sad" -> SadColor
        "angry" -> AngryColor
        "anxious" -> AnxiousColor
        "tired" -> TiredColor
        "excited" -> ExcitedColor
        "calm" -> CalmColor
        "neutral" -> NeutralColor
        else -> NeutralColor
    }
}