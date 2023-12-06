package com.mahardika.comets.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahardika.comets.ui.commons.ArticleItemContent
import com.mahardika.comets.ui.screen.home.components.ArticleItem
import com.mahardika.comets.ui.screen.home.components.MoodItem

@Composable
fun HomeScreen()
{
    val scrollState = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
            .verticalScroll(scrollState)
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        HeaderSection()
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.clip(
                shape = RoundedCornerShape(
                    32.dp,
                    32.dp,
                    0.dp,
                    0.dp
                )
            )
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier)
                MoodSection()
                ArticleSection()
                Spacer(modifier = Modifier.height(32.dp))
            }
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
        StatisticSection()
    }
}

@Composable
fun StatisticSection()
{
    Column() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Statistic",
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(16.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Mental Health Score",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "70",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "/100",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Today's Mood",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Neutral",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
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
)
{
    Column(
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
            fontWeight = FontWeight.Medium
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
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

