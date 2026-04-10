package com.example.placementprojectmp.ui.screens.system.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tune
import com.example.placementprojectmp.ui.components.FeatureCard
import com.example.placementprojectmp.ui.screens.staff.components.StatCard
import com.example.placementprojectmp.ui.screens.staff.components.SectionHeader

@Composable
fun SystemSettingsScreen(
    modifier: Modifier = Modifier
) {
    var emailNotifications by remember { mutableStateOf(true) }
    var pushNotifications by remember { mutableStateOf(true) }
    var deadlinesEnabled by remember { mutableStateOf(true) }
    var driveAlertsEnabled by remember { mutableStateOf(true) }
    var appRemindersEnabled by remember { mutableStateOf(false) }
    var paymentAutoRenew by remember { mutableStateOf(true) }
    var featureStudentModule by remember { mutableStateOf(true) }
    var featureStaffModule by remember { mutableStateOf(true) }
    var featureSystemModule by remember { mutableStateOf(true) }

    var adminView by remember { mutableStateOf(true) }
    var adminEdit by remember { mutableStateOf(true) }
    var adminManage by remember { mutableStateOf(true) }
    var staffView by remember { mutableStateOf(true) }
    var staffEdit by remember { mutableStateOf(false) }
    var staffManage by remember { mutableStateOf(false) }
    var teacherView by remember { mutableStateOf(true) }
    var teacherEdit by remember { mutableStateOf(false) }
    var teacherManage by remember { mutableStateOf(false) }
    var recruiterView by remember { mutableStateOf(true) }
    var recruiterEdit by remember { mutableStateOf(false) }
    var recruiterManage by remember { mutableStateOf(false) }
    var hrView by remember { mutableStateOf(true) }
    var hrEdit by remember { mutableStateOf(false) }
    var hrManage by remember { mutableStateOf(false) }

    var emailValue by remember { mutableStateOf("admin@placementapp.com") }
    var passwordResetEmail by remember { mutableStateOf("") }

    val updateItems = listOf(
        "Drive X results announced",
        "New company added: Quantum Edge",
        "Deadline for Nexora internship updated",
        "Role update applied to 14 users"
    )
    val quickActions = listOf(
        "Add Role" to Icons.Default.AdminPanelSettings,
        "Update Config" to Icons.Default.Tune,
        "Send Notification" to Icons.Default.Send
    )

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 24.dp),
            contentPadding = PaddingValues(bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    SectionHeader("System Settings & Insights")
                    Text(
                        text = "Admin controls, permissions, analytics, and updates in one place",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    SectionHeader("Role-Based Access & Permissions")
                    PermissionRoleCard("Admin", adminView, adminEdit, adminManage, { adminView = it }, { adminEdit = it }, { adminManage = it })
                    PermissionRoleCard("Staff", staffView, staffEdit, staffManage, { staffView = it }, { staffEdit = it }, { staffManage = it })
                    PermissionRoleCard("Teacher", teacherView, teacherEdit, teacherManage, { teacherView = it }, { teacherEdit = it }, { teacherManage = it })
                    PermissionRoleCard("Recruiter", recruiterView, recruiterEdit, recruiterManage, { recruiterView = it }, { recruiterEdit = it }, { recruiterManage = it })
                    PermissionRoleCard("HR", hrView, hrEdit, hrManage, { hrView = it }, { hrEdit = it }, { hrManage = it })
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    SectionHeader("Placement Statistics")
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Column(modifier = Modifier.weight(1f)) { StatCard("Placed (Batch 2026)", "412", Icons.Default.Groups) }
                        Column(modifier = Modifier.weight(1f)) { StatCard("Applied Students", "1,184", Icons.Default.Analytics) }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Column(modifier = Modifier.weight(1f)) { StatCard("Selected Ratio", "38%", Icons.Default.AdminPanelSettings) }
                        Column(modifier = Modifier.weight(1f)) { StatCard("Internship vs Full-time", "54:46", Icons.Default.Business) }
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    SectionHeader("Drive Efficiency & Analytics")
                    AnalyticsRow("Nexora Systems Drive", "Applicants 320", "Shortlisted 96", "Selected 28", "Conversion 8.7%")
                    AnalyticsRow("Veltrix Labs Drive", "Applicants 248", "Shortlisted 72", "Selected 21", "Conversion 8.4%")
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    SectionHeader("Student Application Insights")
                    InsightsCard("Internship Applications", "CSE 210 • ECE 128 • IT 96")
                    InsightsCard("Full-time Applications", "CSE 182 • ECE 104 • IT 74")
                    InsightsCard("Participation (Department-wise)", "CSE 84% • ECE 71% • IT 77%")
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SectionHeader("Notifications & Alerts")
                    SettingsToggleItem("Email Notifications", emailNotifications) { emailNotifications = it }
                    SettingsToggleItem("Push Notifications", pushNotifications) { pushNotifications = it }
                    SettingsToggleItem("Deadlines", deadlinesEnabled) { deadlinesEnabled = it }
                    SettingsToggleItem("Drive Alerts", driveAlertsEnabled) { driveAlertsEnabled = it }
                    SettingsToggleItem("Application Reminders", appRemindersEnabled) { appRemindersEnabled = it }
                    InsightsCard("Upcoming Deadline", "Nexora Systems Internship registration closes in 2 days")
                    InsightsCard("Important Alert", "1 drive has pending result publication approval")
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SectionHeader("Email & Credential Management")
                    SimpleInputCard("Admin Email", emailValue) { emailValue = it }
                    SimpleInputCard("Reset Password (user email)", passwordResetEmail) { passwordResetEmail = it }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = {}, modifier = Modifier.weight(1f)) {
                            Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Update Email")
                        }
                        OutlinedButton(onClick = {}, modifier = Modifier.weight(1f)) {
                            Icon(Icons.Default.LockReset, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Reset Password")
                        }
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SectionHeader("Role Management Controls")
                    InsightsCard("Global Role Update", "24 users eligible for system-level role refresh")
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = {}, modifier = Modifier.weight(1f)) { Text("Assign Roles") }
                        OutlinedButton(onClick = {}, modifier = Modifier.weight(1f)) { Text("Bulk Update") }
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SectionHeader("Payment / Subscription")
                    InsightsCard("Subscription Status", "Enterprise Plan • Renews in 19 days")
                    SettingsToggleItem("Auto Renew", paymentAutoRenew) { paymentAutoRenew = it }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = {}, modifier = Modifier.weight(1f)) {
                            Icon(Icons.Default.AttachMoney, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Payment Methods")
                        }
                        OutlinedButton(onClick = {}, modifier = Modifier.weight(1f)) { Text("Manage Plan") }
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SectionHeader("Global App Configuration")
                    SettingsToggleItem("Student Module", featureStudentModule) { featureStudentModule = it }
                    SettingsToggleItem("Staff Module", featureStaffModule) { featureStaffModule = it }
                    SettingsToggleItem("System Module", featureSystemModule) { featureSystemModule = it }
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SectionHeader("Activity / Updates")
                    updateItems.forEach {
                        InsightsCard("Update", it)
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    SectionHeader("Quick Actions")
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(quickActions.size) { index ->
                            val (label, icon) = quickActions[index]
                            FeatureCard(
                                modifier = Modifier.width(120.dp),
                                label = label,
                                imageVector = icon,
                                onClick = {}
                            )
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 24.dp),
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp, pressedElevation = 8.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Quick action")
        }
    }
}

@Composable
private fun SettingsToggleItem(
    title: String,
    enabled: Boolean,
    onChange: (Boolean) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Switch(checked = enabled, onCheckedChange = onChange)
        }
    }
}

@Composable
private fun PermissionRoleCard(
    role: String,
    canView: Boolean,
    canEdit: Boolean,
    canManage: Boolean,
    onViewChange: (Boolean) -> Unit,
    onEditChange: (Boolean) -> Unit,
    onManageChange: (Boolean) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = role,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            SettingsToggleItemInline("Can view analytics", canView, onViewChange)
            SettingsToggleItemInline("Can edit records", canEdit, onEditChange)
            SettingsToggleItemInline("Can manage access", canManage, onManageChange)
        }
    }
}

@Composable
private fun SettingsToggleItemInline(
    label: String,
    enabled: Boolean,
    onChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Switch(checked = enabled, onCheckedChange = onChange)
    }
}

@Composable
private fun AnalyticsRow(
    title: String,
    applicants: String,
    shortlisted: String,
    selected: String,
    conversion: String
) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(title, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
            Text(applicants, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(shortlisted, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(selected, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(conversion, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
private fun InsightsCard(
    title: String,
    value: String
) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(title, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
            Text(value, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun SimpleInputCard(
    title: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(title, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
    }
}
