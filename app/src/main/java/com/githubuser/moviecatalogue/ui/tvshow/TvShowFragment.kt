package com.githubuser.moviecatalogue.ui.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.githubuser.moviecatalogue.R
import com.githubuser.moviecatalogue.data.source.local.entity.TvEntity
import com.githubuser.moviecatalogue.databinding.FragmentTvShowBinding
import com.githubuser.moviecatalogue.viewmodel.ViewModelFactory
import com.githubuser.moviecatalogue.vo.Status


class TvShowFragment : Fragment(), TvShowFragmentCallback {

  private    var _fragmentTvShowBinding: FragmentTvShowBinding? = null
    private val fragmentTvShowBinding get() = _fragmentTvShowBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentTvShowBinding = FragmentTvShowBinding.inflate(inflater, container, false)
        return fragmentTvShowBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]
            //val tv = viewModel.getTv()
            val adapter = TvShowAdapter(this)

            fragmentTvShowBinding?.progressBar?.visibility = View.VISIBLE
            viewModel.getTv().observe(viewLifecycleOwner, { tv ->
                when (tv.status) {
                    Status.LOADING -> fragmentTvShowBinding?.progressBar?.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        fragmentTvShowBinding?.progressBar?.visibility = View.GONE
                        adapter.submitList(tv.data)
                        adapter.notifyDataSetChanged()

                    }
                    Status.ERROR -> { fragmentTvShowBinding?.progressBar?.visibility = View.GONE
                        fragmentTvShowBinding?.lotteTv?.visibility = View.VISIBLE
                    Toast.makeText(context, "Terjadi Salah Paham", Toast.LENGTH_SHORT).show()

                }


                }
            })


            fragmentTvShowBinding?.let {
                with(it.rvTv) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    this.adapter = adapter
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

    override fun onDestroy() {
        super.onDestroy()
        _fragmentTvShowBinding = null
    }


}