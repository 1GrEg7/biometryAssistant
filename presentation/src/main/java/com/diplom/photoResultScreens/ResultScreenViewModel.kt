package com.diplom.photoResultScreens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ResultScreenViewModel:ViewModel(

) {


    private val _isSmiling = mutableStateOf(true)
    val isSmiling = _isSmiling

    private val _eyesAreOpen = mutableStateOf(true)
    val eyesAreOpen = _eyesAreOpen

    private val _countOfFaces = mutableStateOf(0)
    val countOfFaces = _countOfFaces




}