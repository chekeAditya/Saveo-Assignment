package com.aditya.movieapp.ui.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aditya.movieapp.R
import com.aditya.movieapp.databinding.ItemLayoutBinding
import com.aditya.movieapp.extras.Constants
import com.aditya.movieapp.local.interfaces.OnCardClicked
import com.aditya.movieapp.local.responses.ResultModel
import com.bumptech.glide.Glide
import java.util.*

class NewMovieAdapter(val onCardClicked: OnCardClicked) :
    PagingDataAdapter<ResultModel, MovieHolder>(diffUtil) {

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val resultModel = getItem(position)
        if (resultModel != null) {
            holder.setData(resultModel)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val itemLayoutBinding: ItemLayoutBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_layout, parent, false
            )
        return MovieHolder(itemLayoutBinding, onCardClicked)

    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ResultModel>() {
            override fun areItemsTheSame(oldItem: ResultModel, newItem: ResultModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResultModel, newItem: ResultModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
