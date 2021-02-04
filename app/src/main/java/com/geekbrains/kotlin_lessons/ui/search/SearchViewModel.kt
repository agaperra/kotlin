package com.geekbrains.kotlin_lessons.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    val liveData=MutableLiveData<String>()

    init {
        start()
    }

    private fun start(){
        liveData.value="This is search Fragment"
    }
}