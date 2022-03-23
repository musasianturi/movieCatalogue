package com.githubuser.moviecatalogue.di

import android.content.Context
import com.githubuser.moviecatalogue.data.source.Repository
import com.githubuser.moviecatalogue.data.source.local.LocalDataSource
import com.githubuser.moviecatalogue.data.source.local.room.MovieCatalogueDatabase
import com.githubuser.moviecatalogue.data.source.remote.RemoteDataSource
import com.githubuser.moviecatalogue.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context): Repository {

        val database = MovieCatalogueDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieCatalogueDao())
        val appExecutors = AppExecutors()
        return Repository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}