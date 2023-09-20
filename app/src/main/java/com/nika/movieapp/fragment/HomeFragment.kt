    package com.nika.movieapp.fragment

    import android.content.Intent
    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.activity.viewModels
    import androidx.fragment.app.Fragment
    import androidx.fragment.app.viewModels
    import androidx.lifecycle.Observer
    import androidx.lifecycle.ViewModelProvider
    import androidx.navigation.fragment.findNavController
    import androidx.recyclerview.widget.GridLayoutManager
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.google.gson.Gson
    import com.nika.movieapp.R
    import com.nika.movieapp.activity.DetailsActivity
    import com.nika.movieapp.adapters.*

    import com.nika.movieapp.databinding.FragmentHomeBinding
    import com.nika.movieapp.db.MovieDataBase
    import com.nika.movieapp.pojo.Movie
    import com.nika.movieapp.viewModel.MovieViewModelFactory
    import com.nika.movieapp.viewModel.Mvvm
    import dagger.hilt.android.AndroidEntryPoint
    import javax.inject.Inject


    @AndroidEntryPoint
    class HomeFragment : Fragment() {

        private lateinit var binding:FragmentHomeBinding
        private lateinit var upcomeingAdapter: BaseMovieAdapter
        private lateinit var nowPlayingAdapter : BaseMovieAdapter
        private lateinit var popularAdapter :BaseMovieAdapter
        private lateinit var topRatedAdapter :BaseMovieAdapter


        @Inject
        lateinit var viewModel: Mvvm

        companion object {
            const val API_KEY="4e3e46ef4fdbc2713633818eb1a9b97b"
             val IMAGE_BASE="https://image.tmdb.org/t/p/w500/"
            val JSON_KEY="om.nika.movieapp.fragment.jsonKey"
        }




        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            binding=FragmentHomeBinding.inflate(inflater , container , false )
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)


            viewModel.executeCall()
            prepareUpcomeingRecyclerView()
            observeUpcomeingMovie()
            prepareNowPlayingRecyclerView()
            observeNowPlayingMovie()
            preparePupularMovieRecyclerView()
            observePopularMovie()
            prepareTopRatedMoviesAdapter()
            observeTopRatedMovieLiveData()
            binding.imgSearch.setOnClickListener{
                onSearchiconClick()
            }

        }

        private fun onSearchiconClick() {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        private fun observeTopRatedMovieLiveData() {
            viewModel.topRatedMoveLiveData.observe(viewLifecycleOwner){
                topRatedAdapter.setMovieList(it)
            }

        }


        private fun prepareTopRatedMoviesAdapter() {
            topRatedAdapter = crateBaseAdpater()
            binding.rvTopRated.apply {
                layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                adapter = topRatedAdapter
            }
        }

        private fun observePopularMovie() {
           viewModel.popularMovieLiveData.observe(viewLifecycleOwner){
               popularAdapter.setMovieList(it)
           }
        }

        private fun preparePupularMovieRecyclerView() {
            popularAdapter=crateBaseAdpater()
            binding.rvPopular.apply {
                layoutManager=LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter=popularAdapter
            }
        }

        private fun prepareNowPlayingRecyclerView() {
            nowPlayingAdapter=crateBaseAdpater()
            binding.rvNowPlaying.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL , false)
                adapter = nowPlayingAdapter
            }
        }

        private fun observeNowPlayingMovie() {
            viewModel.nowPlayingLiveData.observe(viewLifecycleOwner){
                nowPlayingAdapter.setMovieList(it)
            }

        }

        private fun observeUpcomeingMovie() {
            viewModel.upComingLiveData.observe(viewLifecycleOwner, Observer {
                upcomeingAdapter.setMovieList(it)
            })
            }


        private fun prepareUpcomeingRecyclerView() {
            upcomeingAdapter= crateBaseAdpater()
            binding.rvUppToCome.apply {
                layoutManager=LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter=upcomeingAdapter
            }
        }
        fun crateBaseAdpater():BaseMovieAdapter {

            return object : BaseMovieAdapter() {
                override fun onMovieClick(movie: Movie) {

                    val intent = Intent(activity, DetailsActivity::class.java)
                    val bundle = Bundle().apply {

                        val json = Gson().toJson(movie)
                        putString(JSON_KEY, json)


                    }
                    intent.putExtras(bundle)
                    startActivity(intent)

                }


            }
        }
    }