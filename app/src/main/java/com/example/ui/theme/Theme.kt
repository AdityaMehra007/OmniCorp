package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val OmniColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = ObsidianBg,
    primaryContainer = DarkCyanPill,
    onPrimaryContainer = NeonCyan,
    secondary = NeonEmerald,
    onSecondary = ObsidianBg,
    secondaryContainer = DarkGreenPill,
    onSecondaryContainer = NeonEmerald,
    tertiary = EnergyAmber,
    onTertiary = ObsidianBg,
    tertiaryContainer = DarkAmberPill,
    onTertiaryContainer = EnergyAmber,
    background = ObsidianBg,
    onBackground = TextPrimary,
    surface = CardSurface,
    onSurface = TextPrimary,
    surfaceVariant = CardSurfaceElevated,
    onSurfaceVariant = TextSecondary,
    outline = BorderSubtle,
    error = LaserRose,
    onError = ObsidianBg
)

@Composable
fun OmniCorpTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = OmniColorScheme,
        typography = Typography,
        content = content
    )
}
