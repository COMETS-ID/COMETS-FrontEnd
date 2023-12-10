package com.mahardika.comets

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
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
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
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
            showToast(applicationContext, "Camera permission granted")
        } else {
            showToast(applicationContext, "Camera permission denied. Some features will be unavailable")
        }
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                shouldShowCamera.value = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> showToast(applicationContext, "Camera permission is required to access important feature")

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

fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}

class AppDependencies(
    val outputDirectory: File,
    val cameraExecutor: ExecutorService,
)

