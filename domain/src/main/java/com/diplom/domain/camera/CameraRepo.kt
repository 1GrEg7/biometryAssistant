package com.diplom.domain.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Camera
import com.diplom.core.permissionsInfo.PermissionsProvider
import com.diplom.core.providers.ActivityContextProvider
import com.diplom.core.providers.CameraPreviewViewProvider
import com.diplom.core.providers.ImageCaptureProvider
import com.diplom.core.providers.LifecycleProvider

interface CameraRepo {

     suspend fun takePhoto(
         activityContextProvider: ActivityContextProvider,
         lifecycleProvider: LifecycleProvider,
         imageCaptureProvider:ImageCaptureProvider
    ):Bitmap

     fun setUpCamera(
         activityContextProvider: ActivityContextProvider,
         lifecycleProvider: LifecycleProvider,
         previewViewProvider: CameraPreviewViewProvider,
         onImageCaptureReady: (ImageCaptureProvider) -> Unit,
     )

//    fun changeCamera(
//        lifecycleProvider: LifecycleProvider,
//        previewViewProvider: CameraPreviewViewProvider,
//        onImageCaptureReady: (ImageCaptureProvider) -> Unit
//    )

}