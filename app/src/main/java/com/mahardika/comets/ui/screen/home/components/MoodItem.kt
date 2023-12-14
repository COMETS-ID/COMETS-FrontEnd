package com.mahardika.comets.ui.screen.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahardika.comets.ui.commons.MoodItemContent

@Composable
fun MoodItem(
    modifier: Modifier = Modifier,
    item: MoodItemContent,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = item.mood.moodSelectedIcon),
            contentDescription = null,
            tint = item.mood.moodColor,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = item.mood.moodName,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = item.day.slice(
                IntRange(
                    0,
                    2
                )
            ),
            fontSize = 12.sp,
            fontWeight = FontWeight.Light
        )
    }
}