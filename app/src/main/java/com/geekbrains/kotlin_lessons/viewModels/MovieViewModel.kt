package com.geekbrains.kotlin_lessons.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.kotlin_lessons.BuildConfig
import com.geekbrains.kotlin_lessons.interactors.string.StringInteractor
import com.geekbrains.kotlin_lessons.repositories.*
import com.geekbrains.kotlin_lessons.responses.MovieResponse

class MovieViewModel(
    private val stringInteractor: StringInteractor,
) : ViewModel() {

    private val movieRepository: MovieRepository = MovieRepository()

    private val _observingMoviesPopular = MutableLiveData<MovieResponse>()

    private val _observingMoviesLookNow = MutableLiveData<MovieResponse>()

    private val _observingMoviesUpComing = MutableLiveData<MovieResponse>()

    private val _observingMoviesTop = MutableLiveData<MovieResponse>()

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

    fun getObserving(temp: Int): MutableLiveData<MovieResponse> {
        return when(temp){
            0->_observingMoviesPopular
            1->_observingMoviesLookNow
            2-> _observingMoviesUpComing
            else ->_observingMoviesTop
        }
    }
    fun setFilms(temp: Int){
        when(temp){
            0->movieRepository.getPopularMovies(_observingMoviesPopular)
            1->movieRepository.getLookNowMovies(_observingMoviesLookNow)
            2->movieRepository.getUpComingMovies(_observingMoviesUpComing)
            else->movieRepository.getTopMovies(_observingMoviesTop)
        }
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