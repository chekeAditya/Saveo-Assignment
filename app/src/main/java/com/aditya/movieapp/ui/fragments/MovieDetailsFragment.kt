package com.aditya.movieapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.aditya.movieapp.R
import com.aditya.movieapp.databinding.FragmentMovieDetailsBinding
import com.aditya.movieapp.extras.Constants.MOVIE_URL
import com.aditya.movieapp.local.responses.ResultModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var detailsBinding: FragmentMovieDetailsBinding
    lateinit var resultModel: ResultModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)
        return detailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultModel = arguments?.getSerializable("resultModel") as ResultModel

        detailsBinding.apply {
            resultModel.apply {
                Glide.with(ivMovieThumbnail).load(MOVIE_URL + posterPath)
                    .into(ivMovieThumbnail)
                tvMovieName.text = originalTitle
                tvTime.text = releaseDate
                tvSynopsisData.text = overview
                starRatingBar.rating = voteAverage.toString().toFloat() / 2
                tvRatings.text = "Rating: ${voteAverage.toString()}"
                tvReview.text = "Popularity: ${popularity.toString()}"
                tvGenre1.text = "comedy"
                tvGenre2.text = "adventure"
                tvGenre3.text = "romance"
                vBack.setOnClickListener {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_movieDetailsFragment_to_homeFragment)
                }
            }
        }
    }
}