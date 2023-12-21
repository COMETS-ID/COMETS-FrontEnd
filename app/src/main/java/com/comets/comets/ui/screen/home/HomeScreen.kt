package com.comets.comets.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.comets.comets.R
import com.comets.comets.data.response.AssessmentResponse
import com.comets.comets.ui.commons.ArticleItemContent
import com.comets.comets.ui.components.ProfileImageButton
import com.comets.comets.ui.components.SectionTitle
import com.comets.comets.ui.navigation.Screen
import com.comets.comets.ui.screen.home.components.ArticleItem
import com.comets.comets.ui.screen.home.components.MoodItem
import com.comets.comets.ui.screen.prediction_result.interpretPrediction
import com.comets.comets.utils.Mood

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(
        Unit
    ) {
        viewModel.getUserAssessments()
    }
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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        item {
            TopSection(navController = navController)
        }
        item {
            Box {
                BottomSection(
                    moodsImage = moodImageList.take(5),
                    moodsForm = moodFormList.take(5),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                24.dp,
                                24.dp,
                                0.dp,
                                0.dp
                            )
                        ),
                )
                MenuSection(modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 32.dp)
                    .align(Alignment.TopCenter)
                    .offset(y = (-32).dp)
                    .clickable {
                        navController.navigate("${Screen.ApplicationContent.Camera.route}/no-uuid") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    })
            }
        }
    }
//    Column {
//        BoxWithConstraints(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(MaterialTheme.colorScheme.primary)
//        ) {
//            var topHeight by remember { mutableStateOf(0.dp) }
//            val maxHeight = this.maxHeight
//            val density = LocalDensity.current
//            val remainingHeight = maxHeight - topHeight
//
//            TopSection(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .align(Alignment.TopCenter)
//                    .onGloballyPositioned {
//                        topHeight = with(density) {
//                            it.size.height.toDp()
//                        }
//                    },
//                navController = navController,
//            )
//            BottomSection(
//                moodsImage = moodImageList.take(5),
//                moodsForm = moodFormList.take(5),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(remainingHeight)
//                    .align(Alignment.BottomCenter)
//                    .clip(
//                        RoundedCornerShape(
//                            24.dp,
//                            24.dp,
//                            0.dp,
//                            0.dp
//                        )
//                    ),
//            )
//
//            val centerHeight = 56.dp
//            val centerBottomPadding = remainingHeight - centerHeight / 2
//
//            MenuSection(modifier = Modifier
//                .padding(
//                    bottom = centerBottomPadding,
//                    start = 32.dp,
//                    end = 32.dp
//                )
//                .fillMaxWidth()
//                .height(centerHeight)
//                .align(Alignment.BottomCenter)
//                .clickable {
//                    navController.navigate("${Screen.ApplicationContent.Camera.route}/no-uuid") {
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            saveState = true
//                        }
//                        restoreState = true
//                        launchSingleTop = true
//                    }
//                })
//        }
//    }
}

@Composable
fun TopSection(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Column {
            Text(
                text = stringResource(R.string.home_greeting),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Eren Yeager",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    ProfileImageButton(modifier = Modifier.clickable {
                        navController.navigate(Screen.ApplicationContent.Profile.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    })
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun BottomSection(
    modifier: Modifier = Modifier,
    moodsImage: List<AssessmentResponse>,
    moodsForm: List<AssessmentResponse>,
) {
    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        StatisticSection(
            mood = if (moodsImage.isNotEmpty()) interpretPrediction(
                moodsImage[0].value
            ) else Mood.NEUTRAL,
            score = if (moodsForm.isNotEmpty()) moodsForm[0].value
            else "?"
        )
        Spacer(modifier = Modifier.height(32.dp))
        MoodSection(
            moodsImage,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        ArticleSection()
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun MenuSection(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.secondary,
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.home_check_your_mood),
                style = MaterialTheme.typography.labelLarge
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null
            )
        }
    }
}

@Composable
fun StatisticSection(
    modifier: Modifier = Modifier,
    mood: Mood,
    score: String,
) {
    Column(modifier = modifier.padding(horizontal = 24.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(
                8.dp
            )
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.home_today_mood_score),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${score.split("-")[0]}/50",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.home_today_mood),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                id = mood.moodSelectedIcon
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            text = mood.moodName,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MoodSection(
    moods: List<AssessmentResponse>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SectionTitle(
            text = stringResource(R.string.home_recent_moods),
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            moods.forEach {
                MoodItem(item = it)
            }
            if (moods.size < 5) {
                repeat(5 - moods.size) {
                    Text(text = "N/A")
                }
            }
        }
    }
}

@Composable
fun ArticleSection() {
    val articleList = listOf(
        ArticleItemContent(
            title = "Lorem Ipsum Dolor Sit Amet",
            description = "Lorem ipsum dolor sit amet consectetur adipiscing elit",
            imageUrl = "https://source.unsplash.com/random"
        ),
        ArticleItemContent(
            title = "Lorem Ipsum Dolor Sit Amet",
            description = "Lorem ipsum dolor sit amet consectetur adipiscing elit",
            imageUrl = "https://source.unsplash.com/random"
        ),
        ArticleItemContent(
            title = "Lorem Ipsum Dolor Sit Amet",
            description = "Lorem ipsum dolor sit amet consectetur adipiscing elit",
            imageUrl = "https://source.unsplash.com/random"
        )
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SectionTitle(
            text = "Articles for you",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                items(
                    items = articleList
                ) { item ->
                    ArticleItem(
                        title = item.title,
                        description = item.description,
                        imageUrl = item.imageUrl,
                        modifier = Modifier.fillParentMaxWidth(0.75f)
                    )
                }
            }
        }
    }
}
