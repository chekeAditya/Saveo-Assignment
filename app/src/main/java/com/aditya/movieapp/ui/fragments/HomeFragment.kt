package com.aditya.movieapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.movieapp.R
import com.aditya.movieapp.databinding.FragmentHomeBinding
import com.aditya.movieapp.local.interfaces.OnCardClicked
import com.aditya.movieapp.local.responses.ResultModel
import com.aditya.movieapp.ui.adapter.NewMovieAdapter
import com.aditya.movieapp.viewmodels.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), OnCardClicked {

    val viewModel: AppViewModel by viewModels()
    private lateinit var homeBinding: FragmentHomeBinding
    lateinit var movieAdapter: NewMovieAdapter
    lateinit var newMovieAdapter: NewMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeBinding.shimmerFrameLayout.startShimmerAnimation()
        setAdapter()
        setNewMoviesRecycler()
        viewModel.getMovieResponse().observe(viewLifecycleOwner, Observer {
            shimmerDisplay()
            it?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    movieAdapter.submitData(it)
                }
            }
        })

        viewModel.getMovieResponse().observe(viewLifecycleOwner, Observer {
            shimmerDisplay()
            it?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    newMovieAdapter.submitData(it)
                }
            }
        })
    }

    private fun setAdapter() {
        movieAdapter = NewMovieAdapter(this)
        homeBinding.rvMovies.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun setNewMoviesRecycler() {
        newMovieAdapter = NewMovieAdapter(this)
        homeBinding.rvTopMovies.apply {
            adapter = newMovieAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        }
    }

    override fun onCardClicked(resultModel: ResultModel, imageView: ImageView) {
        val bundle = Bundle()
        bundle.putSerializable("resultModel", resultModel)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            imageView,
            ViewCompat.getTransitionName(imageView)!!
        )
        Navigation.findNavController(requireView())
            .navigate(R.id.action_homeFragment_to_movieDetailsFragment, bundle)
    }

    private fun shimmerDisplay() {
        homeBinding.apply {
            shimmerFrameLayout.stopShimmerAnimation()
            shimmerFrameLayout.visibility = View.GONE
            rvMovies.visibility = View.VISIBLE
            rvTopMovies.visibility = View.VISIBLE
            tvNowShowing.visibility = View.VISIBLE
        }
    }
}