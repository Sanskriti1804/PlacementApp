package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.data.AptitudeTestRepository
import com.example.placementprojectmp.ui.components.TestOverviewBox

/**
 * Aptitude test details: title, START TEST button, instructions card, overview, rules.
 */
@Composable
fun AptitudeTestDetailsScreen(
    modifier: Modifier = Modifier,
    testId: String,
    onStartTest: () -> Unit = {}
) {
    val test = remember(testId) { AptitudeTestRepository.getTest(testId) }
    if (test == null) return

    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(top = 28.dp, bottom = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            Text(
                text = test.title.uppercase(),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            InstructionsCard(modifier = Modifier.padding(top = 24.dp))

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 20.dp),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            )

            TestOverviewBox(
                modifier = Modifier.fillMaxWidth(),
                totalQuestions = test.totalQuestions,
                timeMinutes = test.timeMinutes,
                sections = test.sections
            )

            Text(
                text = "Test Rules",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
            )
            RulesList()
            Spacer(modifier = Modifier.height(16.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            StartTestButton(onClick = onStartTest)
        }
    }
}

@Composable
private fun StartTestButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.clip(RoundedCornerShape(12.dp)),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp)
    ) {
        Text(
            text = "START TEST",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun InstructionsCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
            .padding(16.dp)
    ) {
        Text(
            text = "Aptitude Test Instructions",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            text = "⭐ This test is designed for practice purposes.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Text(
            text = "⭐ The goal is to simulate a real-time aptitude test environment similar to company assessments.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun RulesList() {
    val rules = listOf(
        "Total test duration is 20 minutes",
        "The test cannot be paused once started",
        "Each question has only one correct answer",
        "Each question carries 1 mark",
        "The test automatically submits when the timer ends"
    )
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        rules.forEach { rule ->
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(vertical = 2.dp)
            ) {
                Text(
                    text = "•",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = rule,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
