package com.diplom.domain.MLmodel

import com.diplom.core.providers.ActivityContextProvider
import com.diplom.core.providers.BitmapProvider

interface MLRepo {

    suspend fun analyzePhoto(
        bitmapProvider: BitmapProvider,
        activityContextProvider: ActivityContextProvider,
    ):List<Float?>

}