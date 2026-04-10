package com.example.placementprojectmp.ui.screens.shared.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.placementprojectmp.ui.screens.shared.component.AppLogo
import kotlinx.coroutines.delay

@Composable
fun AboutAppScreen(
    modifier: Modifier = Modifier,
    onNavigateToRoleSelection: (() -> Unit)? = null
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        while (true) {
            delay(5000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Job",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 22.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "•",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                Text(
                    text = "Opportunity",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 22.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "•",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                Text(
                    text = "Internship",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 22.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = pagerState
            ) { page ->
                when (page) {
                    0 -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            AppLogo(size = 132.dp)
                            Text(
                                text = "OPPORTUNE",
                                style = MaterialTheme.typography.displayLarge.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 54.sp
                                ),
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = " Connect Talent to Opportunity",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Light,
                                    fontSize = 28.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    1, 2 -> {
                        val isTalent = page == 1
                        val heading = if (isTalent) "TALENT" else "OPPORTUNITY"
                        val summaryLead = if (isTalent) {
                            "All your placement opportunities in one place"
                        } else {
                            "Manage all opportunities in one place"
                        }
                        val summaryRest = if (isTalent) {
                            "- Job Tracking,\nApplication Management,\nPrep Hub"
                        } else {
                            "- Drive Management,\nStudent Data,\nSmart Shortlisting"
                        }

                        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                            val leftInset = maxWidth * 0.1f
                            val topInset = maxHeight * 0.1f
                            val bottomInset = maxHeight * 0.1f
                            Column(
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(start = leftInset, top = topInset)
                            ) {
                                AppLogo(size = 72.dp)
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(verticalAlignment = Alignment.Bottom) {
                                    Text(
                                        text = "FOR",
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Thin,
                                            fontSize = 26.sp
                                        ),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = " $heading",
                                        style = MaterialTheme.typography.displayMedium.copy(
                                            fontWeight = FontWeight.Black,
                                            fontSize = 36.sp
                                        ),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                HorizontalDivider(
                                    modifier = Modifier.fillMaxWidth(0.7f),
                                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.55f),
                                    thickness = 1.dp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(end = 8.dp, bottom = bottomInset),
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = summaryLead,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 20.sp
                                    ),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.End
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = summaryRest,
                                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 17.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.End
                                )
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) { index ->
                    val selected = index == pagerState.currentPage
                    val color by animateColorAsState(
                        targetValue = if (selected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        animationSpec = tween(200),
                        label = "dot"
                    )
                    Box(
                        modifier = Modifier
                            .size(if (selected) 10.dp else 6.dp)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            if (onNavigateToRoleSelection != null) {
                Button(
                    onClick = onNavigateToRoleSelection,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text("Get Started")
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
