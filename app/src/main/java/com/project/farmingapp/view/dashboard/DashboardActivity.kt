package com.project.farmingapp.view.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import com.google.android.material.navigation.NavigationView
import com.project.farmingapp.R
import com.project.farmingapp.model.data.Weather
import com.project.farmingapp.view.weather.WeatherFragment
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var dashboardFragment: dashboardFragment
    lateinit var weatherFragment: WeatherFragment
    lateinit var navController: NavController
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var blankFragment1: WeatherFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        dashboardFragment=dashboardFragment()
        weatherFragment = WeatherFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, dashboardFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.miItem1 -> {
                if (supportFragmentManager.findFragmentByTag("name") == null) {
                    blankFragment1 = WeatherFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, blankFragment1, "name1")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit()
                }
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else {
            super.onBackPressed()
        }
    }
}