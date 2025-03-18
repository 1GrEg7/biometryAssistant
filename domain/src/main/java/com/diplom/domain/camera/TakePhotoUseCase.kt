package com.diplom.domain.camera

import android.graphics.Bitmap
import com.diplom.core.permissionsInfo.PermissionsProvider
import com.diplom.core.providers.ActivityContextProvider
import com.diplom.core.providers.ImageCaptureProvider
import com.diplom.core.providers.LifecycleProvider

class TakePhotoUseCase(
    private val cameraRepo: CameraRepo,
    private val activityContextProvider: ActivityContextProvider,
    private val lifecycleProvider: LifecycleProvider,
    private val imageCaptureProvider: ImageCaptureProvider
) {

    suspend fun invoke(): Bitmap {
        return cameraRepo.takePhoto(
            activityContextProvider,
            lifecycleProvider,
            imageCaptureProvider
        )
    }

}