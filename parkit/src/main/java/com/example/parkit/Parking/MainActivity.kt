package com.example.parkit.Parking

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.example.parkit.R
import com.example.parkit.databinding.ActivityMainBinding


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



//        val navHostFragment2 = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
//        navController = navHostFragment2.navController


    }
    override fun onSupportNavigateUp() = super.onSupportNavigateUp() || NavigationUI.navigateUp(navMenuController,binding.drawerLayout)


}