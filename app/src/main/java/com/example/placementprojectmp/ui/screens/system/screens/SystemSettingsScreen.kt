package com.example.placementprojectmp.ui.screens.system.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.screens.staff.components.SectionHeader

@Composable
fun SystemSettingsScreen(
    modifier: Modifier = Modifier
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var accessControlStrict by remember { mutableStateOf(false) }
    var featureToggleA by remember { mutableStateOf(true) }
    var darkThemeEnabled by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { SectionHeader("System Settings") }
        item {
            SettingsToggleItem(
                title = "Notification Settings",
                enabled = notificationsEnabled,
                onChange = { notificationsEnabled = it }
            )
        }
        item {
            SettingsToggleItem(
                title = "Access Control",
                enabled = accessControlStrict,
                onChange = { accessControlStrict = it }
            )
        }
        item {
            SettingsToggleItem(
                title = "Feature Toggle (Mock)",
                enabled = featureToggleA,
                onChange = { featureToggleA = it }
            )
        }
        item {
            SettingsToggleItem(
                title = "Theme (Dark)",
                enabled = darkThemeEnabled,
                onChange = { darkThemeEnabled = it }
            )
        }
    }
}

@Composable
private fun SettingsToggleItem(
    title: String,
    enabled: Boolean,
    onChange: (Boolean) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Switch(checked = enabled, onCheckedChange = onChange)
        }
    }
}
