package com.example.placementprojectmp.ui.screens.shared.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object SystemCTADefaults {
    val MenuOptionLabels: List<String> = listOf(
        "Add Drive",
        "Add Company",
        "Add Job",
        "Add Student",
        "Add Tutorial Class",
        "Add Workshop"
    )
}

/**
 * Staff-style primary FAB (bottom-end) plus quick-add bottom sheet. Hosts [content] in a [androidx.compose.foundation.layout.Box];
 * FAB is layered above [content] with the same placement and styling as the Staff Dashboard implementation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemCTA(
    modifier: Modifier = Modifier,
    menuOptionLabels: List<String> = SystemCTADefaults.MenuOptionLabels,
    sheetTitle: String = "Quick add",
    fabContentDescription: String = "Quick add",
    onMenuOptionClick: ((String) -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    var showFabMenu by remember { mutableStateOf(false) }
    val fabSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Box(modifier = modifier) {
        content()
        FloatingActionButton(
            onClick = { showFabMenu = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 24.dp),
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = fabContentDescription
            )
        }
    }

    if (showFabMenu) {
        ModalBottomSheet(
            onDismissRequest = { showFabMenu = false },
            sheetState = fabSheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = sheetTitle,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                menuOptionLabels.forEach { label ->
                    TextButton(
                        onClick = {
                            showFabMenu = false
                            onMenuOptionClick?.invoke(label)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
