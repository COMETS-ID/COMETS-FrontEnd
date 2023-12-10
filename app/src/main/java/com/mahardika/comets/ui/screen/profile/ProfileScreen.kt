package com.mahardika.comets.ui.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.mahardika.comets.ui.commons.PrimaryButton
import com.mahardika.comets.ui.navigation.Screen

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
)
{
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        PrimaryButton(text = "Logout") {
            navController.navigate(Screen.Authentication.route){
                popUpTo(navController.graph.findStartDestination().id){
                    inclusive = true
                }
            }
        }
    }
}