package com.diplom.data.cameraData

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.diplom.core.permissionsInfo.PermissionsProvider
import com.diplom.core.providers.ActivityContextProvider
import com.diplom.core.providers.CameraPreviewViewProvider
import com.diplom.domain.camera.CameraRepo
import com.diplom.core.providers.ImageCaptureProvider
import com.diplom.core.providers.LifecycleProvider
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


object CameraRepoImpl: CameraRepo {



    override suspend fun takePhoto(
        activityContextProvider: ActivityContextProvider,
        lifecycleProvider: LifecycleProvider,
        imageCaptureProvider: ImageCaptureProvider
    ):Bitmap = suspendCancellableCoroutine{ cont ->
        val context = activityContextProvider.getActivityContext() as Context
        val imageCapture = imageCaptureProvider.getImageCapture() as ImageCapture
        var resultBitmap: Bitmap
        imageCapture.takePicture(
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    try {
                        // Преобразуем ImageProxy в Bitmap
                        resultBitmap = rotateBitmap(image.toBitmap(),270f)
                        // Освобождаем ресурсы
                        image.close()
                        // Возобновляем выполнение с полученным изображением
                        cont.resume(resultBitmap)
                    }catch (e:Exception){
                        cont.resumeWithException(e)
                    }


                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("ImageCapture", "Ошибка при захвате изображения: ${exception.message}", exception)
                    cont.resumeWithException(exception)
                }
            }
        )
    }

    private fun rotateBitmap(bitmap: Bitmap, rotationDegrees: Float): Bitmap {
        val matrix = Matrix().apply { postRotate(rotationDegrees) }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun ImageProxy.toBitmap(): Bitmap {
        val buffer = this.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)

        // Конвертируем байты в Bitmap
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, null) ?: throw IllegalArgumentException("Cannot convert ImageProxy to Bitmap")
    }


    override fun setUpCamera(
        activityContextProvider: ActivityContextProvider,
        lifecycleProvider: LifecycleProvider,
        previewViewProvider: CameraPreviewViewProvider,
        onImageCaptureReady: (ImageCaptureProvider) -> Unit,

    ) {
        val lifecycleOwner = lifecycleProvider.getLifecycleOwner() as LifecycleOwner
        val ctx = activityContextProvider.getActivityContext() as Context
        val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = androidx.camera.core.Preview.Builder().build().also {
                it.setSurfaceProvider(
                    (previewViewProvider.getCameraPreviewView() as PreviewView).surfaceProvider
                )
            }
            val imageCapture = ImageCapture.Builder()
                .build()

            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA


            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
                val imageCaptureProvider = ImageCaptureProvider{ imageCapture }
                onImageCaptureReady(imageCaptureProvider)
            } catch (exc: Exception) {
                Log.e("CameraX", "Не удалось привязать CameraX к жизненному циклу", exc)
            }


        }, ContextCompat.getMainExecutor(ctx))


    }


}