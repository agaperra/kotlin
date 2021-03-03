package com.geekbrains.kotlin_lessons.repositories

import com.geekbrains.kotlin_lessons.models.Movie
import com.geekbrains.kotlin_lessons.models.MovieFull
import com.geekbrains.kotlin_lessons.room.dao.FavoriteDao
import com.geekbrains.kotlin_lessons.room.data.Favorite
import java.util.ArrayList

class FavoriteRepositoryImpl(private val localDataSource: FavoriteDao) : FavoriteRepository {

    override fun getAllFavorite(): List<MovieFull> {
        return convertFavoriteEntityToMovie(localDataSource.all())
    }

    override fun getFavoriteMovie(id: Int): Int {
        return localDataSource.find(id)
    }

    override fun saveEntity(movie: MovieFull) {
        localDataSource.insert(convertMovieToEntity(movie))
    }

    override fun deleteEntity(id: Int) {
        localDataSource.drop(id)
    }


    private fun convertFavoriteEntityToMovie(entityList: List<Favorite>): List<MovieFull> {
        return entityList.map {
            MovieFull(
                    it.id,
                    "",
                    it.overview,
                    it.poster_path,
                    it.release_date,
                    it.title,
                    0.0,
                    0,
                    ArrayList(),
                    ArrayList(),
                    0,
                    0,
                    0,
                    "",
                    0.0
            )
        }
    }


    private fun convertMovieToEntity(movie: MovieFull): Favorite {
        return Favorite(movie.id, movie.overview, movie.poster_path, movie.release_date, movie.title)
    }
}