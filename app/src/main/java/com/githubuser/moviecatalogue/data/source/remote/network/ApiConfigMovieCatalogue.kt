package com.githubuser.moviecatalogue.data.source.remote.network

import com.githubuser.moviecatalogue.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfigMovieCatalogue {

    companion object {
       const val apikey = BuildConfig.ApiKey

        fun getApiService(): ApiServiceMovieCatalogue {

            if(BuildConfig.DEBUG) {
                   val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                 OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
            }

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().build())
                .build()
            return retrofit.create(ApiServiceMovieCatalogue::class.java)
        }
    }
}