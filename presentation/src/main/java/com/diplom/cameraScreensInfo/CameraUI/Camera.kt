package com.diplom.cameraScreensInfo.CameraUI

// Импорты для Compose и вспомогательных библиотек
import android.content.Context
import androidx.camera.core.ImageCapture
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diplom.cameraScreensInfo.CameraViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


// Основной composable, показывающий превью камеры с оверлеем
@Composable
fun Camera(
    cameraViewModel: CameraViewModel = viewModel(),
    toResultScreen: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }
    val cameraExecutor: ExecutorService = remember { Executors.newSingleThreadExecutor() }

    Box(modifier = Modifier.fillMaxSize()) {
        var previewView: PreviewView = PreviewView(context)
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                previewView = PreviewView(ctx)
                cameraViewModel.setUpCamera(
                    activityContextProvider = { ctx },
                    lifecycleProvider = { lifecycleOwner },
                    previewViewProvider = { previewView }
                ) { newImageCapture ->
                    imageCapture = newImageCapture.getImageCapture() as ImageCapture
                }
                previewView
            }
        )

        // Оверлей с овалом лица
        FaceOvalOverlay()

        // Кнопка захвата фото
        PhotoPanel(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            onCapture = { imageCapture ->
                imageCapture?.let {
                    cameraViewModel.takePhoto(
                        imageCapture,
                        activityContextProvider = { context },
                        lifecycleProvider = { context },
                        toScreen = toResultScreen
                    )
                }
            },
            imageCapture = imageCapture,
            onChangeCamera = {

            }
        )
    }
    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }
}

// Composable для рисования оверлея с вырезанным овалом лица
@Composable
fun FaceOvalOverlay() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Задаем размеры овала (например, 80% по ширине и 50% по высоте)
        val ovalWidth = canvasWidth * 0.9f
        val ovalHeight = canvasHeight * 0.6f
        val ovalLeft = (canvasWidth - ovalWidth) / 2f
        val ovalTop = (canvasHeight - ovalHeight) / 2.5f

        // Полный прямоугольник экрана
        val fullRect = Rect(0f, 0f, canvasWidth, canvasHeight)
        // Прямоугольник, описывающий овал
        val ovalRect = Rect(ovalLeft, ovalTop, ovalLeft + ovalWidth, ovalTop + ovalHeight)

        // Путь для затемнения области вне овала
        val path = Path.combine(
            PathOperation.Difference,
            Path().apply { addRect(fullRect) },
            Path().apply { addOval(ovalRect) }
        )

        // Рисуем затемненные области
        drawPath(
            path = path,
            color = Color.Black.copy(alpha = 0.5f)
        )

        // Рисуем границу овала
        drawOval(
            color = Color.White,
            topLeft = Offset(ovalLeft, ovalTop),
            size = Size(ovalWidth, ovalHeight),
            style = Stroke(width = 4.dp.toPx())
        )
    }
}