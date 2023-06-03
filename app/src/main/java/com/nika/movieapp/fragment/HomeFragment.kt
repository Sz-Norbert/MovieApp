    package com.nika.movieapp.fragment

    import android.content.Intent
    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.fragment.app.Fragment
    import androidx.lifecycle.Observer
    import androidx.lifecycle.ViewModelProvider
    import androidx.navigation.fragment.findNavController
    import androidx.recyclerview.widget.GridLayoutManager
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.google.gson.Gson
    import com.nika.movieapp.R
    import com.nika.movieapp.activity.DetailsActivity
    import com.nika.movieapp.adapters.NowPlayingAdapter
    import com.nika.movieapp.adapters.PopularAdapter
    import com.nika.movieapp.adapters.TopRatedAdapter
    import com.nika.movieapp.adapters.UpcomeingAdapter

    import com.nika.movieapp.databinding.FragmentHomeBinding
    import com.nika.movieapp.db.MovieDataBase
    import com.nika.movieapp.viewModel.MovieViewModelFactory
    import com.nika.movieapp.viewModel.Mvvm


    class HomeFragment : Fragment() {

        private lateinit var binding:FragmentHomeBinding
        private lateinit var upcomeingAdapter: UpcomeingAdapter
        private lateinit var viewModel: Mvvm
        var nowPlayingAdapter: NowPlayingAdapter = NowPlayingAdapter()
        var popularAdapter=PopularAdapter()
        var topRatedAdapter=TopRatedAdapter()

        companion object {
             val API_KEY="4e3e46ef4fdbc2713633818eb1a9b97b"
             val IMAGE_BASE="https://image.tmdb.org/t/p/w500/"
            val Title_KEY="package com.nika.movieapp.fragment.titleKey"
            val RELASE_KEY="package com.nika.movieapp.fragment.relaseeKey"
            val OVERVIEW_KEY="package com.nika.movieapp.fragment.overviewKey"
            val BACKDROP_PATH="com.nika.movieapp.fragment.backdropPath"
            val MOVIE_ID="com.nika.movieapp.fragment.movieID"

            val JSON_KEY="om.nika.movieapp.fragment.jsonKey"

        }


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            val movieDataBase= MovieDataBase.getInstance(requireContext())
            val viewModelFactory= MovieViewModelFactory(movieDataBase)
            viewModel=ViewModelProvider(this,viewModelFactory)[Mvvm::class.java]


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

            viewModel.getUpcomeing()
            prepareUpcomeingRecyclerView()
            observeUpcomeingMovie()

            viewModel.getNowPlayingMovie()
            prepareNowPlayingRecyclerView()
            observeNowPlayingMovie()

            viewModel.getPopularMovie()
            preparePupularMovieRecyclerView()
            observePopularMovie()


            prepareTopRatedMoviesAdapter()
            observeTopRatedMovieLiveData()
            viewModel.getTopRatedMoveis()



            onItemCLick()

            binding.imgSearch.setOnClickListener{
                onSearchiconClick()
            }



        }

        private fun onSearchiconClick() {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        private fun observeTopRatedMovieLiveData() {

            viewModel.observeTopRatedMoviesLiveData().observe(viewLifecycleOwner, Observer {
                topRatedAdapter.setMovieList(it)
            })


            }


        private fun prepareTopRatedMoviesAdapter() {
            topRatedAdapter = TopRatedAdapter()
            binding.rvTopRated.apply {
                layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                adapter = topRatedAdapter
            }
        }

        private fun observePopularMovie() {
            viewModel.observePopularMovie().observe(viewLifecycleOwner, Observer {
                popularAdapter.setMovieList(it)
            })
        }

        private fun preparePupularMovieRecyclerView() {
            binding.rvPopular.apply {
                layoutManager=LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter=popularAdapter
            }
        }

        private fun prepareNowPlayingRecyclerView() {
            binding.rvNowPlaying.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL , false)
                adapter = nowPlayingAdapter
            }
        }

        private fun observeNowPlayingMovie() {
            viewModel.observeNowPlayingMovieLiveData().observe(viewLifecycleOwner, Observer {
                nowPlayingAdapter.setMovieList(it)
            })
        }




        private fun onItemCLick() {
            upcomeingAdapter.onItemClick = {
                val intent = Intent(activity, DetailsActivity::class.java)
                val bundle = Bundle().apply {

                    val json =Gson().toJson(it)
                    putString(JSON_KEY, json)


                }
                intent.putExtras(bundle)
                startActivity(intent)
            }

                nowPlayingAdapter.onItemClick={
                val intent=Intent(activity, DetailsActivity::class.java)
                val bundle=Bundle().apply {
                    val json =Gson().toJson(it)
                    putString(JSON_KEY, json)

                }
                intent.putExtras(bundle)
                startActivity(intent)
            }

            popularAdapter.onItemClick={
                val intent=Intent(activity, DetailsActivity::class.java)
                val bundle=Bundle().apply {
                    val json =Gson().toJson(it)
                    putString(JSON_KEY, json)

                }
                intent.putExtras(bundle)
                startActivity(intent)
            }


            topRatedAdapter.onItemClick={
                val intent=Intent(activity, DetailsActivity::class.java)
                val bundle=Bundle().apply {
                    val json =Gson().toJson(it)
                    putString(JSON_KEY, json)

                }
                intent.putExtras(bundle)
                startActivity(intent)
            }

        }

        private fun observeUpcomeingMovie() {
            viewModel.observeUpcomeingMoveLiveData().observe(viewLifecycleOwner, Observer {
                upcomeingAdapter.setMovieList(it)
            })
        }

        private fun prepareUpcomeingRecyclerView() {

            upcomeingAdapter= UpcomeingAdapter()
            binding.rvUppToCome.apply {
                layoutManager=LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter=upcomeingAdapter
            }


        }


    }