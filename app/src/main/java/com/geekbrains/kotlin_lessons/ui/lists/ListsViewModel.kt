package com.geekbrains.kotlin_lessons.ui.lists

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListsViewModel : ViewModel() {

   val liveData=MutableLiveData<String>()

    init {
        start()
    }

    private fun start(){
        liveData.value="This is lists Fragment"
    }
}