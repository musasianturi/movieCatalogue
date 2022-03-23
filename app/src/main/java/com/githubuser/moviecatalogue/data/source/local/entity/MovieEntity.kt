package com.githubuser.moviecatalogue.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "movieentities")

@Parcelize
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "overview")
    var overview: String = "",

    @ColumnInfo(name = "release_date")
    var release_date : String = "",

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "poster_path")
    var poster_path: String? = "",

    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String = "",

    @ColumnInfo(name = "popularity")
    var popularity: Double = 0.0,

    @ColumnInfo(name = "vote_average")
    var voteAverage: Double = 0.0,

    @ColumnInfo(name = "runtime")
    var runtime: Int = 0,

    @ColumnInfo(name = "tagline")
    var tagline: String = "",

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean


) : Parcelable
