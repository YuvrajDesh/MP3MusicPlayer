package com.yuvrajdeshmukh.mp3musicplayer

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var bottomNavigationView : BottomNavigationView? = null
        var appBarConfiguration : AppBarConfiguration? = null
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       bottomNavigationView = findViewById(R.id.bottomNavigation)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController : NavController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(setOf(
                 R.id.dashboardFragment,
            R.id.favoritesFragment,
            R.id.settingsFragment,
            R.id.aboutFragment
        ))
        bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController,appBarConfiguration)

    }


}