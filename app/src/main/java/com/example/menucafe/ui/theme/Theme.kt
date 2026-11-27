package com.example.menucafe.ui.theme

import android.app.Activity
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

    primary = MutedPlumPrimary,              // Color del botón "Agregar"
    onPrimary = Color.White,                 // Texto blanco dentro del botón "Agregar"
    background = CreamBackground,            // Fondo general de la pantalla
    onBackground = EspressoText,             // Títulos ("El lugar donde el café...", "Categorías")

    surface = CreamBackground,               // Superficie base (buscador y barra superior)
    onSurface = EspressoText,                // Letras de tarjetas, buscador y tab activo
    onSurfaceVariant = MutedGrayText,        // Descripciones e íconos inactivos de la barra inferior

    surfaceContainerHighest = SoftLilacCard, // Fondo de las tarjetas (Card)
    surfaceContainer = SoftLilacCard,        // Fondo de la barra inferior (NavigationBar)

    primaryContainer = RoseBadge,            // Fondo de etiquetas/contenedores suaves
    onPrimaryContainer = EspressoText,        // Texto sobre primaryContainer

    secondary = EspressoText,             // Color del texto activo ("Inicio")
    secondaryContainer = RoseBadge,       // Color del óvalo/indicador seleccionado
    onSecondaryContainer = EspressoText   // Color del ícono activo (si usas Icon vectorial)

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun MenuCafeTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}