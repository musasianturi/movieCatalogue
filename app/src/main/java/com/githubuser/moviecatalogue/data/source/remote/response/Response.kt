package com.githubuser.moviecatalogue.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class Response<T>(
    @SerializedName("results")
    var result: List<T>? = null
)