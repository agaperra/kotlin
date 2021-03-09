package com.geekbrains.kotlin_lessons.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.kotlin_lessons.repositories.LocalRepository
import com.geekbrains.kotlin_lessons.repositories.LocalRepositoryImpl
import com.geekbrains.kotlin_lessons.App.Companion.getHistoryDao
import com.geekbrains.kotlin_lessons.utils.AppState
import com.geekbrains.kotlin_lessons.utils.Constants
import com.geekbrains.kotlin_lessons.utils.Variables

class HistoryViewModel(
        val historyLiveData: MutableLiveData<AppState> = MutableLiveData(),
        private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())
) : ViewModel() {

    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        historyLiveData.value = AppState.Success(historyRepository.getAllHistory())
    }

    fun setPref(param: Boolean) {
        Constants.sPrefs.editor.putBoolean(Constants.PREF_ADULT, param).apply()
    }

    fun getPref() = Constants.sPrefs.retrieveBoolean(Constants.PREF_ADULT, Variables.ADULT)
}