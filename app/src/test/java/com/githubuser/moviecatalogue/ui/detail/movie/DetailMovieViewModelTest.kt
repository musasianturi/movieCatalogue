package com.githubuser.moviecatalogue.ui.detail.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.githubuser.moviecatalogue.data.source.Repository
import com.githubuser.moviecatalogue.data.source.local.entity.MovieEntity
import com.githubuser.moviecatalogue.utils.DataDummy
import com.githubuser.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)

class DetailMovieViewModelTest {

    @Mock
    private lateinit var viewModel: DetailMovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    private val dummyMovie = DataDummy.generateDummyDetailMovieResponse()
    private val dummyId = DataDummy.generateDummyMovieResponse()
    private val movieId = dummyId[0].id

    @Mock
    private lateinit var observer: Observer<Resource<MovieEntity>>


    @Before
    fun setup() {
        viewModel = DetailMovieViewModel(repository)
    }

    @Test
    fun getTv() {

        val expected = MutableLiveData<Resource<MovieEntity>>()
        expected.value = Resource.success(dummyMovie)

        `when`(repository.getDetailMovie(movieId)).thenReturn(expected)

        viewModel.setMovie(movieId)

        viewModel.getMovie().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getMovie().value

        assertEquals(expectedValue, actualValue)
    }


    @Test
    fun setFavoriteTv() {

        val expected = MutableLiveData<Resource<MovieEntity>>()
        expected.value = Resource.success(dummyMovie)
        `when`(repository.getDetailMovie(movieId)).thenReturn(expected)

        viewModel.setMovie(movieId)

        val favorite = expected.value?.data?.isFavorite
        val newState = favorite?.not()

        viewModel.setFavoriteMovie()

        if (newState != null) {
            verify(repository).setMovieFavorite(dummyMovie, newState)
        }

    }


}