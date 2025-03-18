package com.diplom.core.providers

fun interface ActivityContextProvider {
    fun getActivityContext(): Any
}

fun interface LifecycleProvider {
    fun getLifecycleOwner(): Any
}

fun interface CameraPreviewViewProvider{
    fun getCameraPreviewView(): Any
}

fun interface ImageCaptureProvider{
    fun getImageCapture(): Any
}

fun interface BitmapProvider{
    fun getBitmap(): Any
}