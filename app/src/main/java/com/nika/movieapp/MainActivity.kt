package com.nika.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nika.movieapp.db.MovieDataBase
import com.nika.movieapp.viewModel.MovieViewModelFactory
import com.nika.movieapp.viewModel.Mvvm

class MainActivity : AppCompatActivity() {

    val viewModel: Mvvm by  lazy {
        val movieDataBase = MovieDataBase.getInstance(this)
        val viewModelProviderFactory= MovieViewModelFactory(movieDataBase)
        ViewModelProvider(this, viewModelProviderFactory)[Mvvm::class.java]

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation=findViewById<BottomNavigationView>(R.id.btn_nav)
        val navController=Navigation.findNavController(this,R.id.host_fragment)

        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }
}