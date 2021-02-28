package com.geekbrains.kotlin_lessons.responses

import com.geekbrains.kotlin_lessons.models.Actor

data class CastResponse(
        val cast: List<Actor>
)