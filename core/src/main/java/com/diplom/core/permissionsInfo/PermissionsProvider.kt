package com.diplom.core.permissionsInfo

import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class PermissionsProvider(private val activity: ComponentActivity) {

    private val requestPermissionLauncher =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Разрешение предоставлено
                onPermissionGranted?.invoke()
            } else {
                // Разрешение не предоставлено
                onPermissionDenied?.invoke()
            }
        }

    private var onPermissionGranted: (() -> Unit)? = null
    private var onPermissionDenied: (() -> Unit)? = null

    fun checkAndRequestPermission(
        permission: String,
        onGranted: () -> Unit,
        onDenied: () -> Unit
    ) {
        // Сохраняем коллбеки
        onPermissionGranted = onGranted
        onPermissionDenied = onDenied

        if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
            // Если разрешение уже предоставлено
            onGranted()
        } else {
            // Если разрешение отсутствует, запрашиваем его
            requestPermissionLauncher.launch(permission)
        }
    }
}