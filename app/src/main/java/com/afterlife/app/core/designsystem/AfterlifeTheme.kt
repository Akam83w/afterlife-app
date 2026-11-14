package com.afterlife.app.core.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AfterlifeLightColors = lightColorScheme()
private val AfterlifeDarkColors = darkColorScheme()

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
