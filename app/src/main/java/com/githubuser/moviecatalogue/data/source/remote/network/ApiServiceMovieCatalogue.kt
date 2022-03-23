package com.githubuser.moviecatalogue.data.source.remote.network

import com.githubuser.moviecatalogue.data.source.remote.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceMovieCatalogue {

    @GET("movie/now_playing")
    fun getListMovie(
        @Query("api_key") apikey: String = ApiConfigMovieCatalogue.apikey
    ): Call<Response<MovieResponse>>

    @GET("tv/on_the_air")
    fun getListTv(
        @Query("api_key") apikey: String = ApiConfigMovieCatalogue.apikey
    ): Call<Response<TvResponse>>

    @GET("tv/{tv_id}")
    fun getTV(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apikey: String = ApiConfigMovieCatalogue.apikey
    ): Call<DetailTvResponse>

    @GET("movie/{movie_id}")
    fun getMovie(
        @Path("movie_id") tvId: Int,
        @Query("api_key") apikey: String = ApiConfigMovieCatalogue.apikey
    ): Call<DetailMovieResponse>
}