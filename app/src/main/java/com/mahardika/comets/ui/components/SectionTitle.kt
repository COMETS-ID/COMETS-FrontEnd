package com.mahardika.comets.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun SectionTitle(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        text = text,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        modifier = modifier
    )
}