package com.mahardika.comets.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mahardika.comets.R

object AppFont {
    val Quicksand = FontFamily(
        Font(R.font.quicksand_light, FontWeight.Light),
        Font(R.font.quicksand_regular),
        Font(R.font.quicksand_medium, FontWeight.Medium),
        Font(R.font.quicksand_semibold, FontWeight.SemiBold),
        Font(R.font.quicksand_bold, FontWeight.Bold)
    )
}

private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = AppFont.Quicksand),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = AppFont.Quicksand),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = AppFont.Quicksand),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = AppFont.Quicksand),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = AppFont.Quicksand),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = AppFont.Quicksand),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = AppFont.Quicksand),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = AppFont.Quicksand),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = AppFont.Quicksand),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = AppFont.Quicksand),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = AppFont.Quicksand),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = AppFont.Quicksand),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = AppFont.Quicksand),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = AppFont.Quicksand),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = AppFont.Quicksand)
)