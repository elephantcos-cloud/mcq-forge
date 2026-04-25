package com.elephantcos.mcqforge.ui.theme
import android.app.Activity
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
private val Dark = darkColorScheme(primary = Color(0xFFA78BFA), onPrimary = Color(0xFF1E1B4B), primaryContainer = Color(0xFF4C1D95), secondary = Color(0xFF7DD3FC), surface = Color(0xFF0F0F23), onSurface = Color(0xFFE2E8F0), background = Color(0xFF0A0A1A), onBackground = Color(0xFFCBD5E1))
@Composable fun McqForgeTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    SideEffect { val w = (view.context as Activity).window; w.statusBarColor = Dark.background.toArgb(); WindowCompat.getInsetsController(w, view).isAppearanceLightStatusBars = false }
    MaterialTheme(colorScheme = Dark, content = content)
}
