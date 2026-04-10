package com.example.placementprojectmp.ui.screens.system.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.screens.staff.components.InfoRow
import com.example.placementprojectmp.ui.screens.staff.components.ProfileWithStatusRing
import com.example.placementprojectmp.ui.screens.staff.components.SectionHeader
import com.example.placementprojectmp.ui.theme.colormap.ApplicationStatus

@Composable
fun SystemProfileScreen(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { SectionHeader("System Profile") }
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    ProfileWithStatusRing(
                        initials = "SA",
                        status = ApplicationStatus.APPLIED
                    )
                    InfoRow("Name", "System Administrator")
                    InfoRow("Role", "Admin")
                    InfoRow("Contact", "sysadmin@placementapp.com")
                }
            }
        }
        item {
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Edit Profile")
            }
        }
        item {
            OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Logout")
            }
        }
    }
}
