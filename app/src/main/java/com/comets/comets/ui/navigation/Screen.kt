package com.comets.comets.ui.navigation

sealed class Screen(
    val route: String,
    val title: String = "Comets",
    val showTopBar: Boolean = true,
    val showBottomBar: Boolean = true,
) {

    object ApplicationContent : Screen(
        "application_content"
    ) {
        object Home : Screen(
            "home",
            title = "Home",
            showTopBar = false
        )

        object Journal : Screen(
            "journal",
            title = "Journal"
        )

        object Camera : Screen(
            "camera",
            title = "Camera",
            showTopBar = false,
            showBottomBar = false
        )

        object CameraResult : Screen(
            "camera_result",
            title = "Camera Result"
        )

        object PredictionResult : Screen(
            "prediction_result",
            title = "Prediction Result"
        )

        object Connect : Screen(
            "connect",
            title = "Connect"
        ) {
            val children: List<Screen> by lazy {
                listOf(
                    Psychologist,
                    CommunityForum,
                    Classroom,
                )
            }

            object Psychologist : Screen(
                "pyschologist",
                title = "Psychologist"
            )

            object CommunityForum : Screen(
                "community_forum",
                title = "Community Forum",
            )

            object AddPost : Screen(
                "add_post",
                title = "Add Post"
            )

            object Classroom : Screen(
                "classroom",
                title = "Classroom"
            )

            object AddRoom : Screen(
                "add_room",
                title = "Add Room"
            )

            object PsychologistDetail : Screen(
                "psychologist_detail",
                title = "Psychologist Detail"
            )

            object PaymentDetail : Screen(
                "Payment Detail",
                title = "Payment Detail"
            )

            object ClassroomDetail : Screen(
                "classroom_detail",
                title = "Classroom Detail"
            )
        }

        object Questionnaire : Screen(
            "questionnaire",
            title = "Questionnaire"
        )

        object QuestionnaireResult : Screen(
            "questionnaire_result",
            title = "Questionnaire Result"
        )

        object Profile : Screen(
            "screen",
            title = "Profile"
        )
    }

    object Authentication : Screen(
        "authentication",
        title = "Authentication",
        showTopBar = false,
        showBottomBar = false
    ) {
        object Login : Screen(
            "login",
            title = "Login"
        )

        object Signup : Screen(
            "signup",
            title = "Signup"
        )

        object Onboarding : Screen(
            "onboarding",
            title = "Onboarding"
        )
    }
}
