package com.example.parkit2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.parkit2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var navMenuController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val navHostFragment = supportFragmentManager. findFragmentById(R.id.navHost) as NavHostFragment
        navMenuController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this,navMenuController,binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.navView,navMenuController)
    }


    override fun onSupportNavigateUp() = super.onSupportNavigateUp() || NavigationUI.navigateUp(navMenuController,binding.drawerLayout)



}