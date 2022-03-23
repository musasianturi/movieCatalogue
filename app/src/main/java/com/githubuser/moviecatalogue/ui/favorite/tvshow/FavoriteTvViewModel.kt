package com.githubuser.moviecatalogue.ui.favorite.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.githubuser.moviecatalogue.data.source.Repository
import com.githubuser.moviecatalogue.data.source.local.entity.TvEntity

class FavoriteTvViewModel(private val repository: Repository) : ViewModel() {
    fun getFavoriteTv(): LiveData<PagedList<TvEntity>> = repository.getAllFavoriteTv()

    fun setFavoriteTV(tvEntity: TvEntity) {
        val newState = !tvEntity.isFavorite
        repository.setTvFavorite(tvEntity, newState)
    }
}