package com.githubuser.moviecatalogue.ui.favorite

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.githubuser.moviecatalogue.R
import com.githubuser.moviecatalogue.ui.favorite.movie.FavoriteMovieFragment
import com.githubuser.moviecatalogue.ui.favorite.tvshow.FavoriteTvFragment

class FavoriteSectionPagerAdapter(private val mContext: Context, fm: FragmentManager) :
FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment =
        when(position){
            0 -> FavoriteMovieFragment()
            1 -> FavoriteTvFragment()
            else -> Fragment()
        }

    override fun getPageTitle(position: Int): CharSequence  = mContext.resources.getString(
        TAB_TITLES[position]
    )

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.movie, R.string.tv_show)
    }
}