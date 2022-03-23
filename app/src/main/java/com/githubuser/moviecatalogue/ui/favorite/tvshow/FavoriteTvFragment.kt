package com.githubuser.moviecatalogue.ui.favorite.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.githubuser.moviecatalogue.R
import com.githubuser.moviecatalogue.data.source.local.entity.TvEntity
import com.githubuser.moviecatalogue.databinding.FragmentFavoriteTvBinding
import com.githubuser.moviecatalogue.ui.detail.tv.FavTvShowFragmentCallback
import com.githubuser.moviecatalogue.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar


class FavoriteTvFragment : Fragment(), FavTvShowFragmentCallback {


    private  var _fragment: FragmentFavoriteTvBinding ? = null
    private val fragment get() = _fragment
    private lateinit var favTvAdapter: FavoriteTvAdapter
    private lateinit var viewModel: FavoriteTvViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragment = FragmentFavoriteTvBinding.inflate(layoutInflater, container, false)
        return fragment?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(fragment?.rvFavTv)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavoriteTvViewModel::class.java]
             favTvAdapter = FavoriteTvAdapter(this)

            fragment?.progressBar?.visibility = View.VISIBLE
            viewModel.getFavoriteTv().observe(viewLifecycleOwner, { tv ->


                if (tv.isNotEmpty()) {
                    fragment?.progressBar?.visibility = View.GONE
                    fragment?.lotteFavTv?.visibility = View.GONE
                    fragment?.rvFavTv?.visibility = View.VISIBLE

                    favTvAdapter.submitList(tv)
                    favTvAdapter.notifyDataSetChanged()


                } else {
                    fragment?.rvFavTv?.visibility = View.GONE
                    fragment?.progressBar?.visibility = View.GONE
                    fragment?.lotteFavTv?.visibility = View.VISIBLE
                }
            })

            fragment?.let {
                with(it.rvFavTv) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    // this.adapter = adapter
                    adapter = favTvAdapter
                }
            }
        }
    }
    override fun onShareClick(tv: TvEntity) {
        if (activity != null) {
            val mimetype = "text/plain"
            ShareCompat.IntentBuilder
                .from(requireActivity())
                .setType(mimetype)
                .setChooserTitle("Share this app")
                .setText(resources.getString(R.string.share_text))
                .startChooser()
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback(){
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int  = makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null){
                val swipedPosition = viewHolder.absoluteAdapterPosition
                val tvEntity = favTvAdapter.getSwipedData(swipedPosition)
                tvEntity?.let { viewModel.setFavoriteTV(it) }

                val snackbar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)

                snackbar.setAction(R.string.undo) { _ ->
                    tvEntity?.let {
                        viewModel.setFavoriteTV(it)
                    }

                }
                snackbar.show()
            }
        }

    })

    override fun onDestroy() {
        super.onDestroy()
        _fragment = null
    }

}