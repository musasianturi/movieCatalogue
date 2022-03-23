package com.githubuser.moviecatalogue.ui.favorite.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.githubuser.moviecatalogue.data.source.local.entity.TvEntity
import com.githubuser.moviecatalogue.databinding.ItemsTvshowBinding
import com.githubuser.moviecatalogue.ui.detail.tv.DetailTvShowActivity
import com.githubuser.moviecatalogue.ui.detail.tv.FavTvShowFragmentCallback
import com.githubuser.moviecatalogue.utils.ImageHelper

class FavoriteTvAdapter(private val callback: FavTvShowFragmentCallback) : PagedListAdapter<TvEntity, FavoriteTvAdapter.TvViewHolder>(DIFF_CALLBACK){


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvViewHolder {
        val itemsTvShowBinding = ItemsTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(itemsTvShowBinding)
    }

    override fun onBindViewHolder(holder: FavoriteTvAdapter.TvViewHolder, position: Int) {
        val tv = getItem(position)
        if (tv != null){
            holder.bind(tv)
        }
    }


    fun getSwipedData(swipedPosition: Int): TvEntity? = getItem(swipedPosition)


    inner class TvViewHolder(private val binding: ItemsTvshowBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(tv: TvEntity){
            with(binding) {
                tvTitle.text = tv.name
                tvRelease.text = tv.firstAirDate
                tvDesc.text = tv.overview

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailTvShowActivity::class.java)
                    intent.putExtra(DetailTvShowActivity.EXTRA_TV, tv.id)
                    itemView.context.startActivity(intent)
                }
                icShare.setOnClickListener { callback.onShareClick(tv) }

                ImageHelper.loadImage(itemView.context,tv.poster_path,imgPoster)
            }
        }
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvEntity>(){
            override fun areItemsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}