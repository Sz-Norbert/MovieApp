package com.nika.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nika.movieapp.databinding.ItemsBinding
import com.nika.movieapp.fragment.HomeFragment
import com.nika.movieapp.pojo.Movie

class NowPlayingAdapter(): RecyclerView.Adapter<NowPlayingAdapter.NowPlayingViewHolder>() {

    private var movieList = listOf<Movie>()
    var onItemClick:((Movie) -> Unit)?=null

    fun setMovieList(movieList: List<Movie>) {
        this.movieList = movieList
        notifyDataSetChanged()
    }




    class NowPlayingViewHolder(val binding : ItemsBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {

        return NowPlayingViewHolder(ItemsBinding.inflate(LayoutInflater.from(parent.context) ,parent , false))
    }

    override fun getItemCount(): Int {


        return movieList.size

    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {



        val movie=movieList[position]
        movieList.let {
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