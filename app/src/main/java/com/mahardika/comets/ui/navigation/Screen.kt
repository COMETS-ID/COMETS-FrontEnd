package com.mahardika.comets.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Journal : Screen("journal")
    object Camera : Screen("camera")
    object Connect : Screen("connect")
    object Profile : Screen("screen")
}
