package com.mahardika.comets.ui.screen.camera_result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.mahardika.comets.ui.components.PrimaryButton
import java.net.URLDecoder

@Composable
fun CameraResultScreen(
    navController: NavHostController,
    uri: String
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 32.dp)){
        Image(
            painter = rememberAsyncImagePainter(URLDecoder.decode(uri, "utf-8")),
            contentDescription = null,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        Row (modifier = Modifier.align(Alignment.BottomCenter).padding(horizontal = 16.dp)){
            PrimaryButton(text = "Analyze") {

            }
        }
    }
}