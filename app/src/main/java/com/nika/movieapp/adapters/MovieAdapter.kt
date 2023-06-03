package com.nika.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nika.movieapp.databinding.ItemsBinding
import com.nika.movieapp.fragment.HomeFragment
import com.nika.movieapp.pojo.Movie

class MovieAdapter():RecyclerView.Adapter<MovieAdapter.FavortiesViewHolder>() {

    inner class FavortiesViewHolder(val binding:ItemsBinding):RecyclerView.ViewHolder(binding.root)
    var onItemClick:((Movie) -> Unit)?=null


    val diffUtil=object :DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {

        return   oldItem.id ==newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem==newItem

        }

    }

    val differ=AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavortiesViewHolder {

        return FavortiesViewHolder(
            ItemsBinding.inflate(LayoutInflater.from(parent.context) , parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavortiesViewHolder, position: Int) {

        val movie=differ.currentList[position]
        movie.let {
        Glide.with(holder.itemView)
            .load(HomeFragment.IMAGE_BASE + movie.posterPath)
            .into(holder.binding.imgPoster)

        holder.binding.tvTitle.text=movie.title

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(movie)
        }

        }

    }

}