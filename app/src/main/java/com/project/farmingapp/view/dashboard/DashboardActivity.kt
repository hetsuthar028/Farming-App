package com.project.farmingapp.view.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.farmingapp.R
import com.project.farmingapp.adapter.CurrentWeatherAdapter
import com.project.farmingapp.adapter.WeatherAdapter
import com.project.farmingapp.model.WeatherApi
import com.project.farmingapp.model.data.Weather
import com.project.farmingapp.model.data.WeatherList
import com.project.farmingapp.model.data.WeatherRootList
import com.project.farmingapp.view.apmc.ApmcFragment
import com.project.farmingapp.view.auth.LoginActivity
import com.project.farmingapp.view.weather.WeatherFragment
import com.squareup.picasso.Picasso
import com.squareup.picasso.PicassoProvider
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var dashboardFragment: dashboardFragment
    lateinit var weatherFragment: WeatherFragment
    lateinit var navController: NavController
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var blankFragment1: WeatherFragment
    lateinit var apmcFragment: ApmcFragment
    val firebaseFireStore = FirebaseFirestore.getInstance()
    val firebaseAuth = FirebaseAuth.getInstance()
    var userName = ""
    var data: WeatherRootList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        dashboardFragment = dashboardFragment()
        weatherFragment = WeatherFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, dashboardFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        val something = navView.getHeaderView(0);


        val googleLoggedUserName = firebaseAuth.currentUser!!.displayName
        if (googleLoggedUserName.isNullOrEmpty()) {
            firebaseFireStore.collection("users").document(firebaseAuth.currentUser!!.email!!)
                .get()
                .addOnCompleteListener {
                    val data = it.result
                    userName = data!!.getString("name").toString()
                    something.cityTextNavHeader.text = data!!.getString("city").toString()
                    something.navbarUserName.text = userName
                }
        } else {
            something.navbarUserName.text = googleLoggedUserName
        }
        something.navbarUserEmail.text = firebaseAuth.currentUser!!.email
        Glide.with(this).load(firebaseAuth.currentUser!!.photoUrl).into(something.navbarUserImage)

//        getWeather()
    }

//    fun getWeather() {
//        val response: Call<WeatherRootList> =
//            WeatherApi.weatherInstances.getWeather("23.0225", "72.5714")
//
//        var data: WeatherRootList? = null
//
//        response.enqueue(object : Callback<WeatherRootList> {
//            override fun onFailure(call: Call<WeatherRootList>, t: Throwable) {
//                Log.d("WeatherRepository", "Error Occured")
//            }
//
//            override fun onResponse(
//                call: Call<WeatherRootList>,
//                response: Response<WeatherRootList>
//            ) {
//                if (response.isSuccessful) {
//                    data = response.body()!!
//                    Log.d("Dashboard", data.toString())
//
//                }
//            }
//        })
//    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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
            R.id.miItem8 -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Log Out")
                    .setMessage("Do you want to logout?")
                    .setPositiveButton("Yes") { dialogInterface, i ->
                        firebaseAuth.signOut()
                        Toast.makeText(this, "Logged Out", Toast.LENGTH_LONG).show()
                        Intent(this, LoginActivity::class.java).also {
                            startActivity(it)
                        }
                    }
                    .setNegativeButton("No") { dialogInterface, i ->
                    }
                    .show()
            }
            R.id.miItem7 -> {

            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}