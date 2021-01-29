package com.project.farmingapp.view.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.project.farmingapp.R
import com.project.farmingapp.model.data.Weather
import com.project.farmingapp.view.apmc.ApmcFragment
import com.project.farmingapp.view.weather.WeatherFragment
import com.squareup.picasso.Picasso
import com.squareup.picasso.PicassoProvider
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*
import retrofit2.Retrofit

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var dashboardFragment: dashboardFragment
    lateinit var weatherFragment: WeatherFragment
    lateinit var navController: NavController
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var blankFragment1: WeatherFragment
    lateinit var apmcFragment:ApmcFragment

    val firebaseAuth = FirebaseAuth.getInstance()

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

        val something = navView.getHeaderView(0);

        something.navbarUserName.text = firebaseAuth.currentUser!!.displayName
        something.navbarUserEmail.text = firebaseAuth.currentUser!!.email
//        something.navbarUserImage.setImageURI(firebaseAuth.currentUser!!.photoUrl)

        Log.d("UserPhoto", firebaseAuth.currentUser!!.photoUrl.toString())

        Toast.makeText(this, firebaseAuth.currentUser!!.displayName, Toast.LENGTH_LONG).show()
        var currentUser = firebaseAuth.currentUser!!.displayName
//        navbarUserName?.text = firebaseAuth.currentUser!!.displayName

//        drawerLayout.navbarUserName.text = firebaseAuth.currentUser!!.displayName

        Glide.with(this).load(firebaseAuth.currentUser!!.photoUrl).into(something.navbarUserImage)
//        Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);


    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.miItem4 -> {
                if (supportFragmentManager.findFragmentByTag("name") == null) {
                    apmcFragment = ApmcFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, apmcFragment, "name1")
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