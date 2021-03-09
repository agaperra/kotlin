package com.geekbrains.kotlin_lessons.viewModels

import android.view.View
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.kotlin_lessons.interactors.string.StringInteractor
import com.geekbrains.kotlin_lessons.models.MovieFull
import com.geekbrains.kotlin_lessons.models.ProductionCountries
import com.geekbrains.kotlin_lessons.responses.CastResponse
import com.geekbrains.kotlin_lessons.App
import com.geekbrains.kotlin_lessons.repositories.*

class InfoViewModel(
    private val stringInteractor: StringInteractor,
    private val historyRepository: LocalRepository = LocalRepositoryImpl(App.getHistoryDao()),
    private val favoriteRepository: FavoriteRepository = FavoriteRepositoryImpl(App.getFavoriteDao()),
    private val watchedRepository: WatchedRepository = WatchedRepositoryImpl(App.getWatchedDao()),
    private val noteRepository: NoteRepository = NoteRepositoryImpl(App.getNoteDao())
) : ViewModel(),
    LifecycleObserver, View.OnClickListener {

    fun saveMovieToDB(movie: MovieFull) {
        historyRepository.saveEntity(movie)
    }

    fun saveFavoriteToDB(movie: MovieFull) {
        favoriteRepository.saveEntity(movie)
    }

    fun saveWatchedToDB(movie: MovieFull) {
        watchedRepository.saveEntity(movie.id)
    }

    fun saveNoteToDB(movie: MovieFull, string: String) {
        noteRepository.saveEntity(movie.id, string)
    }

    fun getId(id: Int): Int {
        return noteRepository.getNote(id)
    }

    fun getNote(id: Int): String {
        return noteRepository.findNote(id)
    }


    fun findFavorite(id: Int): Int {
        return favoriteRepository.getFavoriteMovie(id)
    }

    fun findWatched(id: Int): Int {
        return watchedRepository.getWatched(id)
    }


    fun deleteFavoriteFromDB(id: Int) {
        favoriteRepository.deleteEntity(id)
    }

    fun deleteWatchedFromDB(id: Int) {
        watchedRepository.deleteEntity(id)
    }

    fun deleteNoteFromDB(id: Int) {
        noteRepository.deleteEntity(id)
    }


    private val _observingMovie = MutableLiveData<MovieFull>()
    private val _observingPeople = MutableLiveData<CastResponse>()
    private val movieDetailsRepository: MovieDetailsRepository = MovieDetailsRepository()

    fun getObservedMovie() = _observingMovie
    fun getPeople() = _observingPeople


    fun getOverview(overview: String?): String {
        overview?.let {
            return overview
        } ?: return stringInteractor.textNoOverview

    }


    fun getRuntime(runtime: Int?): String {
        runtime?.let {
            val hours = runtime / 60
            val minutes = runtime % 60
            return (String.format("%d", hours) + stringInteractor.textHour + String.format(
                "%02d",
                minutes
            ) + stringInteractor.textMinute)
        } ?: return stringInteractor.textUnknownRuntime

    }

    fun getDetails(movieId: Int) {
        movieDetailsRepository.getDetailsMovie(id = movieId, _observingMovie = _observingMovie)
        movieDetailsRepository.getPeople(id = movieId, _observingPeople)
    }

    fun getCountry(productionCountries: List<ProductionCountries>?): String {
        productionCountries?.let {
            return if (productionCountries.isEmpty()) {
                stringInteractor.textUnknown
            } else {
                productionCountries.first().name
            }
        } ?: return stringInteractor.textUnknown
    }

    fun getDate(date: String) = date.take(4)


    override fun onClick(v: View?) {
    }
}