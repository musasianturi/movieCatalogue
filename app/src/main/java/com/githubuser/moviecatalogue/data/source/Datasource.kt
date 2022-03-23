package com.githubuser.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.githubuser.moviecatalogue.data.source.local.entity.MovieEntity
import com.githubuser.moviecatalogue.data.source.local.entity.TvEntity
import com.githubuser.moviecatalogue.vo.Resource

interface Datasource {
    fun getAllMovie(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getAllTv(): LiveData<Resource<PagedList<TvEntity>>>

    fun getAllFavoriteMovie(): LiveData<PagedList<MovieEntity>>
    fun getAllFavoriteTv(): LiveData<PagedList<TvEntity>>

    fun getDetailMovie(movie_id: Int): LiveData<Resource<MovieEntity>>
    fun getDetailTv(tv_id: Int): LiveData<Resource<TvEntity>>


    fun setTvFavorite(tv: TvEntity, state: Boolean)
    fun setMovieFavorite(movie: MovieEntity, state: Boolean)
}