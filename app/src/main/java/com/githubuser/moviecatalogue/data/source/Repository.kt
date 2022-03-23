package com.githubuser.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.githubuser.moviecatalogue.data.NetworkBoundResource
import com.githubuser.moviecatalogue.data.source.local.LocalDataSource
import com.githubuser.moviecatalogue.data.source.local.entity.MovieEntity
import com.githubuser.moviecatalogue.data.source.local.entity.TvEntity
import com.githubuser.moviecatalogue.data.source.remote.ApiResponse
import com.githubuser.moviecatalogue.data.source.remote.RemoteDataSource
import com.githubuser.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.githubuser.moviecatalogue.data.source.remote.response.DetailTvResponse
import com.githubuser.moviecatalogue.data.source.remote.response.MovieResponse
import com.githubuser.moviecatalogue.data.source.remote.response.TvResponse
import com.githubuser.moviecatalogue.utils.AppExecutors
import com.githubuser.moviecatalogue.vo.Resource

class Repository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors) : Datasource {




    override fun getAllMovie(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors){

            public override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(5)
                    .setPageSize(5)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovie(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data != null

            public override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getListMovie()

            public override fun saveCallResult(movieResponse: List<MovieResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in movieResponse) {

                    val movie = MovieEntity(

                        id =  response.id,
                        overview =   response.overview,
                        release_date =  response.releaseDate,
                        title = response.title,
                        poster_path =  response.posterPath,
                        isFavorite = false
                    )
                    movieList.add(movie)


                }
                localDataSource.insertMovie(movieList)
            }
        }.asLiveData()

    }


    override fun getAllTv(): LiveData<Resource<PagedList<TvEntity>>> {
        return object : NetworkBoundResource<PagedList<TvEntity>, List<TvResponse>>(appExecutors){
            public override fun loadFromDB(): LiveData<PagedList<TvEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(5)
                    .setPageSize(5)
                    .build()

                return LivePagedListBuilder(localDataSource.getAllTv(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvEntity>?): Boolean =
                data != null

            public override fun createCall(): LiveData<ApiResponse<List<TvResponse>>> =
                remoteDataSource.getListTv()

            public override fun saveCallResult(data: List<TvResponse>) {
                val tvList = ArrayList<TvEntity>()

                for (response in  data){
                    val tv = TvEntity(
                        id = response.id,
                        firstAirDate = response.firstAirDate,
                        overview = response.overview,
                        name = response.name,
                        poster_path =  response.posterPath,
                        isFavorite = false)
                    tvList.add(tv)
                }
                localDataSource.insertTv(tvList)

            }

        }.asLiveData()



    }

    override fun getAllFavoriteMovie(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(5)
            .setPageSize(5)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovie(), config).build()
    }

    override fun getAllFavoriteTv(): LiveData<PagedList<TvEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(5)
            .setPageSize(5)
            .build()

        return LivePagedListBuilder(localDataSource.getFavoriteTv(), config).build()
    }

    override fun getDetailMovie(movie_id: Int): LiveData<Resource<MovieEntity>> {

        return object : NetworkBoundResource<MovieEntity, DetailMovieResponse> (appExecutors){
            override fun loadFromDB(): LiveData<MovieEntity> =
                localDataSource.getDetailMovie(movie_id)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data != null

            override fun createCall(): LiveData<ApiResponse<DetailMovieResponse>> =
                remoteDataSource.getDetailMovie(movie_id)

            override fun saveCallResult(data: DetailMovieResponse) {
                val state = localDataSource.getFavMovieValue(movie_id)
                val movie = MovieEntity(

                    id = data.id,
                    overview = data.overview,
                    release_date =  data.releaseDate,
                    title = data.title,
                    backdropPath = data.backdropPath,
                    popularity = data.popularity,
                    voteAverage = data.voteAverage,
                    runtime = data.runtime,
                    tagline = data.tagline,
                    poster_path = data.posterPath,
                    isFavorite = state
                )

                localDataSource.updateFavoriteMovie(movie, state)
            }

        }.asLiveData()

    }


    override fun getDetailTv(tv_id: Int): LiveData<Resource<TvEntity>> {

        return object : NetworkBoundResource<TvEntity, DetailTvResponse>(appExecutors){
            override fun loadFromDB(): LiveData<TvEntity> =
                localDataSource.getDetailTv(tv_id)

            override fun shouldFetch(data: TvEntity?): Boolean =
                data != null

            override fun createCall(): LiveData<ApiResponse<DetailTvResponse>> =
                remoteDataSource.getDetailTv(tv_id)

            override fun saveCallResult(data: DetailTvResponse) {
                val state = localDataSource.getFavTvValue(tv_id)

                val tv = TvEntity(
                    id =  data.id,
                    firstAirDate =  data.firstAirDate,
                    overview = data.overview,
                    name = data.name,
                    backdropPath = data.backdropPath,
                    popularity = data.popularity,
                    voteAverage = data.voteAverage,
                    poster_path = data.posterPath,
                    isFavorite = state)

                localDataSource.updateFavoriteTv(tv, state)

            }

        }.asLiveData()
    }

    override fun setTvFavorite(tv: TvEntity, state: Boolean) =
        appExecutors.diskIO().execute{localDataSource.updateFavoriteTv(tv, state)}

    override fun setMovieFavorite(movie: MovieEntity, state: Boolean) =
        appExecutors.diskIO().execute{localDataSource.updateFavoriteMovie(movie, state)}


    companion object {

        @Volatile
        private var instance: Repository? = null

        fun getInstance(remoteData: RemoteDataSource, localDataSource: LocalDataSource, appExecutors: AppExecutors): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(remoteData, localDataSource, appExecutors).apply { instance = this }
            }
    }

}