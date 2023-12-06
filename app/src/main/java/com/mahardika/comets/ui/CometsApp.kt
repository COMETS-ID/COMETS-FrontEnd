package com.mahardika.comets.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bottombar.AnimatedBottomBar
import com.example.bottombar.components.BottomBarItem
import com.example.bottombar.model.IndicatorStyle
import com.example.bottombar.model.ItemStyle
import com.mahardika.comets.R
import com.mahardika.comets.ui.navigation.NavigationItem
import com.mahardika.comets.ui.navigation.Screen
import com.mahardika.comets.ui.screen.camera.CameraScreen
import com.mahardika.comets.ui.screen.connect.ConnectScreen
import com.mahardika.comets.ui.screen.home.HomeScreen
import com.mahardika.comets.ui.screen.journal.JournalScreen
import com.mahardika.comets.ui.screen.profile.ProfileScreen
import com.mahardika.comets.ui.theme.CometsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CometsApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
)
{
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (currentRoute != Screen.Home.route)
            {
                Surface(
                    shadowElevation = 4.dp
                ) {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = currentRoute.toString().replaceFirstChar {
                                    it.titlecase()
                                },
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    )
                }
            } else
            {
//                TopBar()
            }
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                currentRoute = currentRoute
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            },
            popEnterTransition = {
                EnterTransition.None
            },
            popExitTransition = {
                ExitTransition.None
            },
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Journal.route) {
                JournalScreen()
            }
            composable(Screen.Camera.route) {
                CameraScreen()
            }
            composable(Screen.Connect.route) {
                ConnectScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}

@Preview
@Composable
fun CometsAppPreview()
{
    CometsTheme {
        CometsApp()
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    currentRoute: String?
)
{
    var selectedItem by remember { mutableIntStateOf(0) }

    val navigationItems = listOf(
        NavigationItem(
            title = "Home",
            icon = ImageVector.vectorResource(R.drawable.ic_home_outline),
            selectedIcon = ImageVector.vectorResource(R.drawable.ic_home_fill),
            screen = Screen.Home
        ),
        NavigationItem(
            title = "Journal",
            icon = ImageVector.vectorResource(R.drawable.ic_book_mutliple_online),
            selectedIcon = ImageVector.vectorResource(R.drawable.ic_book_multiple_fill),
            screen = Screen.Journal
        ),
        NavigationItem(
            title = "Camera",
            icon = ImageVector.vectorResource(R.drawable.ic_face_recognition),
            selectedIcon = null,
            screen = Screen.Camera
        ),
        NavigationItem(
            title = "Connect",
            icon = ImageVector.vectorResource(R.drawable.ic_account_group_outline),
            selectedIcon = ImageVector.vectorResource(R.drawable.ic_account_group_fill),
            screen = Screen.Connect
        ),
        NavigationItem(
            title = "Profile",
            icon = ImageVector.vectorResource(R.drawable.ic_account_outline),
            selectedIcon = ImageVector.vectorResource(R.drawable.ic_account_fill),
            screen = Screen.Profile
        ),
    )

    AnimatedBottomBar(
        selectedItem = selectedItem,
        itemSize = navigationItems.size,
        containerColor = MaterialTheme.colorScheme.surface,
        containerShape = RoundedCornerShape(16.dp),
        indicatorStyle = IndicatorStyle.LINE,
        indicatorColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.shadow(16.dp)
    ) {
        navigationItems.forEachIndexed { index, item ->
            BottomBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                    selectedItem = index
                },
                imageVector = item.icon,
                label = item.title,
                itemStyle = ItemStyle.STYLE4,
                iconColor = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

