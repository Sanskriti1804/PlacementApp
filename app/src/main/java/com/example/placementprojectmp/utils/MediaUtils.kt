package com.example.placementprojectmp.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

class MediaPickerHelper(
    private val context: Context,
    private val permissionLauncher: androidx.activity.result.ActivityResultLauncher<String>,
    private val singlePickerLauncher: androidx.activity.result.ActivityResultLauncher<PickVisualMediaRequest>,
    private val multiplePickerLauncher: androidx.activity.result.ActivityResultLauncher<PickVisualMediaRequest>,
    private val updatePendingAction: (PendingPickerAction?) -> Unit
) {
    enum class PendingPickerAction { SINGLE, MULTIPLE }

    fun pickSingleImage() {
        if (hasPermission(context)) {
            launchSinglePicker()
        } else {
            updatePendingAction(PendingPickerAction.SINGLE)
            permissionLauncher.launch(getRequiredPermission())
        }
    }

    fun pickMultipleImages() {
        if (hasPermission(context)) {
            launchMultiplePicker()
        } else {
            updatePendingAction(PendingPickerAction.MULTIPLE)
            permissionLauncher.launch(getRequiredPermission())
        }
    }

    private fun launchSinglePicker() {
        singlePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun launchMultiplePicker() {
        multiplePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun getRequiredPermission(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    }

    private fun hasPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            getRequiredPermission()
        ) == PackageManager.PERMISSION_GRANTED
    }
}

@Composable
fun rememberMediaPicker(
    onSingleImageSelected: (Uri?) -> Unit,
    onMultipleImagesSelected: (List<Uri>) -> Unit
): MediaPickerHelper {
    val context = LocalContext.current
    var pendingAction by remember { mutableStateOf<MediaPickerHelper.PendingPickerAction?>(null) }

    val singlePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> onSingleImageSelected(uri) }
    )

    val multiplePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris -> onMultipleImagesSelected(uris) }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                when (pendingAction) {
                    MediaPickerHelper.PendingPickerAction.SINGLE -> {
                        singlePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                    MediaPickerHelper.PendingPickerAction.MULTIPLE -> {
                        multiplePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                    null -> {}
                }
            } else {
                Toast.makeText(context, "Permission Denied. Cannot access gallery.", Toast.LENGTH_SHORT).show()
            }
            pendingAction = null
        }
    )

    return remember(context, permissionLauncher, singlePickerLauncher, multiplePickerLauncher) {
        MediaPickerHelper(
            context = context,
            permissionLauncher = permissionLauncher,
            singlePickerLauncher = singlePickerLauncher,
            multiplePickerLauncher = multiplePickerLauncher,
            updatePendingAction = { action -> pendingAction = action }
        )
    }
}
