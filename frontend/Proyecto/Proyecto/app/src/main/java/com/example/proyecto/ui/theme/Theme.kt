package com.example.proyecto.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext

val DarkColorScheme = darkColorScheme(
    primary = Darkblue,
    secondary = DarkLightBlue,
    tertiary = White
)

val LightColorScheme = lightColorScheme(
    primary = White,
    secondary = LightBlue,
    tertiary = Black
)

val DarkColorPalette = CustomColors(
    background = Color(0xFF0A0E23), // Muy oscuro, casi negro
    cardPrimary = Color(0xFF1A2332), // Azul grisáceo suave para tarjetas
    cardSecondary = Color(0xFF263238), // Azul muy oscuro para card secundario
    cardBorder = Color(0xFF3C4A5D),
    normalButton = Color(0xFF4F83CC), // Azul claro, buen contraste sobre fondo oscuro
    outLinedButton = Color(0xFF4F83CC), // Igual que el botón normal
    modalBackground = Color(0xFF1A2332), // Igual que cardPrimary para coherencia
    modalButton = Color(0xFF4F83CC), // Azul cielo claro
    deleteText = Color(0xFFE57373), // Rojo tenue
    toggleOn = Color(0xFF81C784),
    textOnPrimary = Color(0xFFE0E0E0),
    textOnSecondary = Color(0xFFB0BEC5),
    gradient = DarkGradient,
    error = Color(0xFFE57373),
    iconSelected = Color(0xFF90CAF9), // Azul claro (coherente con modalButton)
    iconUnselected = Color(0xFF607D8B),
    textPrimary = Color(0xFFE0E0E0),
    textSecondary = Color(0xFFB0BEC5),
    cardBorderSecondary = Color(0xFFFFFFFF)

)

val LightColorPalette = CustomColors(
    background = Color(0xFFBBDEFB), // Azul claro más definido y aún suave
    cardPrimary = Color(0xFFF8F4F8), // Azul grisáceo muy claro
    cardSecondary = Color(0xFFF5F5F5), // Gris muy claro para el card secundario
    cardBorder = Color(0xFF1976D2), // Azul medio
    normalButton = Color(0xFF1976D2), // Azul medio
    outLinedButton = Color(0xFF1976D2),
    modalBackground = Color(0xFFFFFFFF), // Blanco puro
    modalButton = Color(0xFF1565C0), // Azul más oscuro
    deleteText = Color(0xFFD32F2F), // Rojo fuerte
    toggleOn = Color(0xFF388E3C), // Verde fuerte
    textOnPrimary = Color(0xFF212121), // Casi negro
    textOnSecondary = Color(0xFF424242), // Gris oscuro
    gradient = LightGradient,
    error = Color(0xFFD32F2F),
    iconSelected = Color(0xFF1565C0), // Azul fuerte (coherente con modalButton)
    iconUnselected = Color(0xFF9E9E9E),
    textPrimary = Color(0xFFFFFFFF), //Para botones azules
    textSecondary = Color(0xFF212121),
    cardBorderSecondary = Color(0xFF212121)
)



val LocalCustomColors = staticCompositionLocalOf { DarkColorPalette }

@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val customColors = if (darkTheme) DarkColorPalette else LightColorPalette

    CompositionLocalProvider(LocalCustomColors provides customColors) {
        MaterialTheme(
            colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme(),
            typography = Typography,
            content = content
        )
    }
}

val CustomTheme: CustomColors
    @Composable
    get() = LocalCustomColors.current


data class CustomColors(
    val background: Color,
    val cardPrimary: Color,
    val cardSecondary: Color,
    val cardBorder: Color,
    val normalButton: Color,
    val outLinedButton: Color,
    val modalBackground: Color,
    val modalButton: Color,
    val deleteText: Color,
    val toggleOn: Color,
    val textOnPrimary: Color,
    val textOnSecondary: Color,
    val gradient: Brush,
    val error: Color,
    val iconSelected: Color,
    val iconUnselected: Color,
    val textPrimary:Color,
    val textSecondary:Color,
    val cardBorderSecondary:Color
)

