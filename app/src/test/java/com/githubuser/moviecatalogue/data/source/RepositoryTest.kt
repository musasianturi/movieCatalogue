package com.githubuser.moviecatalogue.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.githubuser.moviecatalogue.data.source.local.LocalDataSource
import com.githubuser.moviecatalogue.data.source.local.entity.MovieEntity
import com.githubuser.moviecatalogue.data.source.local.entity.TvEntity
import com.githubuser.moviecatalogue.data.source.remote.RemoteDataSource
import com.githubuser.moviecatalogue.utils.AppExecutors
import com.githubuser.moviecatalogue.utils.DataDummy
import com.githubuser.moviecatalogue.utils.LiveDataTestUtil
import com.githubuser.moviecatalogue.utils.PagedListTestUtil
import com.githubuser.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock


class RepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remoteDataSource = mock(RemoteDataSource::class.java)
    private val localDataSource = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val repository = FakeRepository(remoteDataSource, localDataSource, appExecutors)

    private val movieResponse = DataDummy.generateDummyMovieResponse()
    private val movieId = movieResponse[0].id
    private val tvResponse = DataDummy.generateDummyTvResponse()
    private val tvId = tvResponse[0].id
    private val detailMovieResponse = DataDummy.generateDummyDetailMovieResponse()
    private val detailTvResponse = DataDummy.generateDummyDetailTvResponse()


    @Test
    fun getAllMovie() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(localDataSource.getAllMovie()).thenReturn(dataSourceFactory)
        repository.getAllMovie()

        val movieEntities =
            Resource.success(PagedListTestUtil.mockPagedList(DataDummy.generateDummyMovieResponse()))
        verify(localDataSource).getAllMovie()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponse.size.toLong(), movieEntities.data?.size?.toLong())
    }


    @Test
    fun getFavoriteMovie() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(localDataSource.getFavoriteMovie()).thenReturn(dataSourceFactory)
        repository.getAllFavoriteMovie()

        val moveEntities = Resource.success(PagedListTestUtil.mockPagedList(movieResponse))
        verify(localDataSource).getFavoriteMovie()
        assertNotNull(moveEntities.data)
        assertEquals(movieResponse.size.toLong(), moveEntities.data?.size?.toLong())
    }

    @Test
    fun getAllTv() {
        val datasourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvEntity>
        `when`(localDataSource.getAllTv()).thenReturn(datasourceFactory)
        repository.getAllTv()

        val tvEntites =
            Resource.success(PagedListTestUtil.mockPagedList(DataDummy.generateDummyTvResponse()))
        verify(localDataSource).getAllTv()
        assertNotNull(tvEntites)
        assertEquals(tvResponse.size.toLong(), tvEntites.data?.size?.toLong())

    }

    @Test
    fun getFavoriteTv() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvEntity>
        `when`(localDataSource.getFavoriteTv()).thenReturn(dataSourceFactory)
        repository.getAllFavoriteTv()

        val tvEntities = Resource.success(PagedListTestUtil.mockPagedList(tvResponse))
        verify(localDataSource).getFavoriteTv()
        assertNotNull(tvEntities.data)
        assertEquals(tvResponse.size.toLong(), tvEntities.data?.size?.toLong())
    }


    @Test
    fun getDetailMovie() {

        val dummyEntity = MutableLiveData<MovieEntity>()
        dummyEntity.value = detailMovieResponse


        `when`(localDataSource.getDetailMovie(movieId)).thenReturn(dummyEntity)
        val movie = LiveDataTestUtil.getValue(repository.getDetailMovie(movieId))
        verify(localDataSource).getDetailMovie(movieId)
        assertNotNull(movie)
        assertEquals(detailMovieResponse.id, movie.data?.id)
        assertEquals(detailMovieResponse, movie.data)

    }

    @Test
    fun getDetailTV() {

        val dummyEntity = MutableLiveData<TvEntity>()
        dummyEntity.value = detailTvResponse

        `when`(localDataSource.getDetailTv(tvId)).thenReturn(dummyEntity)
        val tv = LiveDataTestUtil.getValue(repository.getDetailTv(tvId))
        verify(localDataSource).getDetailTv(tvId)
        assertNotNull(tv)
        assertEquals(detailTvResponse.id, tv.data?.id)
        assertEquals(detailTvResponse, tv.data)
    }

    @Test
    fun setTvFavorite() {
        val tv = detailTvResponse
        repository.setTvFavorite(tv, true)
        verify(localDataSource).updateFavoriteTv(tv, true)
    }

    @Test
    fun setMoveFavorite() {
        val movie = detailMovieResponse
        repository.setMovieFavorite(movie, true)
        verify(localDataSource).updateFavoriteMovie(movie, true)
    }

}