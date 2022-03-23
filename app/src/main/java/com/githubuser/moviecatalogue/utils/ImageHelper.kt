package com.githubuser.moviecatalogue.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.githubuser.moviecatalogue.R

object ImageHelper {
    fun loadImage(context: Context, imageSource: String?, imageView: ImageView) {

        Glide.with(context)
            .load("https://image.tmdb.org/t/p/original$imageSource")
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(imageView)
    }

}