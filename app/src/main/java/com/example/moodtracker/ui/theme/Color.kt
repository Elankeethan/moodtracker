package com.example.moodtracker.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Light Color Scheme
val LightPrimary = Color(0xFF667EEA)
val LightOnPrimary = Color(0xFFFFFFFF)
val LightSurface = Color(0xFFF5F7FA)
val LightOnSurface = Color(0xFF2D3748)
val LightSurfaceVariant = Color(0xFFFFFFFF)
val LightOnSurfaceVariant = Color(0xFF4A5568)

// Dark Color Scheme (optional - for future dark mode support)
val DarkPrimary = Color(0xFFA5B4FC)
val DarkOnPrimary = Color(0xFF1E1B2E)
val DarkSurface = Color(0xFF1E1B2E)
val DarkOnSurface = Color(0xFFE2E8F0)
val DarkSurfaceVariant = Color(0xFF2D2A3E)
val DarkOnSurfaceVariant = Color(0xFFCBD5E0)

// Mood-specific colors
val HappyColor = Color(0xFFFFD54F) // Amber
val SadColor = Color(0xFF4FC3F7)   // Light Blue
val AngryColor = Color(0xFFE57373) // Red
val AnxiousColor = Color(0xFFBA68C8) // Purple
val TiredColor = Color(0xFF4DB6AC)  // Teal
val ExcitedColor = Color(0xFF81C784) // Green
val CalmColor = Color(0xFF64B5F6)   // Blue
val NeutralColor = Color(0xFF90A4AE) // Blue Grey

// Semantic colors
val SuccessColor = Color(0xFF48BB78) // Green
val WarningColor = Color(0xFFED8936) // Orange
val ErrorColor = Color(0xFFF56565)   // Red
val InfoColor = Color(0xFF4299E1)    // Blue

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

// Mood gradients for more visual appeal
val HappyGradient = Brush.verticalGradient(
    colors = listOf(
        HappyColor,
        Color(0xFFFFB74D)
    )
)

val SadGradient = Brush.verticalGradient(
    colors = listOf(
        SadColor,
        Color(0xFF29B6F6)
    )
)

val AngryGradient = Brush.verticalGradient(
    colors = listOf(
        AngryColor,
        Color(0xFFEF5350)
    )
)

val CalmGradient = Brush.verticalGradient(
    colors = listOf(
        CalmColor,
        Color(0xFF42A5F5)
    )
)

// Text colors
val TextPrimary = Color(0xFF2D3748)
val TextSecondary = Color(0xFF4A5568)
val TextTertiary = Color(0xFF718096)
val TextDisabled = Color(0xFFA0AEC0)

// Border colors
val BorderLight = Color(0xFFE2E8F0)
val BorderMedium = Color(0xFFCBD5E0)
val BorderDark = Color(0xFFA0AEC0)

// Background colors
val BackgroundPrimary = Color(0xFFF7FAFC)
val BackgroundSecondary = Color(0xFFEDF2F7)
val BackgroundTertiary = Color(0xFFE2E8F0)

// Shadow colors
val ShadowLight = Color(0x1A000000)
val ShadowMedium = Color(0x33000000)
val ShadowDark = Color(0x4D000000)