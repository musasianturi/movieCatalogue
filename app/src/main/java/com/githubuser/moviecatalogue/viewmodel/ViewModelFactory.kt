package com.githubuser.moviecatalogue.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.githubuser.moviecatalogue.data.source.Repository
import com.githubuser.moviecatalogue.di.Injection
import com.githubuser.moviecatalogue.ui.detail.movie.DetailMovieViewModel
import com.githubuser.moviecatalogue.ui.detail.tv.DetailTvShowViewModel
import com.githubuser.moviecatalogue.ui.favorite.movie.FavoriteMovieViewModel
import com.githubuser.moviecatalogue.ui.favorite.tvshow.FavoriteTvViewModel
import com.githubuser.moviecatalogue.ui.movie.MovieViewModel
import com.githubuser.moviecatalogue.ui.tvshow.TvShowViewModel

class ViewModelFactory private constructor(private val mRepository: Repository) :
    ViewModelProvider.NewInstanceFactory() {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                return MovieViewModel(mRepository) as T
            }

            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                return TvShowViewModel(mRepository) as T
            }
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                return DetailMovieViewModel(mRepository) as T
            }
            modelClass.isAssignableFrom(DetailTvShowViewModel::class.java) -> {
                return DetailTvShowViewModel(mRepository) as T
            }

            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) -> {
                return FavoriteMovieViewModel(mRepository) as T
            }

            modelClass.isAssignableFrom(FavoriteTvViewModel::class.java) -> {
                return FavoriteTvViewModel(mRepository) as T
            }

            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) -> {
                return FavoriteMovieViewModel(mRepository) as T
            }

            else -> throw Throwable("Unknow View Modle Class: " + modelClass.name)

        }


    }


    companion object {
        @Volatile

        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =

            instance ?: synchronized(this) {

                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }
    }
}