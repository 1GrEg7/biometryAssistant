package com.diplom.cameraScreensInfo

import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.diplom.core.MLInformation
import com.diplom.core.providers.ActivityContextProvider
import com.diplom.core.providers.CameraPreviewViewProvider
import com.diplom.core.providers.ImageCaptureProvider
import com.diplom.core.providers.LifecycleProvider
import com.diplom.domain.MLmodel.AnalyzePhotoUseCase
import com.diplom.domain.MLmodel.MLRepo
import com.diplom.domain.camera.CameraRepo
import com.diplom.domain.camera.SetUpCameraUseCase
import com.diplom.domain.camera.TakePhotoUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CameraViewModel(
    private val cameraRepoImpl: CameraRepo,
    private val mlRepoImpl: MLRepo
):ViewModel() {


    private val _frontCamera = mutableStateOf(true)
    val frontCamera = _frontCamera

    fun setUpCamera(
        activityContextProvider: ActivityContextProvider,
        lifecycleProvider: LifecycleProvider,
        previewViewProvider: CameraPreviewViewProvider,
        onImageCaptureReady: (ImageCaptureProvider) -> Unit,
    ){
        val setUpCameraUseCase = SetUpCameraUseCase(
            cameraRepoImpl,
            activityContextProvider,
            lifecycleProvider,
            previewViewProvider,
            onImageCaptureReady,
        )
        setUpCameraUseCase.invoke()
    }



//    fun changeCamera(
//        lifecycleProvider: LifecycleProvider,
//        previewViewProvider: CameraPreviewViewProvider,
//        onImageCaptureReady: (ImageCaptureProvider) -> Unit
//    ){
//        cameraRepoImpl.changeCamera(
//            lifecycleProvider,
//            previewViewProvider,
//            onImageCaptureReady
//        )
//    }

    fun analyzePhoto(
        activityContextProvider: ActivityContextProvider
    ){
        val analyzePhotoUseCase = AnalyzePhotoUseCase(
            mlRepoImpl,
            bitmapProvider = {_capturedImage.value},
            activityContextProvider = activityContextProvider
        )
        viewModelScope.launch {
            val list = analyzePhotoUseCase.invoke()
            MLInformation.countOfFaces = list[0]
            MLInformation.chanceOfSmiling = list[1]
            MLInformation.chanceOfClosedLeftEye = 1f-list[2]!!+0.1f
            MLInformation.chanceOfClosedRightEye = 1f-list[3]!!+0.1f
            MLInformation.isInfoProvided = true
        }
    }


    private val _capturedImage = mutableStateOf<Bitmap>(Bitmap.createBitmap(1,1,Bitmap.Config.ARGB_8888))
    val capturedImage = _capturedImage

    fun takePhoto(
        imageCapture:ImageCapture,
        activityContextProvider: ActivityContextProvider,
        lifecycleProvider: LifecycleProvider,
        toScreen:()->Unit
    ) {
        val takePhotoUseCase = TakePhotoUseCase(
            cameraRepoImpl,
            activityContextProvider,
            lifecycleProvider,
            { imageCapture }
        )
        viewModelScope.launch {
            _capturedImage.value = takePhotoUseCase.invoke()
            delay(500)
            analyzePhoto(activityContextProvider)
            val list = MLInformation.getMlParametrs()
            toScreen() // Передать картинку в ML и перейти на другой экран

        }
    }

    class CameraViewModelFactory(
        private val cameraRepoImpl: CameraRepo,
        private val mlRepoImpl: MLRepo
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return CameraViewModel(
                cameraRepoImpl,
                mlRepoImpl
            ) as T
        }
    }

}