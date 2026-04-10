package com.example.placementprojectmp.ui.screens.shared.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.placementprojectmp.auth.AuthRole
import com.example.placementprojectmp.ui.screens.shared.component.AppLogo
import com.example.placementprojectmp.ui.components.ForgotPasswordDialog
import com.example.placementprojectmp.ui.screens.shared.component.NeonGlassToastHost
import com.example.placementprojectmp.ui.theme.NeonBlue
import com.example.placementprojectmp.ui.theme.NeonBlueDim
import com.example.placementprojectmp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    selectedRole: String = "user",
    onNavigateToLoading: (AuthRole) -> Unit = {}
) {
    val authViewModel: AuthViewModel = koinViewModel()
    val roleFromRoute = remember(selectedRole) { AuthRole.fromInput(selectedRole) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val clipboardManager = LocalClipboardManager.current
    val adminEmail = "admin@oporgion.com"
    var showForgotDialog by remember { mutableStateOf(false) }

    LaunchedEffect(roleFromRoute) {
        authViewModel.setRole(roleFromRoute)
    }
    LaunchedEffect(authViewModel.errorMessage) {
        authViewModel.errorMessage?.let { message ->
            scope.launch {
                snackbarHostState.showSnackbar(message)
            }
        }
    }

    Box(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            AppLogo(size = 100.dp)
            Spacer(modifier = Modifier.height(24.dp))

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Login to Opportune",
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 34.sp),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = authViewModel.email,
                onValueChange = {
                    authViewModel.email = it
                },
                label = { Text("Email") },
                isError = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedBorderColor = NeonBlue,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    errorBorderColor = NeonBlue,
                    focusedLabelColor = NeonBlue,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    cursorColor = NeonBlue,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )

            OutlinedTextField(
                value = authViewModel.password,
                onValueChange = {
                    authViewModel.password = it
                },
                label = { Text("Password") },
                isError = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(16.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedBorderColor = NeonBlue,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    errorBorderColor = NeonBlue,
                    focusedLabelColor = NeonBlue,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    cursorColor = NeonBlue,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )

            TextButton(
                onClick = { showForgotDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp)
            ) {
                Text(
                    text = "Forgot your password?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = NeonBlue
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    authViewModel.login { role -> onNavigateToLoading(role) }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                enabled = !authViewModel.isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = NeonBlue,
                    contentColor = Color.White,
                    disabledContainerColor = NeonBlueDim,
                    disabledContentColor = Color.White.copy(alpha = 0.75f)
                )
            ) {
                Text(if (authViewModel.isLoading) "Logging in..." else "Login")
            }
        }
        NeonGlassToastHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }

    if (showForgotDialog) {
        ForgotPasswordDialog(
            adminEmail = adminEmail,
            clipboardManager = clipboardManager,
            onDismissRequest = { showForgotDialog = false }
        )
    }
}
