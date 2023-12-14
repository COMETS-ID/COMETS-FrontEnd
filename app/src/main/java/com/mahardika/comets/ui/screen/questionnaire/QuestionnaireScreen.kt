package com.mahardika.comets.ui.screen.questionnaire

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.mahardika.comets.ui.navigation.Screen

@Composable
fun QuestionnaireScreen(
    navController: NavHostController,
) {
    var totalScore by remember { mutableIntStateOf(0) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
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
                            text = "Disclaimer",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(text = "This self-filled questionnaire is for general information only. Your responses may not accurately reflect your mental health status. It is not a substitute for professional advice.")

                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
        items(questionnaireList.size) { index ->
            QuestionnaireQuestionItem(question = questionnaireList[index],
                index = index + 1,
                onAnswerSelected = {
                    totalScore += it
                })
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    navController.navigate(
                        "${
                            Screen.QuestionnaireResult.route
                        }/$totalScore"
                    ) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Submit",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun QuestionnaireQuestionItem(
    question: QuestionnaireQuestion,
    index: Int,
    onAnswerSelected: (Int) -> Unit,
) {
    val selectedAnswer = remember { mutableStateOf<Int?>(null) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Question $index")
            Text(
                text = question.question,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                question.answers.forEach {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = selectedAnswer.value == it.score,
                            onClick = {
                                selectedAnswer.value = it.score
                                onAnswerSelected(it.score)
                            })
                        Text(text = it.text)
                    }
                }
            }
        }
    }
}

@Composable
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