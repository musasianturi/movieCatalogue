package com.githubuser.moviecatalogue.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.githubuser.moviecatalogue.R
import com.githubuser.moviecatalogue.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(activityFavoriteBinding.root)

        val sectionPagerAdapter = FavoriteSectionPagerAdapter(this, supportFragmentManager)
        activityFavoriteBinding.favViewpager.adapter = sectionPagerAdapter
        activityFavoriteBinding.favTabs.setupWithViewPager(activityFavoriteBinding.favViewpager)

        supportActionBar?.elevation = 0f
        supportActionBar?.title = getString(R.string.fav_activity)

    }
}