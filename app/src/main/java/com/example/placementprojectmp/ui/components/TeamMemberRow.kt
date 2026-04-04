package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * One team member row: "Member N" label, then 50/50 [ Member Name ] [ Role ].
 */
@Composable
fun TeamMemberRow(
    memberIndex: Int,
    memberName: String,
    onMemberNameChange: (String) -> Unit,
    role: String,
    onRoleChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Member $memberIndex",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FormField(
                label = "Member Name",
                value = memberName,
                onValueChange = onMemberNameChange,
                modifier = Modifier.weight(0.5f),
                required = false,
                placeholder = "Name"
            )
            FormField(
                label = "Role",
                value = role,
                onValueChange = onRoleChange,
                modifier = Modifier.weight(0.5f),
                required = false,
                placeholder = "e.g. Developer"
            )
        }
    }
}
