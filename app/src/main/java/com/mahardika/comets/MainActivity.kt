package com.mahardika.comets

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mahardika.comets.ui.CometsApp
import com.mahardika.comets.ui.theme.CometsTheme
import com.mahardika.comets.ui.viewmodels.camera.CameraViewModel
import com.mahardika.comets.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val appDependencies by lazy {
        AppDependencies(
            outputDirectory = getOutputDirectory(),
            cameraExecutor = Executors.newSingleThreadExecutor(),
        )
    }
    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)

    private val cameraViewModel: CameraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CometsTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    requestCameraPermission()
                    CometsApp(appDependencies = appDependencies)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        appDependencies.cameraExecutor.shutdown()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            showToast(
                applicationContext,
                "Camera permission granted"
            )
        } else {
            showToast(
                applicationContext,
                "Camera permission denied. Some features will be unavailable"
            )
        }
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.d(
                    "camera allowed",
                    cameraViewModel.uiState.value.cameraAllowed.toString()
                )
                shouldShowCamera.value = true
                cameraViewModel.updatePermissionState(true)
                Log.d(
                    "camera allowed",
                    cameraViewModel.uiState.value.cameraAllowed.toString()
                )
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> showToast(
                applicationContext,
                "Camera permission is required to access important feature"
            )

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs
            .firstOrNull()
            ?.let {
                File(
                    it,
                    resources.getString(R.string.app_name)
                ).apply { mkdirs() }
            }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }
}

class AppDependencies @Inject constructor(
    val outputDirectory: File,
    val cameraExecutor: ExecutorService,
)


