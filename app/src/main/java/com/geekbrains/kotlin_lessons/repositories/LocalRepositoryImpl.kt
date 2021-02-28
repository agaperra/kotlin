package com.geekbrains.kotlin_lessons.repositories

import com.geekbrains.kotlin_lessons.models.Genres
import com.geekbrains.kotlin_lessons.room.data.HistoryEntity
import com.geekbrains.kotlin_lessons.models.MovieFull
import com.geekbrains.kotlin_lessons.models.ProductionCountries
import com.geekbrains.kotlin_lessons.room.dao.HistoryDao

class LocalRepositoryImpl(private val localDataSource: HistoryDao) : LocalRepository {

    override fun getAllHistory(): List<MovieFull> {
        return convertHistoryEntityToMovie(localDataSource.all())
    }

    override fun saveEntity(movie: MovieFull) {
        localDataSource.insert(convertMovieToEntity(movie))
    }

    private fun convertHistoryEntityToMovie(entityList: List<HistoryEntity>): List<MovieFull> =
        entityList.map {
            MovieFull(
                it.id,
                "",
                "",
                it.poster_path,
                it.release_date,
                it.title,
                0.0,
                0,
                ArrayList<Genres>(),
                ArrayList<ProductionCountries>(),
                0,
                0,
                0,
                "",
                0.0
            )
        }

    private fun convertMovieToEntity(movie: MovieFull): HistoryEntity = HistoryEntity(
        id = movie.id,
        poster_path = movie.poster_path,
        release_date = movie.release_date,
        title = movie.title
    )
}