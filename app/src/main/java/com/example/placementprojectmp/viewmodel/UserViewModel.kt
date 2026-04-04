package com.example.placementprojectmp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementprojectmp.data.model.User
import com.example.placementprojectmp.data.repo.UserRepo
import kotlinx.coroutines.launch

class UserViewModel (
    private val repository : UserRepo
) : ViewModel(){
    var students by mutableStateOf<List<User>>(emptyList())
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)

    fun fetchUsers(){
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                students = repository.getUsers()
            }
            catch (e : Exception){
                Log.e("API_ERROR", "Failed to fetch students", e)
                errorMessage = e.message ?: "Failed to fetch students"
            }
            finally {
                isLoading = false
            }
        }
    }
}