package com.githubuser.moviecatalogue.ui.detail.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.githubuser.moviecatalogue.data.source.Repository
import com.githubuser.moviecatalogue.data.source.local.entity.TvEntity
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
class DetailTvShowViewModelTest {

    @Mock
    private lateinit var viewModel: DetailTvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    private val dummyTv = DataDummy.generateDummyDetailTvResponse()
    private val dummyId = DataDummy.generateDummyTvResponse()
    private val tvId = dummyId[0].id

    @Mock
    private lateinit var observer: Observer<Resource<TvEntity>>


    @Before
    fun setup() {
        viewModel = DetailTvShowViewModel(repository)
    }


    @Test
    fun getTv() {

        val expected = MutableLiveData<Resource<TvEntity>>()
        expected.value = Resource.success(dummyTv)

        `when`(repository.getDetailTv(tvId)).thenReturn(expected)

        viewModel.setTv(tvId)

        viewModel.getTv().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getTv().value

        assertEquals(expectedValue, actualValue)
    }


    @Test
    fun setFavoriteTv() {

        val expected = MutableLiveData<Resource<TvEntity>>()
        expected.value = Resource.success(dummyTv)

        `when`(repository.getDetailTv(tvId)).thenReturn(expected)

        viewModel.setTv(tvId)

        val favorite = expected.value?.data?.isFavorite
        val newState = favorite?.not()

        viewModel.setFavoriteTv()

        if (newState != null) {
            verify(repository).setTvFavorite(dummyTv, newState)
        }

    }
}