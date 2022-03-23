package com.githubuser.moviecatalogue.ui.detail.tv

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.githubuser.moviecatalogue.R
import com.githubuser.moviecatalogue.databinding.ActivityDetailTvShowBinding
import com.githubuser.moviecatalogue.databinding.ContentDetailTvShowBinding
import com.githubuser.moviecatalogue.utils.ImageHelper
import com.githubuser.moviecatalogue.viewmodel.ViewModelFactory
import com.githubuser.moviecatalogue.vo.Status

class DetailTvShowActivity : AppCompatActivity() {

   private var isFavorite: Boolean = false


    private lateinit var detailContentBinding: ContentDetailTvShowBinding
    private lateinit var viewModel: DetailTvShowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityDetailTvShowBinding = ActivityDetailTvShowBinding.inflate(layoutInflater)
        detailContentBinding = activityDetailTvShowBinding.detailTv

        setContentView(activityDetailTvShowBinding.root)

        setSupportActionBar(activityDetailTvShowBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailTvShowViewModel::class.java]

        val extras = intent.extras

        if (extras != null) {
            val tvId = extras.getInt(EXTRA_TV)
            viewModel.setTv(tvId)
            viewModel.getTv().observe(this, { tv ->

                when(tv.status){

                            Status.LOADING -> detailContentBinding.progressBar.visibility = View.VISIBLE

                    Status.SUCCESS ->{
                        detailContentBinding.progressBar.visibility = View.GONE

                        tv.data?.let { tvdata ->

                            val state = tvdata.isFavorite
                            if (tvdata.tagline.isEmpty()){
                                tvdata.tagline = "-"
                            }

                            detailContentBinding.TvTitle.text = tvdata.name
                            detailContentBinding.tagline.text = tvdata.tagline
                            detailContentBinding.dateDuration.text = tvdata.firstAirDate
                            detailContentBinding.Score.text = getString(R.string._score)
                            detailContentBinding.score.text = tvdata.voteAverage.toString()
                            detailContentBinding.title2.text = resources.getString(R.string.title2)
                            detailContentBinding.description.text = tvdata.overview

                            ImageHelper.loadImage(this, tvdata.backdropPath, detailContentBinding.tvPoster)

                            isFavorite = state

                            Log.d("statetv1", state.toString())

                            updateItem()
                        }
                    }

                    Status.ERROR -> {
                        detailContentBinding.progressBar.visibility = View.GONE
                        detailContentBinding.lotteTv.visibility = View.VISIBLE
                        Toast.makeText(this, "Terjadi Salah Paham", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        menu?.let {

            if (!isFavorite) {
                it.findItem(R.id.action_add_favorite)?.setIcon(R.drawable.ic_baseline_favorite_24)
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_add_favorite -> {
                viewModel.setFavoriteTv()
                false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateItem(){
        isFavorite = !isFavorite
        invalidateOptionsMenu()
    }


    companion object {
        const val EXTRA_TV = "extra_tv"
    }

}
