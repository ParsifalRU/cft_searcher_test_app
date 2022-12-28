package com.example.bin_searcher_app.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.bin_searcher_app.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFragment()

        hideActionBar()

        setNavController()

    }

    private fun setFragment(){
        supportFragmentManager.beginTransaction().replace(R.id.fragment, MainFragment()).commit()
    }

    private fun setNavController(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    private fun hideActionBar(){
        supportActionBar?.hide()
    }


}