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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.nika.movieapp.MainActivity
import com.nika.movieapp.activity.DetailsActivity

import com.nika.movieapp.adapters.MovieAdapter
import com.nika.movieapp.databinding.FragmentFavoritesBinding
import com.nika.movieapp.viewModel.Mvvm


class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: Mvvm
    private lateinit var rvAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<Mvvm>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorites()
        onItemClick()

        val itemTouchHelper=object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val mealList = rvAdapter.differ.currentList

                if (mealList.isNotEmpty() && position < mealList.size) {
                    val deletedMeal = mealList[position]
                    viewModel.deletMovie(deletedMeal)

                    Snackbar.make(requireView(), "Meal deleted", Snackbar.LENGTH_LONG).setAction(
                        "Undo"
                    ) {
                        viewModel.insertMovie(deletedMeal)
                    }.show()
                }
            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)
    }

    private fun prepareRecyclerView() {
        rvAdapter = MovieAdapter()
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = rvAdapter
        }

    }

    private fun observeFavorites() {
        viewModel.observeFavorties().observe(requireActivity(), Observer {
            rvAdapter.differ.submitList(it)

        })
    }

    private fun onItemClick() {

        rvAdapter.onItemClick = {
            val intent = Intent(activity, DetailsActivity::class.java)
            val bundle = Bundle().apply {

                val json = Gson().toJson(it)
                putString(HomeFragment.JSON_KEY, json)


            }
            intent.putExtras(bundle)
            startActivity(intent)
        }

    }
}