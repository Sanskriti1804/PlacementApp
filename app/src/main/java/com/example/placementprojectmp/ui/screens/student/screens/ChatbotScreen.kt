package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.screens.shared.component.AppTopBar
import com.example.placementprojectmp.ui.components.ChatInputBar
import com.example.placementprojectmp.ui.components.ChatMessageBubble

private data class ChatMessage(
    val text: String,
    val isFromUser: Boolean
)

@Composable
fun ChatbotScreen(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    var inputText by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<ChatMessage>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AppTopBar(
            onMenuClick = onMenuClick,
            onNotificationClick = onNotificationClick
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (messages.isEmpty()) {
                Text(
                    text = "Ask anything related to preparation, academics, or placement guidance.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(32.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    items(messages.size) { index ->
                        val msg = messages[index]
                        ChatMessageBubble(
                            text = msg.text,
                            isFromUser = msg.isFromUser
                        )
                    }
                }
            }
        }
        ChatInputBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            value = inputText,
            onValueChange = { inputText = it },
            onSend = {
                val trimmed = inputText.trim()
                if (trimmed.isNotEmpty()) {
                    messages.add(ChatMessage(text = trimmed, isFromUser = true))
                    inputText = ""
                }
            }
        )
    }
}
