package com.comets.comets.ui.screen.questionnaire_result

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.comets.comets.R
import com.comets.comets.ui.components.SectionTitle
import com.comets.comets.ui.navigation.Screen
import com.comets.comets.ui.viewmodels.assessment.AssessmentViewModel
import com.comets.comets.utils.showToast

@Composable
fun QuestionnaireResultScreen(
    prediction: String,
    score: Int,
    uuid: String,
    navController: NavHostController,
    viewModel: AssessmentViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(
        Unit
    ) {
        viewModel.updatePrediction(prediction)
        viewModel.updateScore(score.toString() + "-" + interpretMood(score))
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                    Column {
                        Text(
                            text = stringResource(R.string.questionnaire_disclaimer),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = stringResource(R.string.questionnaire_disclaimer_text),
                            style = MaterialTheme.typography.bodyMedium
                        )

                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SectionTitle(
                        text = "Your Score",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "$score/50",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Text(
                        text = "Mood Status: ",
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                    Text(
                        text = interpretMood(score),
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            AccordionContent()
            Spacer(modifier = Modifier.height(32.dp))
            val context = LocalContext.current
            Button(
                onClick = {
                    if (uuid.isBlank() || uuid == "no-uuid") {
                        viewModel.postAssessment(onSuccess = {
                            navController.navigate(Screen.ApplicationContent.Journal.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        },
                            onError = {
                                showToast(
                                    context,
                                    it
                                )
                            })
                    } else {
                        viewModel.postAssessmentFromUserRoom(uuid = uuid,
                            onSuccess = {
                                navController.navigate(Screen.ApplicationContent.Connect.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            },
                            onError = {
                                showToast(
                                    context,
                                    it
                                )
                            })
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Save result",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun AccordionContent() {
    var expandedState by remember {
        mutableStateOf(moodItems
            .map { it.title to false }
            .toMap())
    }

    moodItems.forEach { moodItem ->
        AccordionItem(title = moodItem.title,
            description = moodItem.description,
            expanded = expandedState[moodItem.title] ?: false,
            onToggle = {
                expandedState = expandedState
                    .toMutableMap()
                    .apply {
                        for (key in keys) {
                            this[key] = false
                        }
                        this[moodItem.title] = !expandedState[moodItem.title]!!
                    }
            })
    }
}

@Composable
fun AccordionItem(
    title: String,
    description: String,
    expanded: Boolean,
    onToggle: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onToggle()
            }) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }

        if (expanded) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

data class MoodItem(
    val title: String,
    val description: String,
)

val moodItems = listOf(
    MoodItem(
        "Excellent Mood (45-50)",
        "Congratulations! You are currently experiencing an excellent mood for the day, indicating a high level of positivity, satisfaction, and overall well-being. Keep up the positive outlook and consider engaging in activities that contribute to your happiness."
    ),
    MoodItem(
        "Good Mood (40-44)",
        "Great news! You are in a good mood for the day, reflecting a positive state, though not at the highest level. Keep doing what you're doing to maintain this positive vibe, and consider continuing activities that uplift your spirits."
    ),
    MoodItem(
        "Neutral Mood (30-39)",
        "You are currently in a neutral mood for the day, suggesting a balanced emotional state without significant highs or lows. Explore activities that might add a touch of positivity to your day and enhance your overall mood."
    ),
    MoodItem(
        "Below Average Mood (20-29)",
        "It seems you are experiencing a mood that is below average for the day. Consider seeking support or trying activities that could help improve your mood, such as engaging in a favorite hobby, taking a short walk, or connecting with friends."
    ),
    MoodItem(
        "Poor Mood (10-19)",
        "Unfortunately, you are in a poor mood for the day, indicating a significant level of negativity or distress. It might be beneficial to reach out for support, talk to someone you trust, or try activities that could help lift your spirits, such as practicing mindfulness."
    ),
    MoodItem(
        "Invalid Mood Score (Below 10 or Above 50)",
        "Your provided score is outside the valid range for mood interpretation. Double-check your input for accuracy. If the issue persists, consider seeking guidance on how to calculate the mood score correctly or addressing any potential errors in the scoring system."
    )
)

fun interpretMood(totalScore: Int): String {
    return when (totalScore) {
        in 45..50 -> "Excellent mood for the day"
        in 40..44 -> "Good mood for the day"
        in 30..39 -> "Neutral mood for the day"
        in 20..29 -> "Below average mood for the day"
        in 10..19 -> "Poor mood for the day"
        else -> "Invalid mood score"
    }
}