package com.githubuser.moviecatalogue.ui.tvshow

import com.githubuser.moviecatalogue.data.source.local.entity.TvEntity

interface TvShowFragmentCallback {
    fun onShareClick(tv: TvEntity)

}
