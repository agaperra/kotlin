package com.geekbrains.kotlin_lessons.viewModels

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.kotlin_lessons.interactors.string.StringInteractor
import com.geekbrains.kotlin_lessons.models.MovieFull
import com.geekbrains.kotlin_lessons.models.ProductionCountries
import com.geekbrains.kotlin_lessons.repositories.MovieDetailsRepository

class InfoViewModel(private val stringInteractor: StringInteractor) : ViewModel(), LifecycleObserver {

    private val _observingMovie = MutableLiveData<MovieFull>()
    private val movieDetailsRepository: MovieDetailsRepository = MovieDetailsRepository()

    fun getObservedMovie() = _observingMovie


    fun getOverview(overview: String?): String {
        if (overview == null) return stringInteractor.textNoOverview
        return overview
    }

    fun getRuntime(runtime: Int?): String {
        if (runtime == null) return stringInteractor.textUnknownRuntime
        val hours = runtime / 60
        val minutes = runtime % 60
        return (String.format("%d", hours) + stringInteractor.textHour + String.format("%02d", minutes) + stringInteractor.textMinute)
    }

    fun getDetails(movieId: Int) {
        movieDetailsRepository.getDetailsMovie( id = movieId,_observingMovie = _observingMovie)
    }

    fun getCountry(productionCountries: List<ProductionCountries>): String {
        if (productionCountries.isEmpty()) return stringInteractor.textUnknown
        return productionCountries[0].name
    }

    fun getDate(date: String) = date.substring(0, 4)
}