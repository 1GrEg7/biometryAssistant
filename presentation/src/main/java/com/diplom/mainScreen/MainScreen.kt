package com.diplom.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diplom.R
import com.diplom.core.MLInformation


//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview(){
//    MainScreen()
//}

@Composable
fun MainScreen(
    vm: MainScreenViewModel = viewModel(),
    toCameraScreen: () -> Unit
){

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(id = R.drawable.generic_avatar),
                contentDescription = "Описание изображения",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(horizontal = 30.dp, vertical = 60.dp)
                .background(Color.Gray.copy(alpha = 0.5f))
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().weight(2f),
                    contentAlignment = Alignment.Center
                ){
                    val text: String
                    if (MLInformation.isDataSucceessful){
                        text = stringResource(R.string.data_is_provided)
                    }else{
                        text = stringResource(R.string.data_isn_t_provided)
                    }
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(15.dp),
                        text = text,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),    // или .fillMaxWidth() в зависимости от задачи
                    contentAlignment = Alignment.Center
                ) {
                    val icon: Painter
                    val color: Color
                    if (MLInformation.isDataSucceessful){
                        icon = painterResource(id = R.drawable.check)
                        color = Color.Green
                    }else{
                        icon = painterResource(id = R.drawable.cross)
                        color = Color.Red
                    }
                    Icon(
                        painter = icon,
                        contentDescription = "Иконка креста",
                        tint = color
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center

        ){
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    toCameraScreen()
                }
            ) {
                Text(
                    text = "Provide",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }



    }

}