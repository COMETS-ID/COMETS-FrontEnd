package com.mahardika.comets.ui.screen.camera

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material.icons.filled.Lens
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mahardika.comets.AppDependencies
import com.mahardika.comets.ui.navigation.Screen
import com.mahardika.comets.ui.viewmodels.camera.CameraViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun CameraScreen(
    appDependencies: AppDependencies,
    navController: NavHostController,
    viewModel: CameraViewModel = hiltViewModel(),
) {
    val cameraUiState by viewModel.uiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    if (cameraUiState.cameraAllowed) {
        CameraView(
            outputDirectory = appDependencies.outputDirectory,
            executor = appDependencies.cameraExecutor,
            onImageCaptured = {
                Log.d("uri", Screen.CameraResult.route + it.toString())
                val encodedUrl = URLEncoder.encode(it.toString(),
                    "utf-8")
                coroutineScope.launch(Dispatchers.Main) {
                    navController.navigate(Screen.CameraResult.route + "/$encodedUrl")
                }
            },
            onError = {
                Log.e(
                    "image_capture_error",
                    "Error: ",
                    it
                )
            },
            navController = navController
        )
    } else {
        Text(text = "Need camera permission")
    }
}

@Composable
fun CameraView(
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit,
    navController: NavHostController,
) {
    var lensFacing by remember {
        mutableIntStateOf(CameraSelector.LENS_FACING_FRONT)
    }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview
        .Builder()
        .build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember {
        ImageCapture
            .Builder()
            .build()
    }
    val cameraSelector = CameraSelector
        .Builder()
        .requireLensFacing(lensFacing)
        .build()

    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        AndroidView(
            factory = {
                previewView
            },
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        0.dp,
                        0.dp,
                        16.dp,
                        16.dp
                    )
                )
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    navController.navigateUp()
                },
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.primary,
                        CircleShape
                    )
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            IconButton(onClick = {
                takePhoto(
                    fileFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                    imageCapture = imageCapture,
                    outputDirectory = outputDirectory,
                    executor = executor,
                    onImageCaptured = onImageCaptured,
                    onError = onError
                )
            }) {
                Icon(
                    imageVector = Icons.Default.Lens,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(1.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onSurface,
                            CircleShape
                        )
                )
            }
            IconButton(
                onClick = {
                    lensFacing =
                        if (lensFacing == CameraSelector.LENS_FACING_BACK) CameraSelector.LENS_FACING_FRONT else CameraSelector.LENS_FACING_BACK
                },
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.primary,
                        CircleShape
                    )
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FlipCameraAndroid,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

private fun takePhoto(
    fileFormat: String,
    imageCapture: ImageCapture,
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit,
) {
    val photoFile = File(
        outputDirectory,
        SimpleDateFormat(
            fileFormat,
            Locale.US
        ).format(System.currentTimeMillis()) + ".jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions
        .Builder(photoFile)
        .build()

    imageCapture.takePicture(outputOptions,
        executor,
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(photoFile)
                onImageCaptured(savedUri)
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e(
                    "take_picture",
                    "Take photo error: ",
                    exception
                )
                onError(exception)
            }
        })
}


private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider
            .getInstance(this)
            .also { cameraProvider ->
                cameraProvider.addListener(
                    {
                        continuation.resume(cameraProvider.get())
                    },
                    ContextCompat.getMainExecutor(this)
                )
            }
    }