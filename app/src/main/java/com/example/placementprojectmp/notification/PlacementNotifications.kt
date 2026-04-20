package com.example.placementprojectmp.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.placementprojectmp.R

/**
 * Lightweight system notifications for three placement events only.
 * Falls back to [Toast] when notifications are disabled or POST_NOTIFICATIONS is denied (API 33+).
 */
object PlacementNotifications {

    private const val CHANNEL_ID = "placement_events_v1"
    private const val CHANNEL_NAME = "Placement updates"

    private const val ID_APPLICATION_SUBMITTED = 91001
    private const val ID_DRIVE_REGISTERED = 91002
    private const val ID_STATUS_UPDATED = 91003

    /** Matches [com.example.placementprojectmp.ui.theme.NeonBlue] — safe for [NotificationCompat.setColor]. */
    private const val NEON_BLUE_ARGB = 0xFF00D4FF.toInt()

    fun ensureChannel(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val mgr = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Application, drive registration, and status updates"
            enableLights(true)
            lightColor = NEON_BLUE_ARGB
        }
        mgr.createNotificationChannel(channel)
    }

    private fun mayPostToTray(context: Context): Boolean {
        if (!NotificationManagerCompat.from(context).areNotificationsEnabled()) return false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        }
        return true
    }

    private fun deliver(
        context: Context,
        notificationId: Int,
        title: String,
        text: String
    ) {
        val appContext = context.applicationContext
        ensureChannel(appContext)
        if (mayPostToTray(appContext)) {
            val notification = NotificationCompat.Builder(appContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(NotificationCompat.BigTextStyle().bigText(text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setColor(NEON_BLUE_ARGB)
                .setAutoCancel(true)
                .build()
            NotificationManagerCompat.from(appContext).notify(notificationId, notification)
        } else {
            Toast.makeText(appContext, "$title — $text", Toast.LENGTH_LONG).show()
        }
    }

    fun notifyApplicationSubmitted(context: Context, studentName: String, companyName: String) {
        val company = companyName.trim().ifBlank { "the company" }
        val student = studentName.trim().ifBlank { "Student" }
        deliver(
            context,
            ID_APPLICATION_SUBMITTED,
            title = "Application Submitted",
            text = "$student applied for $company"
        )
    }

    fun notifyDriveRegistration(context: Context, studentName: String, driveName: String) {
        val drive = driveName.trim().ifBlank { "the drive" }
        val student = studentName.trim().ifBlank { "Student" }
        deliver(
            context,
            ID_DRIVE_REGISTERED,
            title = "Drive Registration Successful",
            text = "$student registered for $drive"
        )
    }

    fun notifyApplicationStatusUpdated(
        context: Context,
        studentName: String,
        companyName: String,
        statusPhrase: String
    ) {
        val company = companyName.trim().ifBlank { "the company" }
        val student = studentName.trim().ifBlank { "Student" }
        val status = statusPhrase.trim().ifBlank { "updated" }
        deliver(
            context,
            ID_STATUS_UPDATED,
            title = "Application Status Updated",
            text = "$student has been $status for $company"
        )
    }
}
