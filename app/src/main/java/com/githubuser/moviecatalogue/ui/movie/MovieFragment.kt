package com.githubuser.moviecatalogue.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.githubuser.moviecatalogue.databinding.FragmentMovieBinding
import com.githubuser.moviecatalogue.viewmodel.ViewModelFactory
import com.githubuser.moviecatalogue.vo.Status

class MovieFragment : Fragment() {

    private  var _fragmentMovieBinding: FragmentMovieBinding? = null
    private val fragmentMovieBinding get() = _fragmentMovieBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)

        return fragmentMovieBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
            val movieAdapter = MovieAdapter()

            fragmentMovieBinding?.progressBar1?.visibility = View.VISIBLE
            viewModel.getMovie().observe(viewLifecycleOwner) { movies ->

                when (movies.status) {
                    Status.LOADING -> fragmentMovieBinding?.progressBar1?.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        fragmentMovieBinding?.progressBar1?.visibility = View.GONE
                        movieAdapter.submitList(movies.data)
                        movieAdapter.notifyDataSetChanged()

                    }
                    Status.ERROR -> {
                        fragmentMovieBinding?.progressBar1?.visibility = View.GONE
                        fragmentMovieBinding?.lotteMovie?.visibility = View.VISIBLE
                        Toast.makeText(context, "Terjadi Salah Paham", Toast.LENGTH_SHORT).show()
                    }

                }
            }

            fragmentMovieBinding?.let {
                with(it.rvMovie) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = movieAdapter
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentMovieBinding = null
    }
}