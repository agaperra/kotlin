package com.geekbrains.kotlin_lessons.models

data class MovieFull(
        val id: Int,
        val original_title: String,
        val overview: String?,
        val poster_path: String,
        val release_date: String,
        val title: String,
        val vote_average: Double,
        val vote_count: Int,
        val genres: ArrayList<Genres>,
        val production_countries:ArrayList<ProductionCountries>,
        val budget: Int,
        val revenue: Int,
        val runtime: Int?,
        val status: String,
        val popularity: Double
)