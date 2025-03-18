package com.diplom.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diplom.core.permissionsInfo.PermissionsProvider
import com.diplom.core.providers.ActivityContextProvider
import com.diplom.domain.camera.CameraRepo
import com.diplom.core.providers.LifecycleProvider

class MainScreenViewModel(
    private val cameraRepoImpl: CameraRepo,
    private val activityContextProvider: ActivityContextProvider,
    private val lifecycleProvider: LifecycleProvider,
    private val permissionsProvider: PermissionsProvider
) : ViewModel(){



//    fun openCamera(){
//        val takePictureUseCase: TakePictureUseCase =
//            TakePictureUseCase(
//                cameraRepoImpl,
//                activityContextProvider,
//                lifecycleProvider,
//                permissionsProvider)
//
//            takePictureUseCase.invoke()
//
//    }

    class MainScreenViewModelFactory(
        private val cameraRepoImpl: CameraRepo,
        private val activityContextProvider: ActivityContextProvider,
        private val lifecycleProvider: LifecycleProvider,
        private val permissionsProvider: PermissionsProvider
    ):ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MainScreenViewModel(
                cameraRepoImpl,
                activityContextProvider,
                lifecycleProvider,
                permissionsProvider
            ) as T
        }
    }
}