package com.diplom.photoResultScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diplom.R
import com.diplom.cameraScreensInfo.CameraViewModel
import com.diplom.core.MLInformation

//@Preview(showBackground = true)
//@Composable
//fun PhotoDeniedInfoScreenPreview(){
//    PhotoDeniedInfoScreen()
//}

@Composable
fun PhotoDeniedInfoScreen(
    cameraViewModel: CameraViewModel = viewModel(),
    toCameraScreen: () -> Unit,
    toMainScreen: () -> Unit,

){
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(7f)
                .padding(top = 20.dp),
            
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
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(horizontal = 30.dp)
                .background(Color.Red.copy(alpha = 0.4f)),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(4f),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = stringResource(R.string.photo_denied),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ){
                Icon(
                    painter = painterResource(R.drawable.cross_red),
                    contentDescription = "Крестик",
                    tint = Color.Red
                )
            }

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(7f)
                .padding(
                    start = 30.dp,
                    top = 40.dp,
                    end = 40.dp,
                    bottom = 15.dp
                )
                .background(Color.Gray.copy(alpha = 0.5f))
            ,
            verticalArrangement = Arrangement.SpaceAround
        ){
            val faces = MLInformation.countOfFaces
            if (faces == 0f){
                TextListItem(
                    text = "Лицо не обнаружено",
                )
            }else if(faces!! > 1f){
                TextListItem(
                    text = "На фото должен быть один человек",
                )
            }else{
                if (MLInformation.chanceOfSmiling!!>0.35){
                    TextListItem(
                        text = "Не улыбайтесь"
                    )
                }
                if (MLInformation.chanceOfClosedLeftEye!!>0.35 || MLInformation.chanceOfClosedRightEye!!>0.35){
                    TextListItem(
                        text = "Откройте глаза"
                    )
                }
            }
        }
        Row(modifier = Modifier
            .fillMaxSize()
            .weight(2f),
            verticalAlignment = Alignment.CenterVertically
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

@Composable
fun TextListItem(text:String){
    Text(
        modifier = Modifier
            .padding(start = 30.dp),
        text = "• ${text}",
        textAlign = TextAlign.Start,
        fontSize = 20.sp
    )
}