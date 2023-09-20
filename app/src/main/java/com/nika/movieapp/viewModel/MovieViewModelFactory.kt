package com.nika.movieapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nika.movieapp.db.MovieDataBase

class MovieViewModelFactory(
    val movieDataBase: MovieDataBase
):ViewModelProvider.Factory {

//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return Mvvm(movieDataBase) as T
//    }
}