package com.example.placementprojectmp.ui.screens.shared.screens

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R
import com.example.placementprojectmp.ui.components.AppLogo

private data class RoleOption(
    val label: String,
    val iconResId: Int
)

private val ROLES = listOf(
    RoleOption("Student", R.drawable.user_student),
    RoleOption("Admin", R.drawable.user_admin),
    RoleOption("Management", R.drawable.user_management)
)

@Composable
fun RoleSelectionScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: (String) -> Unit = {}
) {
    var selectedRole by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        AppLogo(size = 72.dp)
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Choose Your Role",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(32.dp))
        ROLES.forEach { role ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        selectedRole = role.label.lowercase()
                        val authRole = if (role.label.equals("Student", ignoreCase = true)) {
                            "STUDENT"
                        } else {
                            "STAFF"
                        }
                        onNavigateToLogin(authRole)
                    }
                    .border(
                        width = 1.dp,
                        color = if (selectedRole == role.label.lowercase())
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.foundation.Image(
                        painter = painterResource(role.iconResId),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Text(
                    text = role.label,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
