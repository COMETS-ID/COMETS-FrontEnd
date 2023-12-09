package com.mahardika.comets.ui.screen.home

import android.util.Log
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
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.mahardika.comets.ui.commons.ArticleItemContent
import com.mahardika.comets.ui.navigation.Screen
import com.mahardika.comets.ui.screen.home.components.ArticleItem
import com.mahardika.comets.ui.screen.home.components.MoodItem
import com.mahardika.comets.ui.screen.home.components.MoodSelectorItem

@Composable
fun HomeScreen(
    navController: NavHostController
){
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        var topHeight by remember { mutableStateOf(0.dp) }
        val maxHeight = this.maxHeight
        val density = LocalDensity.current

        val remainingHeight = maxHeight - topHeight
        Log.d("max height", maxHeight.toString())
        Log.d("top height", topHeight.toString())
        Log.d("remaining height", remainingHeight.toString())

        TopSection(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .onGloballyPositioned {
                    topHeight = with(density) {
                        it.size.height.toDp()
                    }
                }
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

        val centerHeight = 64.dp
        val centerBottomPadding = remainingHeight - centerHeight / 2

        MenuSection(
            modifier = Modifier
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
                }
        )
    }
}

@Composable
fun TopSection(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .background(MaterialTheme.colorScheme.primary)
    ) {
        HeaderSection()
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun BottomSection(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.background(MaterialTheme.colorScheme.surface)) {
        Spacer(modifier = Modifier.height(64.dp))
        MoodSection(modifier = Modifier.padding(horizontal = 24.dp))
        Spacer(modifier = Modifier.height(32.dp))
        ArticleSection()
    }
}

@Composable
fun MenuSection(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.secondaryContainer,
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Check your mood now")
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null
            )
        }
    }
}

@Composable
fun HeaderSection()
{
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Column {
            Text(
                text = "Good Morning,",
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Eren Yeager",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        HowAreYouSection()
    }
}

@Composable
fun HowAreYouSection()
{
    Column() {
        Text(
            text = "How are you today?",
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        ) {
            MoodSelectorItem(
                icon = Icons.Default.Mood,
                mood = "Angry",
                modifier = Modifier.weight(1f)
            )
            MoodSelectorItem(
                icon = Icons.Default.Mood,
                mood = "Sad",
                modifier = Modifier.weight(1f)
            )
            MoodSelectorItem(
                icon = Icons.Default.Mood,
                mood = "Neutral",
                modifier = Modifier.weight(1f)
            )
            MoodSelectorItem(
                icon = Icons.Default.Mood,
                mood = "Good",
                modifier = Modifier.weight(1f)
            )
            MoodSelectorItem(
                icon = Icons.Default.Mood,
                mood = "Happy",
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Based on the assessment you have done before",
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun MoodSection(
    modifier: Modifier = Modifier
)
{
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "5-day mood history",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            MoodItem(
                mood = "Happy",
                day = "Mon",
                icon = Icons.Default.Person
            )
            MoodItem(
                mood = "Happy",
                day = "Mon",
                icon = Icons.Default.Person
            )
            MoodItem(
                mood = "Happy",
                day = "Mon",
                icon = Icons.Default.Person
            )
            MoodItem(
                mood = "Happy",
                day = "Mon",
                icon = Icons.Default.Person
            )
            MoodItem(
                mood = "Happy",
                day = "Mon",
                icon = Icons.Default.Person
            )

        }
    }
}

@Composable
fun ArticleSection()
{
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
        Text(
            text = "Articles for you",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 24.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                items(
                    items = articleList
                ) {item ->
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

