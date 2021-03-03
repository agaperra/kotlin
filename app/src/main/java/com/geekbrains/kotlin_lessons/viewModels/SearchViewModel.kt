package com.geekbrains.kotlin_lessons.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.kotlin_lessons.interactors.string.StringInteractor
import com.geekbrains.kotlin_lessons.repositories.SearchRepository
import com.geekbrains.kotlin_lessons.responses.ActorsResponse
import com.geekbrains.kotlin_lessons.responses.MovieResponse
import com.geekbrains.kotlin_lessons.utils.Constants

class SearchViewModel(private val stringInteractor: StringInteractor) : ViewModel() {
    private val searchRepository: SearchRepository = SearchRepository()

    private var _observingMovies = MutableLiveData<MovieResponse>()
    fun getMovies() = _observingMovies

    private var _observingActors = MutableLiveData<ActorsResponse>()
    fun getActors() = _observingActors
    val liveDataPictures = MutableLiveData<String>()
    val liveDataActors = MutableLiveData<String>()

    init {
        start()
    }

    private fun start() {
        liveDataPictures.value = stringInteractor.textSearch
        liveDataActors.value = stringInteractor.textActor
    }

    fun setPref(param: Boolean) {
        Constants.sPrefs.editor.putBoolean(Constants.PREF_ADULT, param).apply()
    }

    fun getPref() = Constants.sPrefs.retrieveBoolean(Constants.PREF_ADULT, Constants.ADULT)


    fun textChanged(query: String) {
        when (query) {
            "" -> {
                return
            }
            else -> {
                searchRepository.searchMovies(query = query, _observingMovies)
                searchRepository.searchActors(query = query, _observingActors)
            }
        }


    }

}