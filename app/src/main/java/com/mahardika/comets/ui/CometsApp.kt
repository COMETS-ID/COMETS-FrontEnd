package com.mahardika.comets.ui

import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.mahardika.comets.AppDependencies
import com.mahardika.comets.R
import com.mahardika.comets.ui.commons.ProfileImageButton
import com.mahardika.comets.ui.navigation.NavigationItem
import com.mahardika.comets.ui.navigation.Screen
import com.mahardika.comets.ui.screen.camera.CameraScreen
import com.mahardika.comets.ui.screen.camera_result.CameraResultScreen
import com.mahardika.comets.ui.screen.connect.ConnectScreen
import com.mahardika.comets.ui.screen.home.HomeScreen
import com.mahardika.comets.ui.screen.journal.JournalScreen
import com.mahardika.comets.ui.screen.login.LoginScreen
import com.mahardika.comets.ui.screen.onboarding.OnboardingScreen
import com.mahardika.comets.ui.screen.profile.ProfileScreen
import com.mahardika.comets.ui.screen.signup.SignupScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CometsApp(
    navController: NavHostController = rememberNavController(),
    appDependencies: AppDependencies,
    shouldShowCamera: State<Boolean>,
    onShouldShowCameraChange: (Boolean) -> Unit,
)
{
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentScreen = when (currentRoute) {
        Screen.Home.route -> Screen.Home
        Screen.Journal.route -> Screen.Journal
        Screen.Camera.route -> Screen.Camera
        Screen.CameraResult.route -> Screen.CameraResult
        Screen.Connect.route -> Screen.Connect
        Screen.Connect.Psychologist.route -> Screen.Connect.Psychologist
        Screen.Connect.CommunityForum.route -> Screen.Connect.CommunityForum
        Screen.Connect.Classroom.route -> Screen.Connect.Classroom
        Screen.Profile.route -> Screen.Profile
        else -> null
    }

    var uri by remember {
        mutableStateOf(Uri.parse(""))
    }
    Log.d("route", currentScreen?.route.toString())

    Scaffold(
        topBar = {
            if (currentScreen?.showTopBar == true) {
                Surface(
                    shadowElevation = 4.dp
                ) {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = currentScreen.title,
                                fontWeight = FontWeight.SemiBold
                            )
                        },
                        actions = {
                            ProfileImageButton(
                                modifier = Modifier.clickable {
                                    navController.navigate(Screen.Profile.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            inclusive = true
                                        }
                                        restoreState = true
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
                    )
                }
            }
        },
        bottomBar = {
            if (currentScreen?.showBottomBar == true) {
                BottomBar(
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
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
                HomeScreen(navController = navController)
            }
            composable(Screen.Journal.route) {
                JournalScreen()
            }
            composable(Screen.Camera.route) {
                CameraScreen(
                    appDependencies = appDependencies,
                    shouldShowCamera = shouldShowCamera,
                    onShouldShowCameraChange = onShouldShowCameraChange,
                    onSetUri = {
                        uri = it
                    },
                    navController = navController
                )
            }
            composable(Screen.CameraResult.route) {
                CameraResultScreen(uri = uri)
            }
            composable(Screen.Connect.route) {
                ConnectScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController = navController)
            }
            navigation(route = Screen.Authentication.route, startDestination = Screen.Authentication.Onboarding.route){
                composable(Screen.Authentication.Onboarding.route){
                    OnboardingScreen(navController = navController)
                }
                composable(Screen.Authentication.Login.route) {
                    LoginScreen(navController)
                }
                composable(Screen.Authentication.Signup.route) {
                    SignupScreen(navController)
                }
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    currentRoute: String?,
)
{
    var selectedItem by remember { mutableIntStateOf(0) }

    BackHandler {
        selectedItem = 0
        navController.popBackStack()
    }

    val navigationItems = listOf(
        NavigationItem(
            title = "Home",
            icon = Icons.Outlined.Home,
            selectedIcon = Icons.Default.Home,
            screen = Screen.Home
        ),
        NavigationItem(
            title = "Journal",
            icon = ImageVector.vectorResource(R.drawable.ic_book_mutliple_online),
            selectedIcon = ImageVector.vectorResource(R.drawable.ic_book_multiple_fill),
            screen = Screen.Journal
        ),
        NavigationItem(
            title = "Connect",
            icon = ImageVector.vectorResource(R.drawable.ic_account_group_outline),
            selectedIcon = ImageVector.vectorResource(R.drawable.ic_account_group_fill),
            screen = Screen.Connect
        ),
    )
    BottomNavigationBar(
        selectedItem = selectedItem,
        itemSize = navigationItems.size
    ) {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
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
                icon = {
                    if (currentRoute == item.screen.route) {
                        Icon(
                            imageVector = item.selectedIcon ?: item.icon,
                            contentDescription = null
                        )
                    } else {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.surface,
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface
                ),
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    containerShape: Shape = RectangleShape,
    selectedItem: Int? = null,
    itemSize: Int? = null,
    content: @Composable RowScope.() -> Unit
){
    Surface(
        modifier = modifier,
        color = containerColor,
        contentColor = contentColor,
        shape = containerShape
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            selectedItem?.let {
                itemSize?.let {
                    val maxWidth = this.maxWidth
                    val animationSpec: AnimationSpec<Dp> = spring(
                        dampingRatio = 1f,
                        stiffness = Spring.StiffnessLow,
                    )
                    val indicatorPosition: Dp by animateDpAsState(
                        targetValue = (maxWidth / (itemSize.takeIf { it != 0 }
                            ?: 1)) * selectedItem,
                        animationSpec = animationSpec,
                        label = "indicator"
                    )

                    Box(
                        modifier = Modifier.width(maxWidth / (itemSize.takeIf { it != 0 } ?: 1))
                    ) {
                        Box (
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 4.dp)
                                .customWormTransition(indicatorPosition,
                                    MaterialTheme.colorScheme.primary,
                                    maxWidth / (itemSize.takeIf { it != 0 } ?: 1)
                                )
                                .size(
                                    height = 4.dp,
                                    width = maxWidth / (itemSize.takeIf { it != 0 } ?: 1))
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp)
                            .selectableGroup(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        content = content
                    )
                }
            }
        }
    }
}

fun Modifier.customWormTransition(
    offset: Dp,
    indicatorColor: Color,
    itemWidth: Dp,
) = composed {
    drawWithContent {
        val distance = itemWidth.roundToPx()
        val scrollPosition = (offset.toPx().div(distance))
        val wormOffset = (scrollPosition % 1) * 2

        val xPos = scrollPosition.toInt() * distance

        val head = xPos + distance * 0f.coerceAtLeast(wormOffset - 1)
        val tail = xPos + size.width + distance * 1f.coerceAtMost(wormOffset)

        val adjustedHead = if (offset < 0.dp) head - distance else head
        val adjustedTail = if (offset < 0.dp) tail - distance else tail

        val worm = RoundRect(
            adjustedHead, 0f, adjustedTail, size.height,
            CornerRadius(50f)
        )


        val path = Path().apply { addRoundRect(worm) }
        drawPath(path = path, color = indicatorColor)
    }
}

