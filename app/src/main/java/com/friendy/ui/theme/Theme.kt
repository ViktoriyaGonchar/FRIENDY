package com.friendy.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

enum class AppTheme {
    PRIMARY,    // Основная темно-синяя тема
    DARK,       // Темная тема (черный фон)
    LIGHT       // Светлая тема (белый фон)
}

// Основная тема (темно-синяя)
private val PrimaryColorScheme = darkColorScheme(
    primary = DarkBluePrimary,
    secondary = DarkBlueSecondary,
    background = DarkBlueBackground,
    surface = DarkBlueSurface,
    onPrimary = DarkBlueOnPrimary,
    onSecondary = DarkBlueOnSecondary,
    onBackground = DarkBlueOnBackground,
    onSurface = DarkBlueOnSurface
)

// Темная тема (черный фон, белые буквы)
private val DarkColorScheme = darkColorScheme(
    primary = DarkBlackPrimary,
    background = DarkBlackBackground,
    surface = DarkBlackSurface,
    onPrimary = DarkBlackOnPrimary,
    onBackground = DarkBlackOnBackground,
    onSurface = DarkBlackOnSurface
)

// Светлая тема (белый фон, черные буквы)
private val LightColorScheme = lightColorScheme(
    primary = LightWhitePrimary,
    background = LightWhiteBackground,
    surface = LightWhiteSurface,
    onPrimary = LightWhiteOnPrimary,
    onBackground = LightWhiteOnBackground,
    onSurface = LightWhiteOnSurface
)

@Composable
fun FriendyTheme(
    theme: AppTheme = AppTheme.PRIMARY,
    content: @Composable () -> Unit
) {
    val colorScheme = when (theme) {
        AppTheme.PRIMARY -> PrimaryColorScheme
        AppTheme.DARK -> DarkColorScheme
        AppTheme.LIGHT -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}