package com.githubuser.moviecatalogue.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.githubuser.moviecatalogue.data.source.Repository
import com.githubuser.moviecatalogue.data.source.local.entity.MovieEntity

class FavoriteMovieViewModel(private val repository: Repository) : ViewModel(){
    fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>> = repository.getAllFavoriteMovie()

    fun setFavoriteMovie(movieEntity: MovieEntity) {
        val newState = !movieEntity.isFavorite
        repository.setMovieFavorite(movieEntity, newState)
    }
}