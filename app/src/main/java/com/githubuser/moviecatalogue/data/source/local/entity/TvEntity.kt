package com.githubuser.moviecatalogue.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tventities")

@Parcelize
data class TvEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "firstAirDate")
    var firstAirDate: String = "",

    @ColumnInfo(name = "overview")
    var overview: String = "",

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "tagline")
    var tagline: String = "",

    @ColumnInfo(name = "poster_path")
    var poster_path: String = "",

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String? = "",

    @ColumnInfo(name = "popularity")
    val popularity: Double = 0.0,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double = 0.0,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
) : Parcelable
