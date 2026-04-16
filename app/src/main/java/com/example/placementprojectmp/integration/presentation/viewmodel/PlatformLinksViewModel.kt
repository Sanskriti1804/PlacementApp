package com.example.placementprojectmp.integration.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.integration.data.dto.PlatformType
import com.example.placementprojectmp.integration.data.remote.ApiResult
import com.example.placementprojectmp.integration.data.repository.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PlatformLinksState(
    val urls: Map<PlatformType, String> = emptyMap(),
    val loading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

class PlatformLinksViewModel(
    private val repository: StudentRepository
) : ViewModel() {
    private val _ui = MutableStateFlow(PlatformLinksState())
    val ui: StateFlow<PlatformLinksState> = _ui.asStateFlow()

    fun update(type: PlatformType, url: String) {
        _ui.value = _ui.value.copy(urls = _ui.value.urls + (type to url), error = null)
    }

    fun submit(studentId: Long) {
        val required = listOf(PlatformType.GITHUB, PlatformType.LINKEDIN, PlatformType.RESUME)
        val missing = required.filter { _ui.value.urls[it].isNullOrBlank() }
        if (missing.isNotEmpty()) {
            _ui.value = _ui.value.copy(
                error = "Required links missing: ${missing.joinToString { it.name }}"
            )
            return
        }

        _ui.value = _ui.value.copy(loading = true, error = null, success = false)
        viewModelScope.launch {
            val urls = _ui.value.urls.filterValues { it.isNotBlank() }
            for ((type, url) in urls) {
                when (val result = repository.addPlatform(studentId, type, url.trim())) {
                    is ApiResult.Success -> Unit
                    is ApiResult.Error -> {
                        _ui.value = _ui.value.copy(loading = false, error = result.message)
                        return@launch
                    }
                }
            }
            _ui.value = _ui.value.copy(loading = false, success = true)
        }
    }
}

