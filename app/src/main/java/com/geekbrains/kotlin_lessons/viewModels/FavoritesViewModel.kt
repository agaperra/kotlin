package com.geekbrains.kotlin_lessons.viewModels

import androidx.lifecycle.ViewModel
import com.geekbrains.kotlin_lessons.utils.Constants

class FavoritesViewModel: ViewModel() {

//    private val favoriteDao: FavoriteDao=  App.getFavoriteDao()
//
//    private lateinit var favoriteRepository: FavoriteRepository
//
//    private val _observingMoviesFavorites = MutableLiveData<MovieResponse>()
//    fun getObservedMoviesFavorites() = _observingMoviesFavorites
//
//    fun favoritesMovie() {
//        favoriteRepository.getAllFavorite()
//    }

    fun setPref(param: Boolean){
        Constants.sPrefs.editor.putBoolean(Constants.PREF_ADULT, param).apply()
    }

    fun getPref()= Constants.sPrefs.retrieveBoolean(Constants.PREF_ADULT, Constants.ADULT)

}