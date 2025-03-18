package com.diplom.domain.MLmodel

import com.diplom.core.providers.ActivityContextProvider
import com.diplom.core.providers.BitmapProvider
import com.diplom.core.providers.ImageCaptureProvider

class AnalyzePhotoUseCase(
    private val mlRepo: MLRepo,
    private val bitmapProvider: BitmapProvider,
    private val activityContextProvider: ActivityContextProvider,
){

    suspend fun invoke():List<Float?> =
        mlRepo.analyzePhoto(
            bitmapProvider,
            activityContextProvider
        )
}