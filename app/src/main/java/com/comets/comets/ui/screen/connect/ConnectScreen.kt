package com.comets.comets.ui.screen.connect

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.comets.comets.ui.navigation.Screen
import com.comets.comets.ui.screen.add_post.AddPostScreen
import com.comets.comets.ui.screen.add_room.AddRoomScreen
import com.comets.comets.ui.screen.classroom.ClassroomScreen
import com.comets.comets.ui.screen.classroom_detail.ClassroomDetailScreen
import com.comets.comets.ui.screen.community_forum.CommunityForumScreen
import com.comets.comets.ui.screen.payment_detail.PaymentDetailScreen
import com.comets.comets.ui.screen.pyschologist.PsychologistScreen
import com.comets.comets.ui.screen.pyschologist_detail.PsychologistDetailScreen

@Composable
fun ConnectScreen(
    navController: NavHostController = rememberNavController(),
    applicationContentNavController: NavHostController,
) {
    var pageState by remember {
        mutableIntStateOf(0)
    }
    BackHandler {
        navController.navigate(Screen.ApplicationContent.Connect.Psychologist.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
            pageState = 0
        }
    }
    Column(
        modifier = Modifier.padding(
            horizontal = 16.dp
        )
    ) {

        var showChips by remember {
            mutableStateOf(true)
        }
        if (showChips) {
            Spacer(modifier = Modifier.height(24.dp))
            NavigationChips() {
                itemsIndexed(items = Screen.ApplicationContent.Connect.children) { index, item ->
                    NavigationChip(
                        title = item.title,
                        isSelected = pageState == index
                    ) {
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
        }
        NavHost(
            navController = navController,
            startDestination = Screen.ApplicationContent.Connect.Psychologist.route,
        ) {
            composable(Screen.ApplicationContent.Connect.Psychologist.route) {
                showChips = true
                PsychologistScreen(navController = navController)
            }
            composable(Screen.ApplicationContent.Connect.PaymentDetail.route) {
                showChips = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    PaymentDetailScreen()
                }
            }
            composable(Screen.ApplicationContent.Connect.CommunityForum.route) {
                showChips = true
                CommunityForumScreen(navController = navController)
            }
            composable(Screen.ApplicationContent.Connect.AddPost.route) {
                showChips = false
                AddPostScreen(navController = navController)
            }
            composable(Screen.ApplicationContent.Connect.Classroom.route) {
                showChips = true
                ClassroomScreen(navController = navController)
            }
            composable(Screen.ApplicationContent.Connect.AddRoom.route) {
                showChips = false
                AddRoomScreen(navController = navController)
            }
            composable(
                "${Screen.ApplicationContent.Connect.PsychologistDetail.route}/{id}",
                arguments = listOf(navArgument("id") {
                    type = NavType.IntType
                })
            ) {
                showChips = false
                PsychologistDetailScreen(
                    id = it.arguments?.getInt("id") ?: 0,
                    navController = navController
                )
            }
            composable(
                "${
                    Screen.ApplicationContent.Connect.ClassroomDetail.route
                }/{userRoomUuid}/{uuid}/{roleRoom}",
                arguments = listOf(navArgument("userRoomUuid") {
                    type = NavType.StringType
                },
                    navArgument("uuid") {
                        type = NavType.StringType
                    },
                    navArgument("roleRoom") {
                        type = NavType.StringType
                    })
            ) {
                showChips = false
                ClassroomDetailScreen(
                    userRoomUuid = it.arguments?.getString("userRoomUuid") ?: "",
                    uuid = it.arguments?.getString("uuid") ?: "",
                    roleRoom = it.arguments?.getString("roleRoom") ?: "",
                    applicationContentNavController = applicationContentNavController
                )
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
    onClick: () -> Unit,
) {
    Surface(color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .clickable { onClick() }) {
        Text(
            text = title,
            fontSize = 12.sp,
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 4.dp
            )
        )
    }
}

