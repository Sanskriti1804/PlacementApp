package com.example.placementprojectmp.ui.screens.startup.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.placementprojectmp.R
import com.example.placementprojectmp.ui.screens.shared.component.AppLogo
import com.example.placementprojectmp.ui.theme.NeonBlue
import com.example.placementprojectmp.ui.theme.NeonBlueDim

private data class RoleOption(
    val label: String,
    val iconResId: Int,
    val comingSoon: Boolean = false
)

private val ROLES = listOf(
    RoleOption("STUDENT", R.drawable.user_student),
    RoleOption("ADMIN", R.drawable.user_admin),
    RoleOption("MANAGEMENT", R.drawable.user_management),
    RoleOption("RECRUITER", R.drawable.user_management, comingSoon = true)
)

@Composable
fun RoleSelectionScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: (String) -> Unit = {},
    onSkipLogin: (String) -> Unit = {}
) {
    var selectedRole by remember { mutableStateOf<String?>(null) }
    var skipLoginEnabled by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var pendingRoleNavigation by remember { mutableStateOf<(() -> Unit)?>(null) }
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        pendingRoleNavigation?.invoke()
        pendingRoleNavigation = null
    }

    fun proceedAfterNotificationPermission(block: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val perm = Manifest.permission.POST_NOTIFICATIONS
            when {
                ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED ->
                    block()
                else -> {
                    pendingRoleNavigation = block
                    notificationPermissionLauncher.launch(perm)
                }
            }
        } else {
            block()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(56.dp))
        AppLogo(size = 72.dp)
        Spacer(modifier = Modifier.height(30.dp))
        Spacer(modifier = Modifier.height(38.dp))
        Text(
            text = "Choose Your Role",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(32.dp))
        ROLES.chunked(2).forEach { rowRoles ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowRoles.forEach { role ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                selectedRole = role.label.lowercase()
                                val authRole = if (role.label.equals("STUDENT", ignoreCase = true)) {
                                    "STUDENT"
                                } else {
                                    "STAFF"
                                }

                                proceedAfterNotificationPermission {
                                    if (skipLoginEnabled) {
                                        onSkipLogin(authRole)
                                    } else {
                                        onNavigateToLogin(authRole)
                                    }
                                }
                            }
                            .border(
                                width = 1.dp,
                                color = if (selectedRole == role.label.lowercase())
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .height(172.dp)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(58.dp)
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    RoundedCornerShape(12.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(role.iconResId),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = role.label,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        if (role.comingSoon) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "COMING SOON",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                if (rowRoles.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = {
               skipLoginEnabled = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (skipLoginEnabled) MaterialTheme.colorScheme.primary else NeonBlue,
                contentColor = Color.White,
                disabledContainerColor = NeonBlueDim,
                disabledContentColor = Color.White.copy(alpha = 0.75f)
            )
        ) {
            Text(if (skipLoginEnabled) "Login Skipped. Select Role!" else "Enter without Login")
        }
    }
}
