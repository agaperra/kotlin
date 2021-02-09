package com.geekbrains.kotlin_lessons.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.kotlin_lessons.interactors.string.StringInteractor
import com.geekbrains.kotlin_lessons.repositories.MovieRepository
import com.geekbrains.kotlin_lessons.responses.MovieResponse

class MovieViewModel(private val stringInteractor: StringInteractor) : ViewModel() {

    private val movieRepository: MovieRepository = MovieRepository()


    val popularMovie: LiveData<MovieResponse>
        get() = movieRepository.getPopularMovies()

    val lookNowMovie: LiveData<MovieResponse>
        get() = movieRepository.getLookNowMovies()


    val upComingMovie: LiveData<MovieResponse>
        get() = movieRepository.getUpComingMovies()

    val topMovie: LiveData<MovieResponse>
        get() = movieRepository.getTopMovies()

    val liveDataPopular = MutableLiveData<String>()
    val liveDataNowPlaying = MutableLiveData<String>()
    val liveDataUpComing = MutableLiveData<String>()
    val liveDataTop = MutableLiveData<String>()

    init {
        setPopular()
        setNowPlaying()
        setUpComing()
        setTop()
    }

    private fun setPopular() {
        liveDataPopular.value = stringInteractor.textPopular
    }

    private fun setNowPlaying() {
        liveDataNowPlaying.value = stringInteractor.textLookNow
    }

    private fun setUpComing() {
        liveDataUpComing.value = stringInteractor.textUpComing
    }

    private fun setTop() {
        liveDataTop.value = stringInteractor.textTop
    }

}