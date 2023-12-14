package com.mahardika.comets.ui.screen.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahardika.comets.ui.screen.home.MoodSection

@Composable
fun JournalScreen() {
    val scrollState = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        MoodSection()
    }
}

@Composable
fun MoodItem(
    modifier: Modifier = Modifier,
    mood: String,
    day: String,
    icon: ImageVector,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = mood,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = day,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light
        )
    }
}