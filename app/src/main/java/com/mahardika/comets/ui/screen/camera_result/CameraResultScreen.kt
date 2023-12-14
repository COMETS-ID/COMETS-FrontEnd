package com.mahardika.comets.ui.screen.camera_result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.mahardika.comets.ui.navigation.Screen
import java.net.URLDecoder

@Composable
fun CameraResultScreen(
    navController: NavHostController,
    uri: String,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                URLDecoder.decode(
                    uri,
                    "utf-8"
                )
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(
                    32.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(onClick = {
                                 navController.navigateUp()
            }, colors = IconButtonDefaults.iconButtonColors(MaterialTheme
                .colorScheme.primary), modifier = Modifier.padding(4.dp)) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Button(
                onClick = {
                          navController.navigate(Screen.Questionnaire.route) {
                              popUpTo(navController.graph.findStartDestination().id) {
                                  saveState = true
                              }
                              launchSingleTop = true
                              restoreState = true
                          }
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Analyze", fontSize = 24.sp)
            }
        }
    }
}