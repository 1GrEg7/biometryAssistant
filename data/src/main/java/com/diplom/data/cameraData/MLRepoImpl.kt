package com.diplom.data.cameraData

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import com.diplom.core.providers.ActivityContextProvider
import com.diplom.core.providers.BitmapProvider
import com.diplom.domain.MLmodel.MLRepo
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object MLRepoImpl: MLRepo {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun analyzePhoto(
        bitmapProvider: BitmapProvider,
        activityContextProvider: ActivityContextProvider,
    ): List<Float?> = suspendCancellableCoroutine { continuation ->
        val bitmap = bitmapProvider.getBitmap() as Bitmap
        val image = InputImage.fromBitmap(bitmap, 0)
        val context = activityContextProvider.getActivityContext() as Context

        val options = FaceDetectorOptions.Builder()
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .setMinFaceSize(0.9f)
            .build()

        val detector = FaceDetection.getClient(options)

        val task = detector.process(image)
            .addOnSuccessListener { faces ->
                val listOfParametrs = mutableListOf<Float?>()
                if (faces.size > 0){
                    listOfParametrs.apply {
                        add(faces.size.toFloat())
                        add(faces[0].smilingProbability)
                        add(faces[0].leftEyeOpenProbability)
                        add(faces[0].rightEyeOpenProbability)
                    }
                }else{
                    listOfParametrs.apply {
                        add(0f)
                        add(0f)
                        add(0f)
                        add(0f)
                    }
                }
                // Если корутина всё ещё активна, резюмируем результат
                if (continuation.isActive)
                    continuation.resume(listOfParametrs)


            }
            .addOnFailureListener { exception ->
                if (continuation.isActive)
                    continuation.resumeWithException(exception)
            }
    }

}