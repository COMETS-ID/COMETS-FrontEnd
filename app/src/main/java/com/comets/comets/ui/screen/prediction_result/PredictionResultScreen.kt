package com.comets.comets.ui.screen.prediction_result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.comets.comets.ui.navigation.Screen
import com.comets.comets.utils.Mood

@Composable
fun PredictionResultScreen(
    prediction: String,
    uuid: String,
    navController: NavHostController,
) {
    val mood = interpretPrediction(prediction)

    Column(modifier = Modifier.fillMaxSize()) {
        Surface(
            color = mood.moodColor,
            shape = RoundedCornerShape(
                bottomEnd = 32.dp,
                bottomStart = 32.dp
            ),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = mood.moodSelectedIcon),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = mood.moodName,
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = mood.moodDescription,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
        Box {
            Button(
                onClick = {
                    navController.navigate(
                        "${
                            Screen.ApplicationContent.Questionnaire.route
                        }/$prediction/$uuid"
                    ) {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Take Questionnaire",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

fun interpretPrediction(mood: String): Mood {
    return when (mood) {
        "happy" -> Mood.HAPPY
        "neutral" -> Mood.NEUTRAL
        "sad" -> Mood.SAD
        "disgust" -> Mood.DISGUST
        "angry" -> Mood.ANGRY
        "fear" -> Mood.FEARFUL
        "surprise" -> Mood.SURPRISED
        else -> Mood.NEUTRAL
    }
}