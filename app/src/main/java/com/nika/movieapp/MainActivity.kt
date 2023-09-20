package com.nika.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nika.movieapp.db.MovieDataBase
import com.nika.movieapp.viewModel.MovieViewModelFactory
import com.nika.movieapp.viewModel.Mvvm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation=findViewById<BottomNavigationView>(R.id.btn_nav)
        val navController=Navigation.findNavController(this,R.id.host_fragment)

        val viewModel by viewModels<Mvvm>()
        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }
}