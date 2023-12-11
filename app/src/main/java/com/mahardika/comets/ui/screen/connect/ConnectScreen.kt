package com.mahardika.comets.ui.screen.connect

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mahardika.comets.ui.navigation.Screen
import com.mahardika.comets.ui.screen.classroom.ClassroomScreen
import com.mahardika.comets.ui.screen.community_forum.CommunityForumScreen
import com.mahardika.comets.ui.screen.pyschologist.PsychologistScreen

@Composable
fun ConnectScreen(
    navController: NavHostController = rememberNavController()
)
{
    Column(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState()),
    ){
        var pageState by remember {
            mutableIntStateOf(0)
        }
        Spacer(modifier = Modifier.height(8.dp))
        NavigationChips(){
            itemsIndexed(items = Screen.Connect.children) { index, item ->
                NavigationChip(title = item.title, isSelected = pageState == index) {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                    pageState = index
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        NavHost(
            navController = navController,
            startDestination = Screen.Connect.Psychologist.route,
        ) {
            composable(Screen.Connect.Psychologist.route){
                PsychologistScreen()
            }
            composable(Screen.Connect.CommunityForum.route){
                CommunityForumScreen()
            }
            composable(Screen.Connect.Classroom.route){
                ClassroomScreen()
            }
        }
    }
}

@Composable
fun NavigationChips(content: LazyListScope.() -> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        content = content
    )
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

