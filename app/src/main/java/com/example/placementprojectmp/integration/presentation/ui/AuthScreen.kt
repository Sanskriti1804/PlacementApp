package com.example.placementprojectmp.integration.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.placementprojectmp.integration.data.dto.RoleType
import com.example.placementprojectmp.integration.presentation.viewmodel.AuthViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    onAuthSuccess: () -> Unit = {}
) {
    val vm: AuthViewModel = koinViewModel()
    val state by vm.uiState.collectAsStateWithLifecycle()
    var roleExpanded by remember { mutableStateOf(false) }

    if (state.authSuccess) onAuthSuccess()

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.email,
            onValueChange = vm::updateEmail,
            label = { Text("Email") },
            isError = state.emailError != null,
            supportingText = { state.emailError?.let { Text(it) } }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.password,
            onValueChange = vm::updatePassword,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = state.passwordError != null,
            supportingText = { state.passwordError?.let { Text(it) } }
        )

        OutlinedTextField(
            value = state.selectedRole.name,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Role") },
            isError = state.roleError != null,
            supportingText = { state.roleError?.let { Text(it) } }
        )
        Button(onClick = { roleExpanded = true }) { Text("Select Role") }
        DropdownMenu(expanded = roleExpanded, onDismissRequest = { roleExpanded = false }) {
            RoleType.entries.forEach { role ->
                DropdownMenuItem(
                    text = { Text(role.name) },
                    onClick = {
                        vm.updateRole(role)
                        roleExpanded = false
                    }
                )
            }
        }

        state.error?.let { Text(it) }

        Button(
            onClick = vm::submit,
            enabled = !state.loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (state.loading) "Please wait..." else "Continue")
        }
    }
}

