package com.geekbrains.kotlin_lessons.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavoritesViewModel : ViewModel() {

    val liveData = MutableLiveData<String>()

    init {
        start()
    }

    private fun start() {
        liveData.value = "This is favorites Fragment"
    }
}