package com.githubuser.moviecatalogue.ui.favorite.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.githubuser.moviecatalogue.data.source.local.entity.MovieEntity
import com.githubuser.moviecatalogue.databinding.ItemsMovieBinding
import com.githubuser.moviecatalogue.ui.detail.movie.DetailMovieActivity
import com.githubuser.moviecatalogue.utils.ImageHelper

class FavoriteMovieAdapter : PagedListAdapter<MovieEntity, FavoriteMovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val itemsMovieBinding = ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: FavoriteMovieAdapter.MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null){
            holder.bind(movie)
        }
    }

    fun getSwipeData(swipedPosition: Int): MovieEntity? = getItem(swipedPosition)

    inner class MovieViewHolder(private val binding: ItemsMovieBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(movie: MovieEntity){
            with(binding){
                tvTitle.text = movie.title
                tvRelease.text = movie.release_date
                tvDesc.text = movie.overview

                itemView.setOnClickListener{
                    val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_MV, movie.id)
                    itemView.context.startActivity(intent)
                }
                ImageHelper.loadImage(itemView.context, movie.poster_path, imgPoster)


            }
        }
    }



    companion object{
        private val DIFF_CALLBACK = object :DiffUtil.ItemCallback<MovieEntity>(){
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}