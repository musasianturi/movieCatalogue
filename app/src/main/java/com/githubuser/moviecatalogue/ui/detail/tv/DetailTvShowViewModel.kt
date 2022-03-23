package com.githubuser.moviecatalogue.ui.detail.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.githubuser.moviecatalogue.data.source.Repository
import com.githubuser.moviecatalogue.data.source.local.entity.TvEntity
import com.githubuser.moviecatalogue.vo.Resource

class DetailTvShowViewModel(private val repository: Repository) : ViewModel() {


    private lateinit var tvLiveData: LiveData<Resource<TvEntity>>

    fun setTv(id: Int){
        tvLiveData = repository.getDetailTv(id)
    }

    fun getTv(): LiveData<Resource<TvEntity>> = tvLiveData

    fun setFavoriteTv(){
        val favorite = tvLiveData.value
        favorite?.let { Favorite ->
            Favorite.data?.let {
                val newState = !it.isFavorite
                repository.setTvFavorite(it, newState)
            }
        }
    }


}