package com.comets.comets.ui.screen.home.components

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
import com.comets.comets.data.response.AssessmentResponse
import com.comets.comets.ui.screen.prediction_result.interpretPrediction
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MoodItem(
    modifier: Modifier = Modifier,
    item: AssessmentResponse,
) {
    val mood = interpretPrediction(item.value)
    val date = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        Locale.getDefault()
    ).parse(
        item.createdAt
    )
    val day = SimpleDateFormat(
        "EEEE",
        Locale.getDefault()
    ).format(date?.date)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = mood.moodSelectedIcon),
            contentDescription = null,
            tint = mood.moodColor,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = mood.moodName,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = day.slice(
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