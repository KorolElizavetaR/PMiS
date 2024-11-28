package com.catalog.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFBD4646),
    onPrimary = Color(0xFF541F1F),
    secondary = Color(0xFF70061D),
    onSecondary = Color.Black,
    background = Color(0xFFFFFFFF),
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    tertiary = Color(0x36000000)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF5D2323),
    onPrimary = Color(0xFFAD6262),
    secondary = Color(0xFFB05F70),
    onSecondary = Color.Black,
    background = Color(0xFF332323),
    onBackground = Color.White,
    surface = Color(0xFF725050),
    onSurface = Color.White,
    tertiary = Color(0x36FFFFFF)
)

@Composable
fun Catalog56Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}