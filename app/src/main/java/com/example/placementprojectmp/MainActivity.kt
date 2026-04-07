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
        // Prevent restoring stale NavController back stack state (e.g., old "startup" route)
        // that can crash when the current root graph starts from student flow.
        super.onCreate(null)
        enableEdgeToEdge()
        setContent {
            PlacementProjectMPTheme {
                AppNavGraph(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
