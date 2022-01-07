package com.aditya.movieapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
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
import com.aditya.movieapp.local.Status
import com.aditya.movieapp.local.interfaces.OnCardClicked
import com.aditya.movieapp.local.responses.ResultModel
import com.aditya.movieapp.ui.adapter.MovieAdapter
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
    lateinit var movieAdapter: MovieAdapter
    private var emptyList = emptyList<ResultModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        viewModel.getMovieResponse().observe(viewLifecycleOwner, Observer {
            it?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    movieAdapter.submitData(it)
                }
            }
        })

        viewModel.getResponseFromAPI(1).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.ERROR ->{
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }

                Status.SUCCESS ->{
                    emptyList = it.data?.resultModels as ArrayList<ResultModel>
                    val adaptor = NewMovieAdapter(emptyList,this)
                    homeBinding.rvTopMovies.adapter = adaptor
                }
            }
        })

    }

    private fun setAdapter() {
        movieAdapter = MovieAdapter(this)
        homeBinding.rvMovies.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }


    override fun onCardClicked(resultModel: ResultModel) {
        val bundle = Bundle()
        bundle.putSerializable("resultModel", resultModel)
        Navigation.findNavController(requireView())
            .navigate(R.id.action_homeFragment_to_movieDetailsFragment, bundle)
    }
}