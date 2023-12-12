package com.mahardika.comets.ui.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mahardika.comets.utils.customWormTransition

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    containerShape: Shape = RectangleShape,
    selectedItem: Int? = null,
    itemSize: Int? = null,
    content: @Composable RowScope.() -> Unit,
) {
    Surface(
        modifier = modifier,
        color = containerColor,
        contentColor = contentColor,
        shape = containerShape
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            selectedItem?.let {
                itemSize?.let {
                    val maxWidth = this.maxWidth
                    val animationSpec: AnimationSpec<Dp> = spring(
                        dampingRatio = 1f,
                        stiffness = Spring.StiffnessLow,
                    )
                    val indicatorPosition: Dp by animateDpAsState(targetValue = (maxWidth / (itemSize.takeIf { it != 0 }
                        ?: 1)) * selectedItem,
                        animationSpec = animationSpec,
                        label = "indicator")

                    Box(modifier = Modifier.width(maxWidth / (itemSize.takeIf { it != 0 } ?: 1))) {
                        Box(modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 4.dp)
                            .customWormTransition(indicatorPosition,
                                MaterialTheme.colorScheme.primary,
                                maxWidth / (itemSize.takeIf { it != 0 } ?: 1))
                            .size(height = 4.dp,
                                width = maxWidth / (itemSize.takeIf { it != 0 } ?: 1)))
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp)
                            .selectableGroup(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        content = content
                    )
                }
            }
        }
    }
}