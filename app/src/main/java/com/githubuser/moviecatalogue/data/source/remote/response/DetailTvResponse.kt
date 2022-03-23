package com.githubuser.moviecatalogue.data.source.remote.response

import com.google.gson.annotations.SerializedName


data class DetailTvResponse(

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = "",

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("first_air_date")
    val firstAirDate: String,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("popularity")
    val popularity: Double,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("tagline")
    val tagline: String
)
