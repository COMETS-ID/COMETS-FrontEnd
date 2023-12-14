package com.mahardika.comets.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.mahardika.comets.R
import com.mahardika.comets.ui.navigation.NavigationItem
import com.mahardika.comets.ui.navigation.Screen

@Composable
fun BottomBar(
    navController: NavHostController,
    currentRoute: String?,
) {
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
            NavigationBarItem(selected = currentRoute == item.screen.route,
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
                    Text(text = item.title, style = MaterialTheme.typography.labelSmall)
                })
        }
    }
}