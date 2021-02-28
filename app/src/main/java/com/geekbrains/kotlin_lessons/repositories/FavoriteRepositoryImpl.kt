package com.geekbrains.kotlin_lessons.repositories

import com.geekbrains.kotlin_lessons.models.Movie
import com.geekbrains.kotlin_lessons.room.dao.FavoriteDao
import com.geekbrains.kotlin_lessons.room.data.Favorite

class FavoriteRepositoryImpl(private val localDataSource: FavoriteDao) : FavoriteRepository {

    override fun getAllFavorite(): List<Movie> {
        return convertFavoriteEntityToMovie(localDataSource.all())
    }

    override fun saveEntity(movie: Movie) {
        localDataSource.insert(convertMovieToEntity(movie))
    }

    private fun convertFavoriteEntityToMovie(entityList: List<Favorite>): List<Movie> {
        return entityList.map {
            Movie(it.id , "", "", "","","")
        }
    }

    private fun convertMovieToEntity(movie: Movie):Favorite {
        return Favorite(movie.id)
    }
}