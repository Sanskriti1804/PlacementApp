package com.example.placementprojectmp.ui.screens.shared.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.placementprojectmp.ui.screens.staff.screens.StaffDriveDetailScreen

/**
 * Student and cross-module entry to the drive detail UI; delegates to the existing implementation.
 */
@Composable
fun DriveDetailScreen(
    modifier: Modifier = Modifier,
    driveId: String,
    onRegisterClick: () -> Unit = {},
    showRegisterButton: Boolean = true
) {
    StaffDriveDetailScreen(
        modifier = modifier,
        driveId = driveId,
        onRegisterClick = onRegisterClick,
        showRegisterButton = showRegisterButton
    )
}
