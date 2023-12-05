package com.mahardika.comets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.mahardika.comets.ui.CometsApp
import com.mahardika.comets.ui.theme.CometsTheme

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            CometsTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    CometsApp()
                }
            }
        }
    }
}

