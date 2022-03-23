package com.githubuser.moviecatalogue.ui.tvshow


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.githubuser.moviecatalogue.data.source.Repository
import com.githubuser.moviecatalogue.data.source.local.entity.TvEntity
import com.githubuser.moviecatalogue.vo.Resource

class TvShowViewModel(private val repository: Repository) : ViewModel() {
    fun getTv(): LiveData<Resource<PagedList<TvEntity>>> = repository.getAllTv()
}