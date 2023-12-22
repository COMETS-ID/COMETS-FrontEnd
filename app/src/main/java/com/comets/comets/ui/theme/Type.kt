package com.comets.comets.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.comets.comets.R

object AppFont {
    val Poppins = FontFamily(
        Font(
            R.font.poppins_light,
            FontWeight.Light
        ),
        Font(R.font.poppins_regular),
        Font(
            R.font.poppins_medium,
            FontWeight.Medium
        ),
        Font(
            R.font.poppins_semibold,
            FontWeight.SemiBold
        ),
        Font(
            R.font.poppins_bold,
            FontWeight.Bold
        ),
    )
}

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    displayMedium = TextStyle(
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp
    ),
    displaySmall = TextStyle(
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Light,
        fontSize = 18.sp
    ),

    headlineLarge = TextStyle(
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    ),

    titleLarge = TextStyle(
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    titleSmall = TextStyle(
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle(
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),

    labelLarge = TextStyle(
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    labelMedium = TextStyle(
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = AppFont.Poppins,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    )
)

