package com.example.placementprojectmp.ui.screens.system.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
@Composable
fun JobManagementScreen(
    modifier: Modifier = Modifier,
    onNavigateToCompanyDetail: (String) -> Unit = { _ ->
    },
    onNavigateToDriveDetail: (String) -> Unit = {}
) {
    CompanyManagementScreen(
        modifier = modifier,
        onCompanyClick = onNavigateToCompanyDetail,
        onDriveClick = onNavigateToDriveDetail
    )
}
