package com.diplom.core

object MLInformation {
    
    var countOfFaces: Float? = 0f
    var chanceOfSmiling: Float? = 0f
    var chanceOfClosedLeftEye: Float? = 0f
    var chanceOfClosedRightEye: Float? = 0f



    fun getMlParametrs():List<Float?> = mutableListOf<Float?>().apply {
        add(chanceOfSmiling)
        add(chanceOfClosedLeftEye)
        add(chanceOfClosedRightEye)
    }


    var isInfoProvided = false
    var isDataSucceessful = false


}