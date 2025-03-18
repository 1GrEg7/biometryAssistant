package com.diplom.domain.camera

import com.diplom.core.providers.ActivityContextProvider
import com.diplom.core.providers.CameraPreviewViewProvider
import com.diplom.core.providers.ImageCaptureProvider
import com.diplom.core.providers.LifecycleProvider

class SetUpCameraUseCase(
    private val cameraRepo: CameraRepo,
    private val activityContextProvider: ActivityContextProvider,
    private val lifecycleProvider: LifecycleProvider,
    private val previewViewProvider: CameraPreviewViewProvider,
    private val onImageCaptureReady: (ImageCaptureProvider) -> Unit,
) {

    fun invoke(){
        cameraRepo.setUpCamera(
            activityContextProvider,
            lifecycleProvider,
            previewViewProvider,
            onImageCaptureReady,
        )
    }
}