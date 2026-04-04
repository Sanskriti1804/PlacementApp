package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.data.AptitudeTestRepository
import com.example.placementprojectmp.data.AptitudeTestSessionHolder
import com.example.placementprojectmp.ui.components.OptionItem
import com.example.placementprojectmp.ui.components.QuestionCard
import com.example.placementprojectmp.ui.components.QuestionIndicatorRow
import com.example.placementprojectmp.ui.components.TimerRing
import kotlinx.coroutines.delay

/**
 * Aptitude test player: timer ring, question indicators, question card, options, prev/next, submit.
 */
@Composable
fun AptitudeTestPlayerScreen(
    modifier: Modifier = Modifier,
    testId: String,
    onSubmit: () -> Unit = {}
) {
    val test = remember(testId) { AptitudeTestRepository.getTest(testId) }
    val questions = remember(testId) { AptitudeTestRepository.getQuestions(testId) }
    if (test == null || questions.isEmpty()) return

    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedAnswers by remember { mutableStateOf(mapOf<Int, Int>()) }
    var remainingSeconds by remember { mutableStateOf(test.timeMinutes * 60L) }

    LaunchedEffect(testId, remainingSeconds) {
        if (remainingSeconds <= 0) {
            AptitudeTestSessionHolder.testId = testId
            AptitudeTestSessionHolder.selectedAnswers = selectedAnswers
            onSubmit()
            return@LaunchedEffect
        }
        delay(1000)
        remainingSeconds = (remainingSeconds - 1).coerceAtLeast(0)
    }

    val currentQuestion = questions.getOrNull(currentIndex) ?: return
    val labels = listOf("A", "B", "C", "D")
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = test.title.uppercase(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            TimerRing(
                totalSeconds = test.timeMinutes * 60L,
                remainingSeconds = remainingSeconds,
                label = ""
            )
        }

        QuestionIndicatorRow(
            modifier = Modifier.padding(vertical = 16.dp),
            totalQuestions = questions.size,
            currentIndex = currentIndex,
            answeredIndices = selectedAnswers.keys.toSet(),
            onQuestionClick = { currentIndex = it }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            QuestionCard(
                questionNumber = currentIndex + 1,
                questionText = currentQuestion.question
            )

            Column(
                modifier = Modifier.padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                currentQuestion.options.forEachIndexed { optIndex, optText ->
                    OptionItem(
                        label = labels[optIndex],
                        text = optText,
                        isSelected = selectedAnswers[currentIndex] == optIndex,
                        onClick = { selectedAnswers = selectedAnswers + (currentIndex to optIndex) }
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { if (currentIndex > 0) currentIndex-- },
                modifier = Modifier.weight(1f),
                enabled = currentIndex > 0
            ) {
                Text("Previous")
            }
            Button(
                onClick = {
                    if (currentIndex < questions.size - 1) currentIndex++
                },
                modifier = Modifier.weight(1f),
                enabled = currentIndex < questions.size - 1
            ) {
                Text("Next")
            }
        }

        Button(
            onClick = {
                AptitudeTestSessionHolder.testId = testId
                AptitudeTestSessionHolder.selectedAnswers = selectedAnswers
                onSubmit()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentPadding = PaddingValues(vertical = 14.dp)
        ) {
            Text("SUBMIT TEST", fontWeight = FontWeight.Bold)
        }
    }
}
