package com.diplom.photoResultScreens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diplom.R
import com.diplom.core.MLInformation
import kotlinx.coroutines.delay


//@Preview(showBackground = true)
//@Composable
//fun WaintingScreenPreview(){
//    WaintingScreen()
//}

@Composable
fun WaitingScreen(
    toSuccessScreen: () -> Unit,
    toFailScreen: () -> Unit
){
    BackHandler(enabled = true){}

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(7f),
            contentAlignment = Alignment.Center
        ){
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
                    .padding(top = 30.dp),
                painter = painterResource(R.drawable.pictureisloading),
                contentDescription = "Картинка до загрузки фото",
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(7f)
                .padding(30.dp),
            contentAlignment = Alignment.Center
        ){
            val infiniteTransition = rememberInfiniteTransition()
            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )

            // Центрируем индикатор на экране
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                // Используем стандартный CircularProgressIndicator с применением анимации вращения
                CircularProgressIndicator(modifier = Modifier.size(100.dp).rotate(rotation))
                LaunchedEffect(MLInformation.isInfoProvided) {
                    delay(2000)
                    if (MLInformation.isInfoProvided){
                        if (MLInformation.countOfFaces == 1f){
                            val list = MLInformation.getMlParametrs()
                            if (list.any { it!! > 0.35f }){
                                toFailScreen()
                                MLInformation.isDataSucceessful = false
                            }else{
                                toSuccessScreen()
                                MLInformation.isDataSucceessful = true
                            }
                        }else{
                            toFailScreen()
                            MLInformation.isDataSucceessful = false
                        }
                    }
                }
            }
        }
    }
}