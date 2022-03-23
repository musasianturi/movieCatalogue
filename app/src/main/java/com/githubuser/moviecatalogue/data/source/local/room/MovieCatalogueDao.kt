package com.githubuser.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.githubuser.moviecatalogue.data.source.local.entity.MovieEntity
import com.githubuser.moviecatalogue.data.source.local.entity.TvEntity

@Dao
interface MovieCatalogueDao {


    @Query("SELECT * FROM movieentities")
    fun getMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movieentities WHERE id = :id")
    fun getDetailMovie(id: Int): LiveData<MovieEntity>

    @Query("SELECT * FROM movieentities WHERE isFavorite = 1")
    fun getFavoriteMovie(): DataSource.Factory<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movie: List<MovieEntity>)

    @Query("SELECT isFavorite FROM movieentities WHERE id = :id")
    fun getFavMovieValue(id: Int): Boolean

    @Update
    fun updateMovie(movie: MovieEntity)


    @Query("SELECT * FROM tventities")
    fun getTv(): DataSource.Factory<Int, TvEntity>

    @Query("SELECT * FROM tventities WHERE id = :id")
    fun getDetailTv(id: Int): LiveData<TvEntity>

    @Query("SELECT * FROM tventities WHERE isFavorite = 1")
    fun getFavoriteTv(): DataSource.Factory<Int, TvEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTv(tv: List<TvEntity>)

    @Query("SELECT isFavorite FROM tventities WHERE id = :id")
    fun getFavTvValue(id: Int): Boolean

    @Update
    fun updateTv(tv: TvEntity)



}