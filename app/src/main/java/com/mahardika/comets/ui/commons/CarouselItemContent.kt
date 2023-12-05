package com.mahardika.comets.ui.commons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

data class CarouselItemContent(
    val title: String,
    val description: String,
    val cta: @Composable () -> Unit = {},
    val imageBitmap: ImageBitmap? = null
)
