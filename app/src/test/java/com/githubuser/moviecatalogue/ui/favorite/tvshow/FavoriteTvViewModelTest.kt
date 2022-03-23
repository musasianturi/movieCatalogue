package com.githubuser.moviecatalogue.ui.favorite.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.githubuser.moviecatalogue.data.source.Repository
import com.githubuser.moviecatalogue.data.source.local.entity.TvEntity
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
class FavoriteTvViewModelTest {

    private lateinit var viewModel: FavoriteTvViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<PagedList<TvEntity>>

    @Before
    fun setUp() {
        viewModel = FavoriteTvViewModel(repository)
    }

    @Test
    fun testGetFavoriteTv() {
        val tv = PagedListTestDataSources.snapshot(DataDummy.generateDummyTvResponse())
        val expected = MutableLiveData<PagedList<TvEntity>>()
        expected.value = tv

        Mockito.`when`(repository.getAllFavoriteTv()).thenReturn(expected)

        val tvEntities = viewModel.getFavoriteTv().value

        verify(repository).getAllFavoriteTv()
        assertNotNull(tvEntities)
        assertEquals(6, tvEntities?.size)

        viewModel.getFavoriteTv().observeForever(observer)
        verify(observer).onChanged(expected.value)
    }

    @Test
    fun testSetFavoriteTV() {
        viewModel.setFavoriteTV(DataDummy.generateDummyDetailTvResponse())
        verify(repository).setTvFavorite(DataDummy.generateDummyDetailTvResponse(), true)
    }

    class PagedListTestDataSources private constructor(private val items: List<TvEntity>) :
        PositionalDataSource<TvEntity>() {
        override fun loadInitial(
            params: LoadInitialParams,
            callback: LoadInitialCallback<TvEntity>
        ) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<TvEntity>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }

        companion object {
            fun snapshot(items: List<TvEntity> = listOf()): PagedList<TvEntity> {
                return PagedList.Builder(PagedListTestDataSources(items), 5)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
            }
        }

    }
}