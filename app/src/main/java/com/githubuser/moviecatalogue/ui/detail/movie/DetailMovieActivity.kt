package com.githubuser.moviecatalogue.ui.detail.movie

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.githubuser.moviecatalogue.R
import com.githubuser.moviecatalogue.databinding.ActivityDetailMovieBinding
import com.githubuser.moviecatalogue.databinding.ContentDetailMovieBinding
import com.githubuser.moviecatalogue.utils.ImageHelper.loadImage
import com.githubuser.moviecatalogue.viewmodel.ViewModelFactory
import com.githubuser.moviecatalogue.vo.Status

class DetailMovieActivity : AppCompatActivity() {

   private var isFavorite: Boolean = false

    private lateinit var detailContentBinding: ContentDetailMovieBinding
    private lateinit var viewModel: DetailMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityDetailMovieBinding = ActivityDetailMovieBinding.inflate(layoutInflater)
        detailContentBinding = activityDetailMovieBinding.detailMovie

        setContentView(activityDetailMovieBinding.root)

        setSupportActionBar(activityDetailMovieBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val factory = ViewModelFactory.getInstance(this)
         viewModel = ViewModelProvider(this, factory)[DetailMovieViewModel::class.java]

        val extras = intent.extras


        if (extras != null) {
            val movieId = extras.getInt(EXTRA_MV)

            viewModel.setMovie(movieId)

            viewModel.getMovie().observe(this, { movie ->

                when(movie.status){
                    Status.LOADING -> detailContentBinding.progressBar.visibility = View.VISIBLE

                    Status.SUCCESS -> {
                        detailContentBinding.progressBar.visibility = View.GONE

                        movie.data?.let { moviedata ->

                            val state = moviedata.isFavorite

                            detailContentBinding.movieTitle.text = moviedata.title
                            val duration = moviedata.runtime.toDouble()
                            val hours = resources.getString(R.string.hours)


                            detailContentBinding.dateDuration.text =
                                ("${moviedata.release_date} \u25CF ${
                                    String.format(
                                        "%.2f",
                                        duration / 60
                                    )

                                } $hours")
                            detailContentBinding.Mtagline.text = moviedata.tagline
                            detailContentBinding.score.text = moviedata.voteAverage.toString()
                            detailContentBinding.title2.text = resources.getString(R.string.title2)
                            detailContentBinding.description.text = moviedata.overview

                            loadImage(this, moviedata.backdropPath, detailContentBinding.tvPoster)

                            isFavorite = state

                            updateItem()

                        }
                    }

                    Status.ERROR -> {
                        detailContentBinding.progressBar.visibility = View.GONE
                        detailContentBinding.lotteMovie.visibility = View.VISIBLE
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

            if (!isFavorite)

                it.findItem(R.id.action_add_favorite)?.setIcon(R.drawable.ic_baseline_favorite_24)
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_add_favorite -> {
                viewModel.setFavoriteMovie()
                false
            }
            else -> super.onOptionsItemSelected(item)
        }

    }


    private fun updateItem() {
        isFavorite = !isFavorite

       invalidateOptionsMenu()

    }


    companion object {
        const val EXTRA_MV = "extra_MV"
    }

}