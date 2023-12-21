package com.comets.comets.ui.screen.add_post

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.comets.comets.ui.navigation.Screen

@Composable
fun AddPostScreen(
    navController: NavHostController,
    viewModel: AddPostViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    if (uiState.isSuccess) {
        navController.navigate(Screen.ApplicationContent.Connect.CommunityForum.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Share your thoughts",
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.text,
            onValueChange = {
                viewModel.updateTextField(it)
            },
            label = {
                Text(
                    "Post",
                    style = MaterialTheme.typography.labelMedium
                )
            },
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        Button(
            onClick = {
                viewModel.postCommunityForum()
            },
            shape = RoundedCornerShape(16.dp),
            enabled = (uiState.text.text.isNotBlank())
        ) {
            Text(text = "Post")
        }
    }
}

