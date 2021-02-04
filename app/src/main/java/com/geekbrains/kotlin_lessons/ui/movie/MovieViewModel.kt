package com.geekbrains.kotlin_lessons.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieViewModel : ViewModel() {

    val liveData=MutableLiveData<String>()

    init {
        start()
    }

    private fun start(){
        liveData.value="This is movie Fragment"
    }
}