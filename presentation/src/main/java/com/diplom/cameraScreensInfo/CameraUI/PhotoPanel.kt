package com.diplom.cameraScreensInfo.CameraUI

import androidx.camera.core.ImageCapture
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PhotoPanel(
    modifier: Modifier = Modifier,
    onCapture: (ImageCapture?) -> Unit,
    imageCapture: ImageCapture?,
    onChangeCamera: () -> Unit
) {
    Row(
        modifier
            .background(Color.Red)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Center
    ) {
        PhotoButton(onCapture,imageCapture)

        // Дополнительная кнопка для разворота камеры
//        OutlinedButton(
//            onClick = { onChangeCamera() },
//            modifier = modifier.size(72.dp),
//            shape = CircleShape,
//            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
//        ) {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ){
//                Text(text = "📷", fontSize = 30.sp)
//            }
//
//        }
    }
}