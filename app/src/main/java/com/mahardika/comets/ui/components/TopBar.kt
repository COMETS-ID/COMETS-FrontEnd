package com.mahardika.comets.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mahardika.comets.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar()
{
    Surface(
        shadowElevation = 4.dp,
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Comets",
                    fontWeight = FontWeight.SemiBold
                )
            },
            navigationIcon = {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            },
            actions = {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}