package com.mahardika.comets

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mahardika.comets.ui.CometsApp
import com.mahardika.comets.ui.theme.CometsTheme
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity()
{
    private val appDependencies by lazy {
        AppDependencies(
            outputDirectory = getOutputDirectory(),
            cameraExecutor = Executors.newSingleThreadExecutor(),
        )
    }

    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            CometsTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    requestCameraPermission()

                    CometsApp(
                        appDependencies = appDependencies,
                        shouldShowCamera = shouldShowCamera,
                        onShouldShowCameraChange = { newState ->
                            shouldShowCamera.value = newState
                        }
                    )
                }
            }
        }
    }

    override fun onDestroy()
    {
        super.onDestroy()
        appDependencies.cameraExecutor.shutdown()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Log.d("camera_permission", "Permission Granted")
        } else {
            Log.d("camera_permission", "Permission Denied")
        }
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.d("camera_permission", "Permission already granted")
                shouldShowCamera.value = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> Log.d("camera_permission", "Show camera request permission dialog")

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }
}

class AppDependencies(
    val outputDirectory: File,
    val cameraExecutor: ExecutorService,
)

