package com.comets.comets.ui.screen.journal

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comets.comets.R
import com.comets.comets.data.response.AssessmentResponse
import com.comets.comets.ui.components.SectionTitle
import com.comets.comets.ui.screen.home.MoodSection
import com.comets.comets.ui.screen.prediction_result.interpretPrediction
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun JournalScreen(
    viewModel: JournalViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(
        Unit
    ) {
        viewModel.getUserAssessments()
    }
    val scrollState = rememberScrollState()
    val moodImageList = mutableListOf<AssessmentResponse>()
    val moodFormList = mutableListOf<AssessmentResponse>()

    uiState.assessments.groupBy { it.createdAt }.values.forEach { item ->
        if (item.size > 1) {
            val moodImageAssessment = item.find { it.type == "Gambar" }
            val moodFormAssessment = item.find { it.type == "Form" }

            moodImageAssessment?.let { moodImageList.add(it) }
            moodFormAssessment?.let { moodFormList.add(it) }
        }
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        MoodSection(
            moods = moodImageList.take(5)
        )
        ChartSection(
            moods = moodFormList.take(5)
        )
        HistorySection(
            moodsImage = moodImageList,
            moodsForm = moodFormList
        )
    }
}

@Composable
fun ChartSection(
    moods: List<AssessmentResponse>,
) {
    val moodScoreList = moods
        .reversed()
        .mapIndexed { index, item ->

            entryOf(
                index.toFloat() + 1,
                item.value.split("-")[0].toFloat()
            )
        }
    val chartEntryModel = entryModelOf(moodScoreList)
    Column {
        SectionTitle(text = stringResource(R.string.journal_mood_charts))
        Chart(
            chart = lineChart(
                decorations = listOf()
            ),
            model = chartEntryModel,
            startAxis = rememberStartAxis(guideline = null,
                valueFormatter = AxisValueFormatter<AxisPosition.Vertical.Start> { value, _ ->
                    value
                        .toInt()
                        .toString()
                }),
            bottomAxis = rememberBottomAxis(
                guideline = null,
            ),
            isZoomEnabled = false
        )
    }
}

@Composable
fun HistorySection(
    moodsImage: List<AssessmentResponse>,
    moodsForm: List<AssessmentResponse>,
) {
    Log.d(
        "moodsImage",
        moodsImage.toString()
    )
    Log.d(
        "moodsForm",
        moodsForm.toString()
    )
    Column() {
        SectionTitle(text = "Mood History")
        Spacer(modifier = Modifier.height(8.dp))
        moodsImage.forEachIndexed { index, item ->
            val dateFormat = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.getDefault()
            )
            val date = dateFormat.parse(item.createdAt)

            val formatter = SimpleDateFormat(
                "EEEE, MMM d'th' yyyy",
                Locale.getDefault()
            )
            val formattedDate = date?.let { value -> formatter.format(value) }
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = formattedDate.toString(),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = interpretPrediction(item.value).moodSelectedIcon),
                            contentDescription = null,
                            tint = interpretPrediction(item.value).moodColor,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = interpretPrediction(item.value).moodName,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Text(
                        text = "Score: ${moodsForm[index].value.split("-")[0]}/50",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

