package com.githubuser.moviecatalogue.ui.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.githubuser.moviecatalogue.data.source.Repository
import com.githubuser.moviecatalogue.data.source.local.entity.MovieEntity
import com.githubuser.moviecatalogue.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executors

@RunWith(MockitoJUnitRunner::class)

class FavoriteMovieViewModelTest {


    private lateinit var viewModel: FavoriteMovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<PagedList<MovieEntity>>

    @Before
    fun setUp() {
        viewModel = FavoriteMovieViewModel(repository)
    }

    @Test
    fun testGetFavoriteMovie() {

        val movie = PagedListTestDataSources.snapshot(DataDummy.generateDummyMovieResponse())
        val expected = MutableLiveData<PagedList<MovieEntity>>()
        expected.value = movie

        Mockito.`when`(repository.getAllFavoriteMovie()).thenReturn(expected)

        val movieEntities = viewModel.getFavoriteMovie().value

        verify(repository).getAllFavoriteMovie()
        assertNotNull(movieEntities)
        assertEquals(6, movieEntities?.size)

        viewModel.getFavoriteMovie().observeForever(observer)
        verify(observer).onChanged(expected.value)

    }

    @Test
    fun testSetFavoriteMovie() {
        viewModel.setFavoriteMovie(DataDummy.generateDummyDetailMovieResponse())
        verify(repository).setMovieFavorite(DataDummy.generateDummyDetailMovieResponse(), true)
    }

    class PagedListTestDataSources private constructor(private val items: List<MovieEntity>) :
        PositionalDataSource<MovieEntity>() {
        override fun loadInitial(
            params: LoadInitialParams,
            callback: LoadInitialCallback<MovieEntity>
        ) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MovieEntity>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }

        companion object {
            fun snapshot(items: List<MovieEntity> = listOf()): PagedList<MovieEntity> {
                return PagedList.Builder(PagedListTestDataSources(items), 5)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
            }
        }

    }
}