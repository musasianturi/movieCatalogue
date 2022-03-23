package com.githubuser.moviecatalogue.ui.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.githubuser.moviecatalogue.data.source.local.entity.TvEntity
import com.githubuser.moviecatalogue.databinding.ItemsTvshowBinding
import com.githubuser.moviecatalogue.ui.detail.tv.DetailTvShowActivity
import com.githubuser.moviecatalogue.utils.ImageHelper

class TvShowAdapter(private val callback: TvShowFragmentCallback) :
    PagedListAdapter<TvEntity, TvShowAdapter.TvShowViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemsTvShowBinding =
            ItemsTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemsTvShowBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tv = getItem(position)
        if (tv != null) {
            holder.bind(tv)
        }
    }


    inner class TvShowViewHolder(private val binding: ItemsTvshowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tv: TvEntity) {
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

    companion object {
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