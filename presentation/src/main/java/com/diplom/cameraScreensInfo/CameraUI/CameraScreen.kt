package com.diplom.cameraScreensInfo.CameraUI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diplom.cameraScreensInfo.CameraViewModel
import com.diplom.cameraScreensInfo.RequestCameraPermission

@Composable
fun CameraScreen(
    cameraViewModel: CameraViewModel = viewModel(),
    toResultScreen: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        RequestCameraPermission {
            Camera(
                cameraViewModel = cameraViewModel,
                toResultScreen = toResultScreen

            )
        }
    }

}