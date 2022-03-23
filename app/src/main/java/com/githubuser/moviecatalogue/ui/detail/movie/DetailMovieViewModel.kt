package com.githubuser.moviecatalogue.ui.detail.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.githubuser.moviecatalogue.data.source.Repository
import com.githubuser.moviecatalogue.data.source.local.entity.MovieEntity
import com.githubuser.moviecatalogue.vo.Resource

class DetailMovieViewModel(private val repository: Repository) : ViewModel() {

    private lateinit var movieLiveData: LiveData<Resource<MovieEntity>>

    fun setMovie(id: Int){
        movieLiveData = repository.getDetailMovie(id)
    }


    fun getMovie(): LiveData<Resource<MovieEntity>> = movieLiveData

    fun setFavoriteMovie() {
        val favorite = movieLiveData.value
        favorite?.let{ Favorite ->
        Favorite.data?.let {
            val newState = !it.isFavorite
            repository.setMovieFavorite(it, newState)

        }

        }
    }

}