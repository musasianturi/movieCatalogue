package com.githubuser.moviecatalogue.ui.favorite.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.githubuser.moviecatalogue.R
import com.githubuser.moviecatalogue.databinding.FragmentFavoriteMovieBinding
import com.githubuser.moviecatalogue.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar


class FavoriteMovieFragment : Fragment() {

    private  var _fragment: FragmentFavoriteMovieBinding? = null
    private val fragment get() = _fragment
    private lateinit var favMoveAdapter: FavoriteMovieAdapter
    private lateinit var viewModel: FavoriteMovieViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragment = FragmentFavoriteMovieBinding.inflate(inflater, container, false)

        return fragment?.root
    }

    override fun onViewCreated( view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(fragment?.rvFavMovie)

        if (activity != null){
            val factory = ViewModelFactory.getInstance(requireActivity())
             viewModel = ViewModelProvider(this, factory)[FavoriteMovieViewModel::class.java]
            favMoveAdapter = FavoriteMovieAdapter()

            fragment?.progressBar1?.visibility = View.VISIBLE
            viewModel.getFavoriteMovie().observe(viewLifecycleOwner, {favmovies ->

                if (favmovies.isNotEmpty()) {
                    fragment?.progressBar1?.visibility = View.GONE
                    fragment?.lotteFavMovie?.visibility = View.GONE
                    fragment?.rvFavMovie?.visibility = View.VISIBLE
                    favMoveAdapter.submitList(favmovies)
                    favMoveAdapter.notifyDataSetChanged()
                } else{
                    fragment?.rvFavMovie?.visibility = View.GONE
                    fragment?.progressBar1?.visibility = View.GONE
                    fragment?.lotteFavMovie?.visibility = View.VISIBLE
                }
            })

            fragment?.let {
                with(it.rvFavMovie) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = favMoveAdapter
                }
            }

        }
    }


    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback(){
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)



        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ): Boolean = true


        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null){
                val swipedPosition = viewHolder.absoluteAdapterPosition
                val movieEntity = favMoveAdapter.getSwipeData(swipedPosition)
                movieEntity?.let {viewModel.setFavoriteMovie(it)}

                    val snackbar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)

                    snackbar.setAction(R.string.undo) { _ ->
                        movieEntity?.let {
                            viewModel.setFavoriteMovie(it)
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