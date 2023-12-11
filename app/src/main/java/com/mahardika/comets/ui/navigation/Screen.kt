package com.mahardika.comets.ui.navigation

sealed class Screen(val route: String, val title: String = "Comets", val showTopBar: Boolean = true, val showBottomBar: Boolean = true)
{
    object Home : Screen("home", title = "Home", showTopBar = false)
    object Journal : Screen("journal", title = "Journal")
    object Camera : Screen("camera", title = "Camera", showTopBar = false, showBottomBar = false)
    object CameraResult : Screen("camera_result", title = "Camera Result")
    object Connect : Screen("connect", title = "Connect") {
        val children: List<Screen> by lazy {
            listOf(Psychologist, CommunityForum, Classroom)
        }
        object Psychologist : Screen("pyschologist", title = "Psychologist")
        object CommunityForum : Screen("community_forum", title = "Community Forum")
        object Classroom : Screen("classroom", title = "Classroom")
    }
    object Profile : Screen("screen", title = "Profile")

    object Authentication : Screen("authentication", title = "Authentication", showTopBar = false, showBottomBar = false) {
        object Login : Screen("login", title = "Login")
        object Signup : Screen("signup", title = "Signup")
        object Onboarding : Screen("onboarding", title = "Onboarding")
    }
}
