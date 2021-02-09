package com.geekbrains.kotlin_lessons.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.kotlin_lessons.interactors.string.StringInteractor
import com.geekbrains.kotlin_lessons.repositories.SearchRepository
import com.geekbrains.kotlin_lessons.responses.MovieResponse

class SearchViewModel(private val stringInteractor: StringInteractor) : ViewModel() {
    private val searchRepository: SearchRepository = SearchRepository()

    private var _observingMovies = MutableLiveData<MovieResponse>()
    fun getMovies() = _observingMovies

    val liveDataPictures = MutableLiveData<String>()

    init {
        start()
    }

    private fun start() {
        liveDataPictures.value = stringInteractor.textSearch
    }


    fun textChanged(query: String) {
        if (query == "") {
            return
        }
        searchRepository.searchMovies(query = query, _observingMovies)
    }

}