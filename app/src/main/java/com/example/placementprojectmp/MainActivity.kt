package com.example.placementprojectmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.placementprojectmp.navigation.AppNavGraph
import com.example.placementprojectmp.ui.theme.PlacementProjectMPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlacementProjectMPTheme {
                AppNavGraph(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
