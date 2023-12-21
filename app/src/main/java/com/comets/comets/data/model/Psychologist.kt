package com.comets.comets.data.model

import androidx.compose.ui.graphics.vector.ImageVector

data class Psychologist(
    val id: Int,
    val name: String,
    val specialization: String,
    val tariff: String,
    val description: String? = null,
    val image: ImageVector?,
)