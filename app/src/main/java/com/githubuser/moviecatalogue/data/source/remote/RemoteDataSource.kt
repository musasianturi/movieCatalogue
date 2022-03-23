package com.githubuser.moviecatalogue.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.githubuser.moviecatalogue.data.source.remote.network.ApiConfigMovieCatalogue
import com.githubuser.moviecatalogue.data.source.remote.network.ApiServiceMovieCatalogue
import com.githubuser.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.githubuser.moviecatalogue.data.source.remote.response.DetailTvResponse
import com.githubuser.moviecatalogue.data.source.remote.response.MovieResponse
import com.githubuser.moviecatalogue.data.source.remote.response.TvResponse
import com.githubuser.moviecatalogue.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.await
import java.io.IOException

class RemoteDataSource {


    private fun api(): ApiServiceMovieCatalogue = ApiConfigMovieCatalogue.getApiService()

    fun getListMovie(): LiveData<ApiResponse<List<MovieResponse>>> {


        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        CoroutineScope(IO).launch {
            try {
                api().getListMovie().await().result?.let {
                    resultMovie.postValue(ApiResponse.success(it))
                }

            } catch (e: IOException) {
                e.printStackTrace()
                resultMovie.postValue(ApiResponse.error(e.message.toString(), mutableListOf()))
            }

        }
        EspressoIdlingResource.decrement()

        return resultMovie

    }


    fun getListTv(): LiveData<ApiResponse<List<TvResponse>>> {
        EspressoIdlingResource.increment()
        val resultTv = MutableLiveData<ApiResponse<List<TvResponse>>>()
        CoroutineScope(IO).launch {
            try {
                api().getListTv().await().result?.let {
                    resultTv.postValue(ApiResponse.success(it))

                }

            } catch (e: IOException) {
                e.printStackTrace()
                resultTv.postValue(ApiResponse.error(e.message.toString(), mutableListOf()))

            }

        }
        EspressoIdlingResource.decrement()


        return resultTv
    }


    fun getDetailMovie(id: Int): LiveData<ApiResponse<DetailMovieResponse>> {
        EspressoIdlingResource.increment()
        val resultDetailMovie = MutableLiveData<ApiResponse<DetailMovieResponse>>()
        CoroutineScope(IO).launch {
            api().getMovie(id).await().let {
                resultDetailMovie.postValue(ApiResponse.success(it))

            }

        }
        EspressoIdlingResource.decrement()

        return resultDetailMovie

    }

    fun getDetailTv(id: Int): LiveData<ApiResponse<DetailTvResponse>> {
        EspressoIdlingResource.increment()
        val resultDetailTv = MutableLiveData<ApiResponse<DetailTvResponse>>()
        CoroutineScope(IO).launch {
            api().getTV(id).await().let {
                resultDetailTv.postValue(ApiResponse.success(it))
            }

        }
        EspressoIdlingResource.decrement()
        return resultDetailTv
    }

    companion object {
        @Volatile

        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }


}