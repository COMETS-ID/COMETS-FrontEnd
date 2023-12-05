package com.mahardika.comets.ui.screen.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun StatisticItem(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
)
{
    Surface(
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            content()
        }
    }
}