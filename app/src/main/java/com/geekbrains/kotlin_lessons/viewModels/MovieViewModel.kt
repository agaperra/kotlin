package com.geekbrains.kotlin_lessons.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.kotlin_lessons.interactors.string.StringInteractor
import com.geekbrains.kotlin_lessons.models.Movie
import com.geekbrains.kotlin_lessons.repositories.MovieRepository
import com.geekbrains.kotlin_lessons.responses.MovieResponse

class MovieViewModel(private val stringInteractor: StringInteractor) : ViewModel() {

    private val movieRepository: MovieRepository = MovieRepository()

    private val _observingMoviesPopular = MutableLiveData<MovieResponse>()
    fun getObservedMoviesPopular() = _observingMoviesPopular

    private val _observingMoviesLookNow = MutableLiveData<MovieResponse>()
    fun getObservedMoviesLookNow() = _observingMoviesLookNow


    private val _observingMoviesUpComing = MutableLiveData<MovieResponse>()
    fun getObservedMoviesUpComing() = _observingMoviesUpComing


    private val _observingMoviesTop = MutableLiveData<MovieResponse>()
    fun getObservedMoviesTop() = _observingMoviesTop

    fun popularMovie() {
        movieRepository.getPopularMovies(_observingMoviesPopular)
    }

    fun lookNowMovie() {
        movieRepository.getLookNowMovies(_observingMoviesLookNow)
    }

    fun upComingMovie() {
        movieRepository.getUpComingMovies(_observingMoviesUpComing)
    }

    fun topMovie() {
        movieRepository.getTopMovies(_observingMoviesTop)
    }

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