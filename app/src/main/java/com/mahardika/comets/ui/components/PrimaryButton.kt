package com.mahardika.comets.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: Int = 16,
    fontWeight: FontWeight = FontWeight.Medium,
    shouldFillMaxWidth: Boolean = true,
    padding: Dp = 16.dp,
    onClick: () -> Unit,
) {
    Surface(color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(32.dp),
        modifier = if (shouldFillMaxWidth) {
            modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                }
        } else {
            modifier.clickable {
                onClick()
            }
        }) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(
                padding
            )
        ) {
            Text(
                text = text,
                fontSize = fontSize.sp,
                fontWeight = fontWeight,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}