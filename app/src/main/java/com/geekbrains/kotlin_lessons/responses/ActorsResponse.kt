package com.geekbrains.kotlin_lessons.responses

import com.geekbrains.kotlin_lessons.models.Actor
import com.google.gson.annotations.SerializedName

data class ActorsResponse(
        val cast: List<Actor>
)