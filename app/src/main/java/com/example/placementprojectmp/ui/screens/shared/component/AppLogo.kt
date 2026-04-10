package com.example.placementprojectmp.ui.screens.shared.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    tint: Color? = null
) {
    Image(
        painter = painterResource(R.drawable.app_logo),
        contentDescription = "App logo",
        modifier = modifier.size(size),
        colorFilter = tint?.let { ColorFilter.tint(it) }
    )
}
