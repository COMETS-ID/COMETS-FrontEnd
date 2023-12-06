package com.mahardika.comets.ui.screen.connect

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun ConnectScreen()
{
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier.padding(16.dp)
    ){
        NavigationChips()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavigationChips() {
    val pageState = rememberPagerState{
        3
    }
    val coroutineScope = rememberCoroutineScope()
    val pageList = listOf("Psychologist", "Community Forum", "Classroom")
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        itemsIndexed(items = pageList){ index, item ->
            NavigationChip(title = item, isSelected = pageState.currentPage == index){
                coroutineScope.launch {
                    pageState.animateScrollToPage(index)
                }
            }
        }
    }
    HorizontalPager(state = pageState) {
        when(it){
           0 -> PsychologistSection()
           1 -> CommunitySection()
            2 -> ClassroomSection()
        }
    }
}

@Composable
fun NavigationChip(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Surface(
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .clickable { onClick() }
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 4.dp
                )
        )
    }
}

@Composable
fun PsychologistSection() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(text = "Psychologist")
    }
}
@Composable
fun CommunitySection() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(text = "Community")
    }
}
@Composable
fun ClassroomSection() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(text = "Classroom")
    }
}
