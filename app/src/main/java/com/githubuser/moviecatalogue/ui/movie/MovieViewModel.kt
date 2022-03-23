package com.githubuser.moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.githubuser.moviecatalogue.data.source.Repository
import com.githubuser.moviecatalogue.data.source.local.entity.MovieEntity
import com.githubuser.moviecatalogue.vo.Resource

class MovieViewModel(private val repository: Repository) : ViewModel() {

    fun getMovie(): LiveData<Resource<PagedList<MovieEntity>>> = repository.getAllMovie()
}
