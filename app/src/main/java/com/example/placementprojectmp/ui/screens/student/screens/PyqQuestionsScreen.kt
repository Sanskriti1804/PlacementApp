package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.components.NeonGlassToastHost

private data class PyqQuestionItem(
    val year: String,
    val question: String,
    val tags: List<String>
)

private val dummyPyqQuestions: List<PyqQuestionItem> = listOf(
    PyqQuestionItem("2023", "What is the difference between HashMap and Hashtable in Java? Explain thread safety and when to use each.", listOf("Java", "Infosys", "Backend")),
    PyqQuestionItem("2023", "Explain the difference between HashMap and ConcurrentHashMap in Java. Discuss thread safety and performance considerations.", listOf("Java", "DSA")),
    PyqQuestionItem("2022", "Describe the Android activity lifecycle. What methods are called when the user switches to another app and returns?", listOf("Android", "Google")),
    PyqQuestionItem("2022", "Implement a function to detect a cycle in a linked list. What is the time and space complexity?", listOf("Data Structures", "Microsoft", "SDE")),
    PyqQuestionItem("2023", "Explain database normalization up to 3NF with examples. When would you denormalize?", listOf("DBMS", "Amazon", "Backend")),
    PyqQuestionItem("2022", "Design a URL shortening service like bit.ly. Discuss scalability and storage.", listOf("System Design", "Google")),
    PyqQuestionItem("2023", "Explain the four pillars of OOP with real-world examples. How does Java implement encapsulation?", listOf("Java", "OOP", "TCS")),
    PyqQuestionItem("2022", "What are the differences between REST and GraphQL? When would you choose one over the other?", listOf("Backend", "Adobe")),
    PyqQuestionItem("2023", "How does the JVM handle garbage collection? Explain different GC algorithms.", listOf("Java", "Android Developer")),
    PyqQuestionItem("2022", "Implement a thread-safe singleton in Java. Discuss double-checked locking.", listOf("Java", "Infosys")),
    PyqQuestionItem("2023", "Explain B-tree and B+ tree. Why are B+ trees preferred for database indices?", listOf("DBMS", "Data Structures")),
    PyqQuestionItem("2022", "Design a rate limiter for an API. Consider distributed systems.", listOf("System Design", "Microsoft")),
    PyqQuestionItem("2023", "What is the difference between processes and threads? How does the OS schedule them?", listOf("Operating Systems", "Amazon")),
    PyqQuestionItem("2022", "Explain dependency injection. How is it implemented in Android?", listOf("Android", "Java")),
    PyqQuestionItem("2023", "Write a function to find the longest palindromic substring. Optimize for time complexity.", listOf("DSA", "Google")),
    PyqQuestionItem("2022", "What are ACID properties? How does a database ensure consistency?", listOf("DBMS", "Backend")),
    PyqQuestionItem("2023", "Describe the Model-View-ViewModel (MVVM) pattern. How does it differ from MVP?", listOf("Android", "Architecture")),
    PyqQuestionItem("2022", "Design a chat application supporting one-to-one and group chats. Discuss real-time sync.", listOf("System Design", "Frontend")),
    PyqQuestionItem("2023", "Explain virtual memory and paging. What is a TLB?", listOf("Operating Systems", "SDE")),
    PyqQuestionItem("2022", "How would you implement a LRU cache? What data structures would you use?", listOf("Data Structures", "Amazon"))
)

/**
 * PYQ Question screen: header (company PYQ), scrollable question cards, bookmark, tags, floating AI button, first-time hint snackbar.
 */
@Composable
fun PyqQuestionsScreen(
    modifier: Modifier = Modifier,
    companyName: String,
    onAiHelpClick: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var showHint by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        if (showHint) {
            snackbarHostState.showSnackbar(
                message = "Ask AI to explain or solve this question.",
                duration = SnackbarDuration.Short
            )
            showHint = false
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "${companyName} PYQ",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                )
            }
            items(dummyPyqQuestions) { item ->
                PyqQuestionCard(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    year = item.year,
                    question = item.question,
                    tags = item.tags
                )
            }
        }

        NeonGlassToastHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
        )

        FloatingActionButton(
            onClick = onAiHelpClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .size(56.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(
                imageVector = Icons.Default.SmartToy,
                contentDescription = "AI Help"
            )
        }
    }
}

@Composable
private fun PyqQuestionCard(
    modifier: Modifier = Modifier,
    year: String,
    question: String,
    tags: List<String>
) {
    var bookmarked by remember(question) { mutableStateOf(false) }
    val bookmarkColor by animateColorAsState(
        targetValue = if (bookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = tween(200),
        label = "bookmark"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = year,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = question,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 6.dp)
            )
            Row(
                modifier = Modifier.padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                tags.take(4).forEach { tag ->
                    Text(
                        text = tag,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
        IconButton(
            onClick = { bookmarked = !bookmarked },
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = if (bookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                contentDescription = if (bookmarked) "Remove bookmark" else "Bookmark",
                tint = bookmarkColor
            )
        }
    }
}
