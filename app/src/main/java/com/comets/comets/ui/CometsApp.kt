package com.comets.comets.ui

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
import androidx.compose.runtime.collectAsState
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
import com.comets.comets.AppDependencies
import com.comets.comets.ui.components.BottomBar
import com.comets.comets.ui.components.ProfileImageButton
import com.comets.comets.ui.navigation.Screen
import com.comets.comets.ui.screen.camera.CameraScreen
import com.comets.comets.ui.screen.camera_result.CameraResultScreen
import com.comets.comets.ui.screen.connect.ConnectScreen
import com.comets.comets.ui.screen.home.HomeScreen
import com.comets.comets.ui.screen.journal.JournalScreen
import com.comets.comets.ui.screen.login.LoginScreen
import com.comets.comets.ui.screen.onboarding.OnboardingScreen
import com.comets.comets.ui.screen.prediction_result.PredictionResultScreen
import com.comets.comets.ui.screen.profile.ProfileScreen
import com.comets.comets.ui.screen.questionnaire.QuestionnaireScreen
import com.comets.comets.ui.screen.questionnaire_result.QuestionnaireResultScreen
import com.comets.comets.ui.screen.signup.SignupScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CometsApp(
    navController: NavHostController = rememberNavController(),
    appDependencies: AppDependencies,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentScreen = when (currentRoute) {
        Screen.ApplicationContent.Home.route -> Screen.ApplicationContent.Home
        Screen.ApplicationContent.Journal.route -> Screen.ApplicationContent.Journal
        Screen.ApplicationContent.Camera.route -> Screen.ApplicationContent.Camera
        Screen.ApplicationContent.CameraResult.route -> Screen.ApplicationContent.CameraResult
        Screen.ApplicationContent.Connect.route -> Screen.ApplicationContent.Connect
        Screen.ApplicationContent.Connect.Psychologist.route -> Screen.ApplicationContent.Connect.Psychologist
        Screen.ApplicationContent.Connect.CommunityForum.route -> Screen.ApplicationContent.Connect.CommunityForum
        Screen.ApplicationContent.Connect.AddPost.route -> Screen.ApplicationContent.Connect.AddPost
        Screen.ApplicationContent.Connect.Classroom.route -> Screen.ApplicationContent.Connect.Classroom
        Screen.ApplicationContent.Connect.ClassroomDetail.route -> Screen.ApplicationContent.Connect.ClassroomDetail
        Screen.ApplicationContent.Connect.PsychologistDetail.route -> Screen.ApplicationContent.Connect.PsychologistDetail
        Screen.ApplicationContent.Profile.route -> Screen.ApplicationContent.Profile
        Screen.ApplicationContent.Questionnaire.route -> Screen.ApplicationContent.Questionnaire
        Screen.ApplicationContent.QuestionnaireResult.route + "/{score}" -> Screen.ApplicationContent.QuestionnaireResult
        else -> null
    }

    val isLoggedIn = appDependencies.authTokenPreferences.authToken.collectAsState(initial = "")


    BackHandler {
        navController.navigate(Screen.ApplicationContent.Home.route) {
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
                                navController.navigate(Screen.ApplicationContent.Profile.route) {
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
            startDestination = if (!isLoggedIn.value.isNullOrEmpty()) Screen.ApplicationContent.route else Screen.Authentication.route,
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
            navigation(
                route = Screen.ApplicationContent.route,
                startDestination = Screen.ApplicationContent.Home.route
            ) {
                composable(Screen.ApplicationContent.Home.route) {
                    HomeScreen(navController = navController)
                }
                composable(Screen.ApplicationContent.Journal.route) {
                    JournalScreen()
                }
                composable("${Screen.ApplicationContent.Camera.route}/{uuid}",
                    arguments = listOf(navArgument("uuid") {
                        type = NavType.StringType
                    })
                ) {
                    CameraScreen(
                        appDependencies = appDependencies,
                        navController = navController,
                        uuid = it.arguments?.getString("uuid") ?: ""
                    )
                }
                composable(
                    "${Screen.ApplicationContent.CameraResult.route}/{uri}/{uuid}",
                    arguments = listOf(navArgument("uri") {
                        type = NavType.StringType
                    },
                        navArgument("uuid") {
                            type = NavType.StringType
                        })
                ) {
                    CameraResultScreen(
                        navController = navController,
                        uri = it.arguments?.getString("uri") ?: "",
                        uuid = it.arguments?.getString("uuid") ?: ""
                    )
                }
                composable(
                    "${
                        Screen.ApplicationContent.PredictionResult.route
                    }/{prediction}/{uuid}",
                    arguments = listOf(navArgument("prediction") {
                        type = NavType.StringType
                    },
                        navArgument("uuid") {
                            type = NavType.StringType
                        })
                ) {
                    PredictionResultScreen(
                        prediction = it.arguments?.getString("prediction") ?: "",
                        navController = navController,
                        uuid = it.arguments?.getString("uuid") ?: ""
                    )
                }
                composable(Screen.ApplicationContent.Connect.route) {
                    ConnectScreen(applicationContentNavController = navController)
                }
                composable(Screen.ApplicationContent.Profile.route) {
                    ProfileScreen(navController = navController)
                }
                composable(
                    "${Screen.ApplicationContent.Questionnaire.route}/{prediction}/{uuid}",
                    arguments = listOf(navArgument("prediction") {
                        type = NavType.StringType
                    },
                        navArgument("uuid") {
                            type = NavType.StringType
                        })
                ) {
                    QuestionnaireScreen(
                        navController,
                        it.arguments?.getString("prediction") ?: "",
                        uuid = it.arguments?.getString("uuid") ?: ""
                    )
                }
                composable(
                    "${
                        Screen.ApplicationContent.QuestionnaireResult.route
                    }/{prediction}/{score}/{uuid}",
                    arguments = listOf(navArgument("prediction") {
                        type = NavType.StringType
                    },
                        navArgument("score") {
                            type = NavType.IntType
                        },
                        navArgument("uuid") {
                            type = NavType.StringType
                        })
                ) {
                    QuestionnaireResultScreen(
                        prediction = it.arguments?.getString("prediction") ?: "",
                        score = it.arguments?.getInt("score") ?: 0,
                        uuid = it.arguments?.getString("uuid") ?: "",
                        navController = navController
                    )
                }
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
        }
    }
}

