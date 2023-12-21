package com.comets.comets.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.customWormTransition(
    offset: Dp,
    indicatorColor: Color,
    itemWidth: Dp,
) =
    composed {
        drawWithContent {
            val distance = itemWidth.roundToPx()
            val scrollPosition = (offset
                .toPx()
                .div(distance))
            val wormOffset = (scrollPosition % 1) * 2

            val xPos = scrollPosition.toInt() * distance

            val head = xPos + distance * 0f.coerceAtLeast(wormOffset - 1)
            val tail = xPos + size.width + distance * 1f.coerceAtMost(wormOffset)

            val adjustedHead = if (offset < 0.dp) head - distance else head
            val adjustedTail = if (offset < 0.dp) tail - distance else tail

            val worm = RoundRect(
                adjustedHead,
                0f,
                adjustedTail,
                size.height,
                CornerRadius(50f)
            )


            val path = Path().apply { addRoundRect(worm) }
            drawPath(
                path = path,
                color = indicatorColor
            )
        }
    }