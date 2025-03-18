package com.diplom.photoResultScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diplom.R
import com.diplom.cameraScreensInfo.CameraViewModel


//@Preview(showBackground = true)
//@Composable
//fun PhotoSuccessInfoScreenPreview(){
//    PhotoSuccessInfoScreen()
//}

@Composable
fun PhotoSuccessInfoScreen(
    cameraViewModel: CameraViewModel = viewModel(),
    toCameraScreen: () -> Unit,
    toMainScreen: () -> Unit,
){

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(7f),
            contentAlignment = Alignment.Center
        ){
            Image(
                bitmap = cameraViewModel.capturedImage.value.asImageBitmap(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
                    .padding(top = 30.dp),
                //painter = painterResource(R.drawable.pictureisloading),
                contentDescription = "Картинка до загрузки фото",
                contentScale = ContentScale.Crop
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(horizontal = 30.dp)
                .background(Color.Green),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = stringResource(R.string.photo_success),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(7f)
                .padding(30.dp),
            contentAlignment = Alignment.Center
        ){
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.check),
                contentDescription = "Галочка",
                tint = Color.Green
            )
        }
        Row(modifier = Modifier
            .fillMaxSize()
            .weight(2f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ){
                OutlinedButton(
                    onClick = { toCameraScreen() },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Переснять",
                        color = Color.Black
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ){
                OutlinedButton(
                    onClick = { toMainScreen() },
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = "В главное меню",
                        color = Color.Black
                    )
                }
            }
        }
    }
}