package com.comets.comets.ui.screen.classroom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.comets.comets.data.response.Room
import com.comets.comets.ui.navigation.Screen

@Composable
fun ClassroomScreen(
    navController: NavHostController,
    viewModel: ClassroomViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(
        Unit
    ) {
        viewModel.getRooms()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(uiState.rooms) {
                ClassroomCard(room = it.room) {
                    navController.navigate(
                        "${
                            Screen.ApplicationContent.Connect.ClassroomDetail.route
                        }/${it.uuid}/${it.room.uuid}/${it.roleRoom}"
                    ) {
                        launchSingleTop = true
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        FloatingActionButton(
            onClick = {
                navController.navigate(
                    Screen.ApplicationContent.Connect.AddRoom.route
                ) {
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }
}

@Composable
fun ClassroomCard(
    room: Room,
    onClick: () -> Unit,
) {
    Card(shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = room.user.username,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = room.name,
                    style = MaterialTheme.typography.displayLarge
                )
                Text(
                    text = "${room.capacity} participants",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
