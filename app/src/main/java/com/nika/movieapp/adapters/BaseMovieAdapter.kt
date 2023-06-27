package com.nika.movieapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.nika.movieapp.databinding.ItemsBinding
import com.nika.movieapp.fragment.HomeFragment
import com.nika.movieapp.pojo.Movie

abstract class BaseMovieAdapter() : RecyclerView.Adapter<BaseMovieViewHolder>() {
    var items: List<Movie> = mutableListOf()
    abstract fun onMovieClick(movie:Movie)


    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseMovieViewHolder {
        return BaseMovieViewHolder(
            ItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setMovieList(movieList: List<Movie>) {
        this.items = movieList
        notifyDataSetChanged()
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: BaseMovieViewHolder, position: Int) {
        val movie = items[position]
        Glide.with(holder.itemView)
            .load(HomeFragment.IMAGE_BASE + movie.posterPath)
            .into(holder.binding.imgPoster)

        holder.binding.tvTitle.text = movie.title

        holder.itemView.setOnClickListener {
            onMovieClick(movie)
        }
    }
}


class BaseMovieViewHolder(val binding: ItemsBinding) : ViewHolder(binding.root)