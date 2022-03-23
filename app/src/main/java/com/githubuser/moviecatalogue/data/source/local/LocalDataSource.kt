package com.githubuser.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.githubuser.moviecatalogue.data.source.local.entity.MovieEntity
import com.githubuser.moviecatalogue.data.source.local.entity.TvEntity
import com.githubuser.moviecatalogue.data.source.local.room.MovieCatalogueDao

class LocalDataSource private constructor(private val mMovieCatalogueDao: MovieCatalogueDao){



    fun getAllMovie(): DataSource.Factory<Int, MovieEntity> = mMovieCatalogueDao.getMovies()
    fun getFavoriteMovie():DataSource.Factory<Int, MovieEntity> = mMovieCatalogueDao.getFavoriteMovie()
    fun getDetailMovie(id: Int): LiveData<MovieEntity> = mMovieCatalogueDao.getDetailMovie(id)
    fun insertMovie(movie : List<MovieEntity>) = mMovieCatalogueDao.insertMovie(movie)
    fun getFavMovieValue(id: Int):Boolean = mMovieCatalogueDao.getFavMovieValue(id)
    fun updateFavoriteMovie(movie: MovieEntity, state: Boolean){
        movie.isFavorite = state
        mMovieCatalogueDao.updateMovie(movie)
    }


    fun getAllTv() : DataSource.Factory<Int, TvEntity> = mMovieCatalogueDao.getTv()
    fun getFavoriteTv(): DataSource.Factory<Int, TvEntity> = mMovieCatalogueDao.getFavoriteTv()
    fun getDetailTv(id: Int):LiveData<TvEntity> = mMovieCatalogueDao.getDetailTv(id)
    fun insertTv(tv: List<TvEntity>) = mMovieCatalogueDao.insertTv(tv)
    fun getFavTvValue(id: Int):Boolean = mMovieCatalogueDao.getFavTvValue(id)
    fun updateFavoriteTv(tv: TvEntity, state: Boolean){
        tv.isFavorite = state
        mMovieCatalogueDao.updateTv(tv)
    }




    companion object{
        private var INSTANCE: LocalDataSource?= null

        fun getInstance(movieCatalogueDao: MovieCatalogueDao):LocalDataSource=
            INSTANCE ?: LocalDataSource(movieCatalogueDao)
    }

}