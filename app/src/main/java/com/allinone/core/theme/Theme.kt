package com.allinone.core.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun AllInOneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkColorScheme(
            primary = Purple80,
            secondary = PurpleGrey80,
            tertiary = Pink80,
            primaryContainer = PrimaryContainerDark,
            onPrimaryContainer = OnPrimaryContainerDark,
            secondaryContainer = SecondaryContainerDark,
            onSecondaryContainer = OnSecondaryContainerDark,
            tertiaryContainer = TertiaryContainerDark,
            onTertiaryContainer = OnTertiaryContainerDark,
            errorContainer = ErrorContainerDark,
            onErrorContainer = OnErrorContainerDark,
            surfaceContainerLowest = SurfaceContainerLowestDark,
            surfaceContainerLow = SurfaceContainerLowDark,
            surfaceContainer = SurfaceContainerDark,
            surfaceContainerHigh = SurfaceContainerHighDark,
            surfaceContainerHighest = SurfaceContainerHighestDark,
            outline = OutlineDark,
            outlineVariant = OutlineVariantDark,
            onSurfaceVariant = OnSurfaceVariantDark,
            inversePrimary = InversePrimaryDark,
            inverseSurface = InverseSurfaceDark,
            inverseOnSurface = InverseOnSurfaceDark
        )
        else -> lightColorScheme(
            primary = Purple40,
            secondary = PurpleGrey40,
            tertiary = Pink40,
            primaryContainer = PrimaryContainerLight,
            onPrimaryContainer = OnPrimaryContainerLight,
            secondaryContainer = SecondaryContainerLight,
            onSecondaryContainer = OnSecondaryContainerLight,
            tertiaryContainer = TertiaryContainerLight,
            onTertiaryContainer = OnTertiaryContainerLight,
            errorContainer = ErrorContainerLight,
            onErrorContainer = OnErrorContainerLight,
            surfaceContainerLowest = SurfaceContainerLowestLight,
            surfaceContainerLow = SurfaceContainerLowLight,
            surfaceContainer = SurfaceContainerLight,
            surfaceContainerHigh = SurfaceContainerHighLight,
            surfaceContainerHighest = SurfaceContainerHighestLight,
            outline = OutlineLight,
            outlineVariant = OutlineVariantLight,
            onSurfaceVariant = OnSurfaceVariantLight,
            inversePrimary = InversePrimaryLight,
            inverseSurface = InverseSurfaceLight,
            inverseOnSurface = InverseOnSurfaceLight
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = ExpressiveTypography,
        shapes = ExpressiveShapes,
        content = content
    )
}
