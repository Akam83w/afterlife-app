package com.afterlife.app.core.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AfterlifeNavy = Color(0xFF142A3A)
private val AfterlifeTeal = Color(0xFF167C80)
private val AfterlifeMist = Color(0xFFF3F7F8)
private val AfterlifeInk = Color(0xFF182B36)
private val AfterlifeDark = Color(0xFF101D26)

private val AfterlifeLightColors = lightColorScheme(
    primary = AfterlifeTeal,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD9EEEE),
    onPrimaryContainer = AfterlifeNavy,
    secondary = Color(0xFF54717A),
    secondaryContainer = Color(0xFFE2ECEE),
    background = AfterlifeMist,
    surface = Color.White,
    onSurface = AfterlifeInk,
    onSurfaceVariant = Color(0xFF60747B),
    tertiary = Color(0xFF27866C)
)

private val AfterlifeDarkColors = darkColorScheme(
    primary = Color(0xFF78C9C6),
    onPrimary = AfterlifeDark,
    primaryContainer = Color(0xFF20464B),
    onPrimaryContainer = Color(0xFFD9EEEE),
    secondary = Color(0xFFA9C5C9),
    secondaryContainer = Color(0xFF30464D),
    background = AfterlifeDark,
    surface = Color(0xFF192B35),
    onSurface = Color(0xFFE6F0F2),
    onSurfaceVariant = Color(0xFFB5C7CC),
    tertiary = Color(0xFF8DD7B7)
)

@Composable
fun AfterlifeTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) AfterlifeDarkColors else AfterlifeLightColors,
        content = content
    )
}
