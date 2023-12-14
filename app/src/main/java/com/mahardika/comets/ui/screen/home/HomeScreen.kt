package com.mahardika.comets.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.mahardika.comets.ui.commons.ArticleItemContent
import com.mahardika.comets.ui.commons.MoodItemContent
import com.mahardika.comets.ui.components.ProfileImageButton
import com.mahardika.comets.ui.navigation.Screen
import com.mahardika.comets.ui.screen.home.components.ArticleItem
import com.mahardika.comets.ui.screen.home.components.MoodItem
import com.mahardika.comets.utils.Mood

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        var topHeight by remember { mutableStateOf(0.dp) }
        val maxHeight = this.maxHeight
        val density = LocalDensity.current
        val remainingHeight = maxHeight - topHeight

        TopSection(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopCenter)
            .onGloballyPositioned {
                topHeight = with(density) {
                    it.size.height.toDp()
                }
            },
            navController = navController,
        )
        BottomSection(
            modifier = Modifier
                .fillMaxWidth()
                .height(remainingHeight)
                .align(Alignment.BottomCenter)
                .clip(
                    RoundedCornerShape(
                        24.dp,
                        24.dp,
                        0.dp,
                        0.dp
                    )
                )
        )

        val centerHeight = 56.dp
        val centerBottomPadding = remainingHeight - centerHeight / 2

        MenuSection(modifier = Modifier
            .padding(
                bottom = centerBottomPadding,
                start = 32.dp,
                end = 32.dp
            )
            .fillMaxWidth()
            .height(centerHeight)
            .align(Alignment.BottomCenter)
            .clickable {
                navController.navigate(Screen.Camera.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    restoreState = true
                    launchSingleTop = true
                }
            })
    }
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
                text = "Good Morning,",
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
                        navController.navigate(Screen.Profile.route) {
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
fun BottomSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        StatisticSection()
        Spacer(modifier = Modifier.height(32.dp))
        MoodSection(modifier = Modifier.padding(horizontal = 24.dp))
        Spacer(modifier = Modifier.height(32.dp))
        ArticleSection()
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
            Text(text = "Check your mood now", style = MaterialTheme.typography.labelLarge)
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null
            )
        }
    }
}

@Composable
fun StatisticSection(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(horizontal = 24.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8
            .dp)) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Today's Mood Score", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "34/50", style = MaterialTheme.typography.titleLarge)
                }
            }
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Today's Mood", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Happy", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}

@Composable
fun MoodSection(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val moodItems = listOf(
            MoodItemContent(
                mood = Mood.HAPPY,
                day = "Monday"
            ),
            MoodItemContent(
                mood = Mood.SAD,
                day = "Tuesday"
            ),
            MoodItemContent(
                mood = Mood.ANGER,
                day = "Wednesday"
            ),
            MoodItemContent(
                mood = Mood.HAPPY,
                day = "Thursday"
            ),
            MoodItemContent(
                mood = Mood.NEUTRAL,
                day = "Friday"
            ),
        )
        Text(text = "5-day mood history", style = MaterialTheme.typography.titleLarge)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            moodItems.forEach {
                MoodItem(item = it)
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
        Text(text = "Articles for you", style = MaterialTheme.typography.titleLarge, modifier =
        Modifier.padding(horizontal = 24.dp))
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
