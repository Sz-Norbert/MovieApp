    package com.nika.movieapp.fragment

    import android.content.Intent
    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.lifecycle.Observer
    import androidx.recyclerview.widget.GridLayoutManager
    import com.google.gson.Gson
    import com.nika.movieapp.MainActivity
    import com.nika.movieapp.activity.DetailsActivity
    import com.nika.movieapp.adapters.MovieAdapter
    import com.nika.movieapp.databinding.FragmentSearchBinding
    import com.nika.movieapp.viewModel.Mvvm


    class SearchFragment : Fragment() {
        private lateinit var bindig :FragmentSearchBinding
        private lateinit var viewModel:Mvvm
        private lateinit var searchRv:MovieAdapter


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            viewModel=(activity as MainActivity).viewModel
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            bindig=FragmentSearchBinding.inflate(layoutInflater)
            return bindig.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)









            observeSearchedMovie()
            bindig.imgSearch.setOnClickListener{
                searchMovies()
                prepareRv()
                onItemClick()

            }

        }

        private fun onItemClick() {

            searchRv.onItemClick = {
                val intent = Intent(activity, DetailsActivity::class.java)
                val bundle = Bundle().apply {

                    val json = Gson().toJson(it)
                    putString(HomeFragment.JSON_KEY, json)


                }
                intent.putExtras(bundle)
                startActivity(intent)
        }
            }

        private fun observeSearchedMovie() {

            viewModel.observeSearchedMovieLivedata().observe(viewLifecycleOwner, Observer {
                searchRv.differ.submitList(it)
            })
        }

        private fun searchMovies() {
            val searchQuery=bindig.editTextSearch.text.toString()
            if (searchQuery.isNotEmpty()){
                viewModel.searchMove(searchQuery)
            }
        }

        private fun prepareRv() {
            searchRv= MovieAdapter()
            bindig.rvSearchedMovies.apply {
                layoutManager=GridLayoutManager(context, 2, GridLayoutManager.VERTICAL , false)
                adapter=searchRv
            }
        }


    }