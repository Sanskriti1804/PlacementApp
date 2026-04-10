package com.example.placementprojectmp.ui.screens.system.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.placementprojectmp.ui.screens.staff.screens.StaffDriveScreen

@Composable
fun JobManagementScreen(
    modifier: Modifier = Modifier,
    onNavigateToDriveDetail: (String) -> Unit = {}
) {
    StaffDriveScreen(
        modifier = modifier,
        onCompanyClick = { sourceId -> onNavigateToDriveDetail(sourceId) },
        onDriveClick = { driveId -> onNavigateToDriveDetail(driveId) },
        onJobClick = { jobId -> onNavigateToDriveDetail(jobId) }
    )
}
