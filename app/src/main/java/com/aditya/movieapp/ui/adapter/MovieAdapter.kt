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
import com.aditya.movieapp.databinding.ItemLayoutGirdBinding
import com.aditya.movieapp.extras.Constants
import com.aditya.movieapp.local.interfaces.OnCardClicked
import com.aditya.movieapp.local.responses.ResultModel
import com.bumptech.glide.Glide
import java.util.*

class MovieAdapter(val onCardClicked: OnCardClicked) :
    PagingDataAdapter<ResultModel, MovieHolder>(diffUtil) {

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val resultModel = getItem(position)
        if (resultModel != null) {
            holder.setData(resultModel)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val itemLayoutGirdBinding: ItemLayoutGirdBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_layout_gird, parent, false
            )
        return MovieHolder(itemLayoutGirdBinding, onCardClicked)

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

class MovieHolder(
    private val itemLayoutGirdBinding: ItemLayoutGirdBinding,
    val onCardClicked: OnCardClicked
) : RecyclerView.ViewHolder(itemLayoutGirdBinding.root) {

    fun setData(resultModel: ResultModel) {

        itemLayoutGirdBinding.apply {
            Glide.with(ivMovieCard).load(Constants.MOVIE_URL + resultModel.posterPath)
                .placeholder(ColorDrawable(getRandomColor()))
                .into(ivMovieCard)

            ivMovieCard.setOnClickListener {
                onCardClicked.onCardClicked(resultModel)
            }
        }

    }

    fun getRandomColor(): Int {
        val colours: MutableList<Int> = ArrayList()
        colours.add(Color.parseColor("#FED8A9"))
        colours.add(Color.parseColor("#C599D6"))
        colours.add(Color.parseColor("#78D6C6"))
        colours.add(Color.parseColor("#A6B8FF"))
        colours.add(Color.parseColor("#E5B9D2"))
        colours.add(Color.parseColor("#FFEABF"))
        colours.add(Color.parseColor("#CCBBE5"))
        colours.add(Color.parseColor("#BCE4FE"))
        colours.add(Color.parseColor("#DAF5A8"))
        colours.add(Color.parseColor("#FFA4B5"))
        colours.add(Color.parseColor("#92CED8"))
        colours.add(Color.parseColor("#DBCBA7"))
        val rand = Random()
        return colours[rand.nextInt(colours.size)]
    }
}
