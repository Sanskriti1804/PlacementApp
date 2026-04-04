package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.data.AptitudeTestRepository
import com.example.placementprojectmp.data.AptitudeTestSessionHolder
import com.example.placementprojectmp.ui.components.QuestionReviewCard
import com.example.placementprojectmp.ui.components.ResultCard

/**
 * Aptitude test result: score summary card and question review cards.
 */
@Composable
fun AptitudeTestResultScreen(
    modifier: Modifier = Modifier,
    testId: String?
) {
    val tid = testId ?: AptitudeTestSessionHolder.testId ?: return
    val test = remember(tid) { AptitudeTestRepository.getTest(tid) }
    val questions = remember(tid) { AptitudeTestRepository.getQuestions(tid) }
    val selectedAnswers = remember(tid) { AptitudeTestSessionHolder.selectedAnswers }
    if (test == null || questions.isEmpty()) return

    var correct = 0
    questions.forEachIndexed { index, q ->
        if (selectedAnswers[index] == q.correctAnswerIndex) correct++
    }
    val wrong = selectedAnswers.size - correct
    val score = correct
    val total = questions.size

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            AnimatedVisibility(visible = true, enter = fadeIn()) {
                ResultCard(
                    score = score,
                    correct = correct,
                    wrong = wrong,
                    total = total
                )
            }
        }
        item {
            Text(
                text = "Question Review",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
            )
        }
        itemsIndexed(questions) { index, q ->
            val selectedIndex = selectedAnswers[index]
            val yourAnswer = if (selectedIndex != null) q.options.getOrNull(selectedIndex) ?: "—" else "—"
            val correctAnswer = q.options.getOrNull(q.correctAnswerIndex) ?: ""
            QuestionReviewCard(
                questionNumber = index + 1,
                questionText = q.question,
                yourAnswer = yourAnswer,
                correctAnswer = correctAnswer,
                explanation = q.explanation,
                isCorrect = selectedIndex == q.correctAnswerIndex
            )
        }
    }
}
