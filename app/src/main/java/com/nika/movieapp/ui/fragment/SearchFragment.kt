    package com.nika.movieapp.ui.fragment

    import android.content.Intent
    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Toast
    import androidx.fragment.app.viewModels
    import androidx.lifecycle.Observer
    import androidx.recyclerview.widget.GridLayoutManager
    import com.google.gson.Gson
    import com.nika.movieapp.ui.activity.DetailsActivity
    import com.nika.movieapp.adapters.MovieAdapter
    import com.nika.movieapp.databinding.FragmentSearchBinding
    import com.nika.movieapp.other.Resource
    import com.nika.movieapp.viewModel.Mvvm
    import dagger.hilt.android.AndroidEntryPoint
    import javax.inject.Inject

    @AndroidEntryPoint
    class SearchFragment : Fragment() {
        private lateinit var bindig :FragmentSearchBinding
        private lateinit var searchRv:MovieAdapter

        private val viewModel: Mvvm by viewModels()


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
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

            viewModel.searchMoveLiveData.observe(viewLifecycleOwner, Observer {
                if (it is Resource.Success){
                    val movieList= it.data?.movies ?: emptyList()
                    searchRv.differ.submitList(movieList)
                }else{
                    Toast.makeText(requireContext(), "Incorrect Movie name " , Toast.LENGTH_SHORT).show()
                }
            })
        }

        private fun searchMovies() {
            val searchQuery=bindig.editTextSearch.text.toString()
            if (searchQuery.isNotEmpty()){
                viewModel.getSearchMovie(searchQuery)
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