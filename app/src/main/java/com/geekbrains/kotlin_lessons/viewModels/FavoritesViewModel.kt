package com.geekbrains.kotlin_lessons.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.kotlin_lessons.App
import com.geekbrains.kotlin_lessons.repositories.FavoriteRepository
import com.geekbrains.kotlin_lessons.repositories.FavoriteRepositoryImpl
import com.geekbrains.kotlin_lessons.utils.AppState
import com.geekbrains.kotlin_lessons.utils.Constants
import com.geekbrains.kotlin_lessons.utils.Variables

class FavoritesViewModel(
        val favoriteLiveData: MutableLiveData<AppState> = MutableLiveData(),
        private val favoriteRepository: FavoriteRepository = FavoriteRepositoryImpl(App.getFavoriteDao())
) : ViewModel() {

    fun getAllFavorite() {
        favoriteLiveData.value = AppState.Loading
        favoriteLiveData.value = AppState.Success(favoriteRepository.getAllFavorite())
    }

    fun setPref(param: Boolean) {
        Constants.sPrefs.editor.putBoolean(Constants.PREF_ADULT, param).apply()
    }

    fun getPref() = Constants.sPrefs.retrieveBoolean(Constants.PREF_ADULT, Variables.ADULT)
}