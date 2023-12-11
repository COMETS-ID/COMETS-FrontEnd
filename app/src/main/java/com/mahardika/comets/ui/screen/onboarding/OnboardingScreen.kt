package com.mahardika.comets.ui.screen.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.mahardika.comets.R
import com.mahardika.comets.ui.commons.PrimaryButton
import com.mahardika.comets.ui.navigation.Screen

@Composable
fun OnboardingScreen(
    navController: NavHostController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Comets",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Track Your Mood Now"
        )
        Spacer(modifier = Modifier.height(96.dp))
        Image(
            painter = painterResource(id = R.drawable.onboarding_img),
            contentDescription = null,
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Let's get you in!",
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(132.dp))
        PrimaryButton(text = "Login") {
            navController.navigate(Screen.Authentication.Login.route){
                popUpTo(navController.graph.findStartDestination().id){
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton(text = "Signup") {
            
        }
    }
}