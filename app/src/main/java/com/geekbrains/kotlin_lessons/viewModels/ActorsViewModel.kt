package com.geekbrains.kotlin_lessons.viewModels

import android.view.View
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.kotlin_lessons.interactors.string.StringInteractor
import com.geekbrains.kotlin_lessons.models.ActorFull
import com.geekbrains.kotlin_lessons.models.MovieFull
import com.geekbrains.kotlin_lessons.models.ProductionCountries
import com.geekbrains.kotlin_lessons.repositories.ActorsDetailsRepository
import com.geekbrains.kotlin_lessons.repositories.MovieDetailsRepository
import com.geekbrains.kotlin_lessons.responses.CastResponse

class ActorsViewModel : ViewModel(),
    LifecycleObserver {

    private val _observingActor = MutableLiveData<ActorFull>()
    private val actorDetailsRepository: ActorsDetailsRepository = ActorsDetailsRepository()

    fun getObservedActor() = _observingActor

    fun getDetails(actorId: Int) {
        actorDetailsRepository.getDetailsActor(id = actorId, _observingActor)
    }
}