package com.example.placementprojectmp.ui.screens.shared.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.placementprojectmp.ui.screens.shared.component.AppLogo

enum class IntroductionMode {
    TALENT,
    OPPORTUNITY
}

@Composable
fun AppIntroductionScreen(
    modifier: Modifier = Modifier,
    mode: IntroductionMode = IntroductionMode.TALENT
) {
    val titlePrimary = if (mode == IntroductionMode.TALENT) "TALENT" else "OPPORTUNITY"
    val subtitle = if (mode == IntroductionMode.TALENT) "for students" else "for recruiter / staff"
    val bottomRightText = if (mode == IntroductionMode.TALENT) {
        "All your placement opportunities in one place - Job Tracking, Application Management, Prep Hub"
    } else {
        "Manage all opportunities in one place - Drive Management, Student Data, Smart Shortlisting"
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(36.dp))
            AppLogo(size = 72.dp)
            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = titlePrimary,
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = " for",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Thin
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(0.7f),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.55f),
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = subtitle,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Light),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = bottomRightText,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 18.dp)
            )
        }
    }
}
