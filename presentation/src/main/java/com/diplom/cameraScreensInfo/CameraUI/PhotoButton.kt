package com.diplom.cameraScreensInfo.CameraUI

import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun PhotoButton(
    onCapture: (ImageCapture?) -> Unit,
    imageCapture: ImageCapture?)
{
    var lastClickTime by remember { mutableStateOf(System.currentTimeMillis()) }
    val interval = 3000L
    val context = LocalContext.current
    var flag  by remember { mutableStateOf(true) }
    Box(modifier = Modifier
        .size(72.dp)
        .clip(CircleShape)
        .background(Color.Black),
        contentAlignment = Alignment.Center
    )
    {
        Box(Modifier.size(55.dp).clip(CircleShape).background(Color.White).clickable {
            val now = System.currentTimeMillis()
            if (now - lastClickTime >= interval) {
                flag = true
                lastClickTime = now
                onCapture(imageCapture)
            }else{
                if (flag){
                    Toast.makeText(context, "Фото можно сделать через 3 секунды", Toast.LENGTH_SHORT).show()
                    flag = false
                }
            }
        })
    }
}