package com.mahardika.comets.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.mahardika.comets.AppDependencies
import com.mahardika.comets.ui.components.BottomBar
import com.mahardika.comets.ui.components.ProfileImageButton
import com.mahardika.comets.ui.navigation.Screen
import com.mahardika.comets.ui.screen.camera.CameraScreen
import com.mahardika.comets.ui.screen.camera_result.CameraResultScreen
import com.mahardika.comets.ui.screen.connect.ConnectScreen
import com.mahardika.comets.ui.screen.home.HomeScreen
import com.mahardika.comets.ui.screen.journal.JournalScreen
import com.mahardika.comets.ui.screen.login.LoginScreen
import com.mahardika.comets.ui.screen.onboarding.OnboardingScreen
import com.mahardika.comets.ui.screen.profile.ProfileScreen
import com.mahardika.comets.ui.screen.questionnaire.QuestionnaireScreen
import com.mahardika.comets.ui.screen.questionnaire_result.QuestionnaireResultScreen
import com.mahardika.comets.ui.screen.signup.SignupScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CometsApp(
    navController: NavHostController = rememberNavController(),
    appDependencies: AppDependencies,
) {
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
        Screen.Connect.PsychologistDetail.route -> Screen.Connect.PsychologistDetail
        Screen.Profile.route -> Screen.Profile
        Screen.Questionnaire.route -> Screen.Questionnaire
        Screen.QuestionnaireResult.route + "/{score}" -> Screen.QuestionnaireResult
        else -> null
    }

    BackHandler {
        navController.navigate(Screen.Home.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
        }
    }

    Scaffold(
        topBar = {
            if (currentScreen?.showTopBar == true) {
                Surface(
                    shadowElevation = 4.dp
                ) {
                    CenterAlignedTopAppBar(title = {
                        Text(
                            text = currentScreen.title,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                        actions = {
                            ProfileImageButton(modifier = Modifier.clickable {
                                navController.navigate(Screen.Profile.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        inclusive = true
                                    }
                                    restoreState = true
                                    launchSingleTop = true
                                }
                            })
                        })
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
                    navController = navController
                )
            }
            composable(
                "${Screen.CameraResult.route}/{uri}",
                arguments = listOf(navArgument("uri") {
                    type = NavType.StringType
                })
            ) {
                CameraResultScreen(
                    navController = navController,
                    uri = it.arguments?.getString("uri") ?: ""
                )
            }
            composable(Screen.Connect.route) {
                ConnectScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController = navController)
            }
            navigation(
                route = Screen.Authentication.route,
                startDestination = Screen.Authentication.Onboarding.route
            ) {
                composable(Screen.Authentication.Onboarding.route) {
                    OnboardingScreen(navController = navController)
                }
                composable(Screen.Authentication.Login.route) {
                    LoginScreen(navController)
                }
                composable(Screen.Authentication.Signup.route) {
                    SignupScreen(navController)
                }
            }
            composable(Screen.Questionnaire.route) {
                QuestionnaireScreen(navController)
            }
            composable("${Screen.QuestionnaireResult.route}/{score}", arguments = listOf
                (navArgument("score"){
                    type = NavType.IntType
            })) {
                QuestionnaireResultScreen(score = it.arguments?.getInt("score") ?: 0)
            }
        }
    }
}

