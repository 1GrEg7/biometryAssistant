package com.diplom.Navigation

sealed class Screen(val route: String) {
    object MainScreen : Screen("mainScreen")
    object CameraScreen : Screen("cameraScreen")
    object PhotoDeniedInfoScreen : Screen("photoDeniedInfoScreen")
    object PhotoSuccessInfoScreen : Screen("photoSuccessInfoScreen")
    object WaitingScreen : Screen("waitingScreen")
}
