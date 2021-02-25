package com.geekbrains.kotlin_lessons.responses

import com.geekbrains.kotlin_lessons.models.Actor

data class ActorsResponse(
        val page: Int,
        val results: ArrayList<Actor>,
        val total_results: Int,
        val total_pages: Int

)