package com.example.placementprojectmp.ui.screens.system.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.placementprojectmp.ui.screens.system.component.ReportCard
import com.example.placementprojectmp.ui.screens.system.component.ReportCardTrend

/**
 * Fixed demo analytics block for System-only company/drive detail routes.
 */
@Composable
fun SystemPlacementAnalyticsReportCard(modifier: Modifier = Modifier) {
    ReportCard(
        modifier = modifier,
        placementSuccessPercent = 78,
        trend = ReportCardTrend.UP,
        jobsTotalValue = "128",
        applicantsTotalValue = "3,420",
        selectionsTotalValue = "214"
    )
}
