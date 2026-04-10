package com.example.placementprojectmp.ui.screens.shared.cards

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.viewmodel.DriveUiModel
import com.example.placementprojectmp.viewmodel.Status
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val driveDummyBranches = listOf("CSE", "IT", "ECE")

private const val driveRegistrationCloseTimeDummy = "6:00 PM"

/** Same cyan family as NeonBlue, slightly softer for the Register button. */
private val driveRegisterButtonContainer = Color(0xFF00A3C4)

@Composable
fun DriveCard(
    drive: DriveUiModel,
    onRegisterClick: () -> Unit,
    onClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var registerScaleTarget by remember { mutableFloatStateOf(1f) }
    val registerScale by animateFloatAsState(
        targetValue = registerScaleTarget,
        animationSpec = tween(durationMillis = 90),
        label = "drive_register_scale"
    )
    val closeDateFormatted = remember(drive.lastDateToRegister) {
        drive.lastDateToRegister.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(driveCardStatusColor(drive.status))
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LogoImage(logoResId = drive.companyLogoResId, containerSize = 46.dp)
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = drive.companyName,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = drive.driveName,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        driveDummyBranches.forEach { branch ->
                            DriveBranchCapsule(label = branch)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(22.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = "Registration closes on",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$closeDateFormatted · $driveRegistrationCloseTimeDummy",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Button(
                    onClick = {
                        scope.launch {
                            registerScaleTarget = 1.05f
                            delay(95)
                            registerScaleTarget = 1f
                            delay(95)
                            onRegisterClick()
                        }
                    },
                    modifier = Modifier.graphicsLayer {
                        scaleX = registerScale
                        scaleY = registerScale
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = driveRegisterButtonContainer,
                        contentColor = Color.White
                    ),
                    contentPadding = ButtonDefaults.ContentPadding
                ) {
                    Text(text = "Register")
                }
            }
        }
    }
}

private fun driveCardStatusColor(status: Status): Color = when (status) {
    Status.OPEN -> Color(0xFF2E7D32)
    Status.UPCOMING -> Color(0xFFFFC107)
    Status.CLOSED -> Color(0xFF1976D2)
}

@Composable
private fun DriveBranchCapsule(label: String) {
    Surface(
        shape = RoundedCornerShape(999.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.28f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.45f))
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
