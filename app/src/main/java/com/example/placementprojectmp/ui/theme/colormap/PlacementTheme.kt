package com.example.placementprojectmp.ui.theme.colormap

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.example.placementprojectmp.ui.theme.NeonBlue
import com.example.placementprojectmp.ui.theme.NeonPurple

@Immutable
data class PlacementColors(
    val roleNeonStudent: Color = ColorMapper.getColor(UserRole.STUDENT),
    val roleNeonStaff: Color = ColorMapper.getColor(UserRole.STAFF),
    val roleNeonManagement: Color = ColorMapper.getColor(UserRole.MANAGEMENT),
    val roleNeonRecruiter: Color = ColorMapper.getColor(UserRole.RECRUITER),
    val roleNeonSystem: Color = ColorMapper.getColor(UserRole.SYSTEM),
    val placedStudent: Color = NeonBlue,
    val appPrimaryAccent: Color = NeonPurple
)

val LocalPlacementColors = staticCompositionLocalOf { PlacementColors() }

/**
 * Theme-style accessor:
 * MaterialTheme.colorScheme.customPlacementColors
 */
val ColorScheme.customPlacementColors: PlacementColors
    @Composable
    get() = LocalPlacementColors.current
