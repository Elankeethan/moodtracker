package com.example.moodtracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarStyle
import com.example.moodtracker.ui.viewmodel.MoodViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeeklyReportScreen(
    viewModel: MoodViewModel,
    navController: NavController,
    weeklyData: Map<String, Int>
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "📊 Weekly Mood Report",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF009688), // Changed to teal to match MainScreen
                    titleContentColor = Color.White
                ),
                modifier = Modifier.shadow(
                    elevation = 16.dp, // Increased to match MainScreen
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
            )
        },
        containerColor = Color.Transparent // Added to match MainScreen
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
                    .padding(16.dp)
            ) {
                if (weeklyData.isNotEmpty()) {
                    // Chart Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(380.dp)
                            .shadow(4.dp, RoundedCornerShape(20.dp)),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFC8E6C9)), // Changed to light mint green
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "📈 Mood Frequency This Week",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2D3748)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            MoodBarChart(data = weeklyData, viewModel = viewModel)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Summary Section
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
                                .padding(20.dp)
                        ) {
                            Text(
                                text = "📋 Mood Summary",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2D3748),
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(weeklyData.entries.toList().sortedByDescending { it.value }) { (mood, count) ->
                                    MoodStatItem(
                                        mood = mood,
                                        count = count,
                                        color = getMoodColor(mood)
                                    )
                                }
                            }
                        }
                    }

                } else {
                    // Empty State
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(4.dp, RoundedCornerShape(20.dp)),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFC8E6C9)), // Changed to light mint green
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "📊",
                                    fontSize = 48.sp,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )
                                Text(
                                    text = "No Data Available",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2D3748),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Start tracking your moods to see insights!",
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center,
                                    color = Color(0xFF718096),
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MoodBarChart(data: Map<String, Int>, viewModel: MoodViewModel) {
    val maxCount = data.values.maxOrNull() ?: 1

    val barData = data.entries.mapIndexed { index, (mood, count) ->
        val color = getMoodColor(mood)
        BarData(
            point = Point(index.toFloat(), count.toFloat()),
            label = mood.split(" ").last(), // Emoji
            color = color
        )
    }

    val xAxisData = AxisData.Builder()
        .axisStepSize(50.dp)
        .steps(barData.size - 1)
        .startDrawPadding(30.dp)
        .labelData { index -> barData[index].label }
        .axisLineColor(Color(0xFF009688)) // Changed to teal
        .axisLabelColor(Color(0xFF4A5568))
        .build()

    val yAxisData = AxisData.Builder()
        .steps(maxCount)
        .labelAndAxisLinePadding(25.dp)
        .axisLineColor(Color(0xFF009688)) // Changed to teal
        .axisLabelColor(Color(0xFF4A5568))
        .labelData { index ->
            if (maxCount > 0) (index * (maxCount.coerceAtLeast(1) / maxCount.coerceAtLeast(1))).toString()
            else index.toString()
        }
        .build()

    val barChartData = BarChartData(
        chartData = barData,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        barStyle = BarStyle(
            barWidth = 30.dp,
            paddingBetweenBars = 25.dp,
            cornerRadius = 8.dp
        ),
        backgroundColor = Color.Transparent,
        showYAxis = true,
        showXAxis = true
    )

    BarChart(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        barChartData = barChartData
    )
}

@Composable
fun MoodStatItem(mood: String, count: Int, color: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(color.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = mood.split(" ").last(),
                        fontSize = 20.sp,
                        color = color
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Column {
                    Text(
                        text = mood.split(" ").first(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2D3748)
                    )
                    Text(
                        text = "${count} time${if (count != 1) "s" else ""} this week",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF718096)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF8BC34A).copy(alpha = 0.1f)), // Changed to green
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = count.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8BC34A) // Changed to green
                )
            }
        }
    }
}