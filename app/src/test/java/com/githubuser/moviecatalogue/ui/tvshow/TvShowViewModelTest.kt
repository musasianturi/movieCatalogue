package com.githubuser.moviecatalogue.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.githubuser.moviecatalogue.data.source.Repository
import com.githubuser.moviecatalogue.data.source.local.entity.TvEntity
import com.githubuser.moviecatalogue.utils.DataDummy
import com.githubuser.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executors


@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {

    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvEntity>>>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(repository)
    }

    @Test
    fun getTv() {

        val dummyTv = PagedListTestDataSources.snapshot(DataDummy.generateDummyTvResponse())
        val expected = MutableLiveData<Resource<PagedList<TvEntity>>>()
        expected.value = Resource.success(dummyTv)

        `when`(repository.getAllTv()).thenReturn(expected)

        val tvEntities = viewModel.getTv().value?.data
        verify(repository).getAllTv()
        assertNotNull(tvEntities)
        assertEquals(6, tvEntities?.size)

        viewModel.getTv().observeForever(observer)
        verify(observer).onChanged(expected.value)
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