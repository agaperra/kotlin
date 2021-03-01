package com.geekbrains.kotlin_lessons.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.kotlin_lessons.App
import com.geekbrains.kotlin_lessons.interactors.string.StringInteractor
import com.geekbrains.kotlin_lessons.models.MovieFull
import com.geekbrains.kotlin_lessons.repositories.*
import com.geekbrains.kotlin_lessons.responses.MovieResponse
import com.geekbrains.kotlin_lessons.utils.Constants

class MovieViewModel(
    private val stringInteractor: StringInteractor,
    private val favoriteRepository: FavoriteRepository = FavoriteRepositoryImpl(App.getFavoriteDao())
) : ViewModel() {


    fun saveFavoriteToDB(movie: MovieFull) {
        favoriteRepository.saveEntity(movie)
    }

    fun findFavorite(id: Int):Int {
        return favoriteRepository.getFavoriteMovie(id)
    }

    fun deleteFavoriteFromDB(id: Int) {
        favoriteRepository.deleteEntity(id)
    }

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


    fun setPref(param: Boolean) {
        Constants.sPrefs.editor.putBoolean(Constants.PREF_ADULT, param).apply()
    }

    fun getPref() = Constants.sPrefs.retrieveBoolean(Constants.PREF_ADULT, Constants.ADULT)


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