package com.example.placementprojectmp.ui.screens.shared.component

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun AppTabSection(
    modifier: Modifier = Modifier,
    tabTitles: List<String>,
    tabContents: List<String>
) {
    var selectedTab by remember { mutableStateOf(0) }
    var rowRootX by remember { mutableFloatStateOf(0f) }
    val tabSegments = remember(tabTitles) { mutableStateMapOf<Int, Pair<Float, Float>>() }
    val density = LocalDensity.current

    val targetSeg = tabSegments[selectedTab]
    val targetOffsetDp = with(density) { (targetSeg?.first ?: 0f).toDp() }
    val targetWidthDp = with(density) { (targetSeg?.second ?: 0f).toDp() }
    val animatedOffset by animateDpAsState(
        targetValue = targetOffsetDp,
        animationSpec = tween(durationMillis = 280, easing = FastOutSlowInEasing),
        label = "tab_indicator_offset"
    )
    val animatedWidth by animateDpAsState(
        targetValue = targetWidthDp,
        animationSpec = tween(durationMillis = 280, easing = FastOutSlowInEasing),
        label = "tab_indicator_width"
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        rowRootX = coordinates.positionInRoot().x
                    },
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                itemsIndexed(tabTitles) { index, title ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                val x = coordinates.positionInRoot().x - rowRootX
                                val w = coordinates.size.width.toFloat()
                                val next = x to w
                                if (tabSegments[index] != next) {
                                    tabSegments[index] = next
                                }
                            }
                            .clickable { selectedTab = index }
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (selectedTab == index) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            },
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .offset(x = animatedOffset)
                        .width(animatedWidth)
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.primary)
                )
            }

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.35f)
            )
        }

        Crossfade(
            targetState = selectedTab,
            animationSpec = tween(durationMillis = 220, easing = FastOutSlowInEasing),
            label = "company_tab_content"
        ) { tabIndex ->
            val content = tabContents.getOrNull(tabIndex).orEmpty()
            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
