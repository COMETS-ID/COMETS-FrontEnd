package com.mahardika.comets.ui.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahardika.comets.ui.commons.CarouselItemContent
import com.mahardika.comets.ui.screen.home.components.ArticleItem
import com.mahardika.comets.ui.screen.home.components.CarouselItem
import com.mahardika.comets.ui.screen.home.components.MoodItem
import com.mahardika.comets.ui.screen.home.components.StatisticItem

@Composable
fun HomeScreen() {
    val scrollState = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier)
        CarouselSection()
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            StatisticSection()
            MoodSection()
            ArticleSection()
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselSection() {
    val carouselItemContent = listOf(
        CarouselItemContent(title = "Title 1", description = "Description 1"),
        CarouselItemContent(title = "Title 2", description = "Description 2"),
        CarouselItemContent(title = "Title 3", description = "Description 3")
    )
    val pagerState = rememberPagerState(
        pageCount = {
            3
        }
    )
    HorizontalPager(
        state = pagerState,
        pageSpacing = 16.dp,
        contentPadding = PaddingValues(horizontal = 24.dp),
        modifier = Modifier.height(200.dp),
    ) {
        carouselItemContent.getOrNull(it)?.let { item ->
            CarouselItem(item = item)
        }
    }
}

@Composable
fun StatisticSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Statistic",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.height(IntrinsicSize.Max)
        ) {
            StatisticItem(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Mental Health Score",
                        fontSize = 12.sp
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "70",
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Yellow,
                                style = TextStyle(
                                    platformStyle = PlatformTextStyle(
                                        includeFontPadding = false
                                    )
                                )
                            )
                            Text(
                                text = "/100"
                            )
                        }
                        Text(
                            text = "Okay",
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
            StatisticItem(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Column {
                    Text(
                        text = "Today's Mood",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun MoodSection(
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "5-day mood history",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
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
fun ArticleSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "Articles for you",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    ArticleItem(
                        title = "Lorem Ipsum Dolor Sit Amet",
                        description = "Lorem ipsum dolor sit amet consectetur adipiscing elit",
                        modifier = Modifier.fillParentMaxWidth(0.5f)
                    )
                }
                item {
                    ArticleItem(
                        title = "Lorem Ipsum Dolor Sit Amet",
                        description = "Lorem ipsum dolor sit amet consectetur adipiscing elit",
                        modifier = Modifier.fillParentMaxWidth(0.5f)
                    )
                }
                item {
                    ArticleItem(
                        title = "Lorem Ipsum Dolor Sit Amet",
                        description = "Lorem ipsum dolor sit amet consectetur adipiscing elit",
                        modifier = Modifier.fillParentMaxWidth(0.5f)
                    )
                }
            }
        }
    }
}

