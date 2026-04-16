package com.example.placementprojectmp.integration.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.placementprojectmp.integration.data.dto.PlatformType
import com.example.placementprojectmp.integration.presentation.viewmodel.PlatformLinksViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlatformLinksScreen(
    studentId: Long,
    modifier: Modifier = Modifier
) {
    val vm: PlatformLinksViewModel = koinViewModel()
    val state by vm.ui.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        PlatformType.entries.forEach { type ->
            OutlinedTextField(
                value = state.urls[type].orEmpty(),
                onValueChange = { vm.update(type, it) },
                label = { Text(type.name + if (type in requiredTypes) " *" else "") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        state.error?.let { Text(it) }
        Button(onClick = { vm.submit(studentId) }, enabled = !state.loading, modifier = Modifier.fillMaxWidth()) {
            Text(if (state.loading) "Submitting..." else "Save Platform Links")
        }
    }
}

private val requiredTypes = setOf(
    PlatformType.GITHUB,
    PlatformType.LINKEDIN,
    PlatformType.RESUME
)

