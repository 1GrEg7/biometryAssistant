package com.diplom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.diplom.Navigation.Screen
import com.diplom.cameraScreensInfo.CameraUI.CameraScreen
import com.diplom.cameraScreensInfo.CameraViewModel
import com.diplom.core.permissionsInfo.PermissionsProvider
import com.diplom.data.cameraData.CameraRepoImpl
import com.diplom.core.providers.ActivityContextProvider
import com.diplom.core.providers.LifecycleProvider
import com.diplom.data.cameraData.MLRepoImpl
import com.diplom.mainScreen.MainScreen
import com.diplom.mainScreen.MainScreenViewModel
import com.diplom.photoResultScreens.PhotoDeniedInfoScreen
import com.diplom.photoResultScreens.PhotoSuccessInfoScreen
import com.diplom.photoResultScreens.WaitingScreen
import com.diplom.ui.theme.BiometryAssistantTheme




class MainActivity : ComponentActivity(), ActivityContextProvider, LifecycleProvider {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissionsProvider: PermissionsProvider = PermissionsProvider(this)

        val mainScreenViewModel = ViewModelProvider(
            this,
            MainScreenViewModel.MainScreenViewModelFactory(
                cameraRepoImpl = CameraRepoImpl,
                activityContextProvider = this,
                lifecycleProvider = this,
                permissionsProvider
            )
        ).get(MainScreenViewModel::class.java)

        val cameraScreenViewModel = ViewModelProvider(
            this, // передаем LifecycleOwner (Activity)
            CameraViewModel.CameraViewModelFactory(
                cameraRepoImpl = CameraRepoImpl,
                mlRepoImpl = MLRepoImpl
            )
        ).get(CameraViewModel::class.java)

        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Screen.MainScreen.route
            ) {
                composable(
                    Screen.MainScreen.route
                ){
                    MainScreen(
                        vm = mainScreenViewModel,
                        toCameraScreen = {
                            navController.navigate(Screen.CameraScreen.route)
                        }
                    )
                }

                composable(
                    Screen.CameraScreen.route
                ){
                    CameraScreen(
                        cameraViewModel = cameraScreenViewModel,
                        toResultScreen = {
                            navController.navigate(Screen.WaitingScreen.route){
                                popUpTo(Screen.CameraScreen.route) { inclusive = true }
                            }
                        }
                    )
                }

                composable(
                    Screen.PhotoDeniedInfoScreen.route
                ){
                    PhotoDeniedInfoScreen(
                        cameraViewModel = cameraScreenViewModel,
                        toCameraScreen = {
                            navController.navigate(Screen.CameraScreen.route){
                                popUpTo(Screen.PhotoSuccessInfoScreen.route) { inclusive = true }
                            }
                        },
                        toMainScreen = {
                            navController.navigate(Screen.MainScreen.route){
                                popUpTo(Screen.PhotoSuccessInfoScreen.route) { inclusive = true }
                            }
                        }
                    )
                }

                composable(
                    Screen.PhotoSuccessInfoScreen.route
                ){
                    PhotoSuccessInfoScreen(
                        cameraViewModel = cameraScreenViewModel,
                        toCameraScreen = {
                            navController.navigate(Screen.CameraScreen.route){
                                popUpTo(Screen.PhotoSuccessInfoScreen.route) { inclusive = true }
                            }
                        },
                        toMainScreen = {
                            navController.navigate(Screen.MainScreen.route){
                                popUpTo(Screen.PhotoSuccessInfoScreen.route) { inclusive = true }
                            }
                        }
                    )
                }

                composable(
                    Screen.WaitingScreen.route
                ){
                    WaitingScreen(
                        toSuccessScreen = {
                            navController.navigate(Screen.PhotoSuccessInfoScreen.route){
                                popUpTo(Screen.WaitingScreen.route) { inclusive = true }
                            }
                        },
                        toFailScreen = {
                            navController.navigate(Screen.PhotoDeniedInfoScreen.route){
                                popUpTo(Screen.WaitingScreen.route) { inclusive = true }
                            }
                        }
                    )
                }

            }

            BiometryAssistantTheme {

            }
        }
    }

    override fun getActivityContext(): Any {
        return this
    }

    override fun getLifecycleOwner(): Any {
        return this
    }
}
