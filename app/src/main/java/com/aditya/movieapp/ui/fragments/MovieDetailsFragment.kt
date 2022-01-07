package com.aditya.movieapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
        Glide.with(detailsBinding.ivMovieThumbnail).load(MOVIE_URL + resultModel.posterPath)
            .into(detailsBinding.ivMovieThumbnail)
        detailsBinding.apply {
            tvMovieName.text = resultModel.originalTitle
            tvTime.text = resultModel.releaseDate
            tvStarRating.visibility = View.VISIBLE
            starRatingBar.visibility = View.VISIBLE
            tvSynopsisData.text = resultModel.overview
            starRatingBar.visibility = View.VISIBLE
            tvStarRating.text = resultModel.voteAverage.toString()
            starRatingBar.rating = resultModel.voteAverage.toString().toFloat() / 2
            tvRatings.text = "Rating: ${resultModel.voteAverage.toString()}"
            tvReview.text = "Popularity: ${resultModel.popularity.toString()}"
            tvGenre1.visibility = View.VISIBLE
            tvGenre1.text = "comedy"
            tvGenre2.visibility = View.VISIBLE
            tvGenre2.text = "adventure"
        }
    }
}