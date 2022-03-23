package com.githubuser.moviecatalogue.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailMovieResponse(

    @field:SerializedName("backdrop_path")
    val backdropPath: String,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("release_date")
    val releaseDate: String,

    @field:SerializedName("popularity")
    val popularity: Double,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("runtime")
    val runtime: Int,

    @field:SerializedName("tagline")
    val tagline: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String
)
