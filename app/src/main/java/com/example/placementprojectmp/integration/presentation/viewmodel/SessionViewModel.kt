package com.example.placementprojectmp.integration.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.auth.TokenStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

data class SessionState(
    val checking: Boolean = true,
    val hasSession: Boolean = false
)

class SessionViewModel(
    private val tokenStore: TokenStore
) : ViewModel() {
    private val _state = MutableStateFlow(SessionState())
    val state: StateFlow<SessionState> = _state.asStateFlow()

    fun bootstrap() {
        viewModelScope.launch {
            val token = tokenStore.tokenFlow.firstOrNull()
            _state.value = SessionState(checking = false, hasSession = !token.isNullOrBlank())
        }
    }
}

