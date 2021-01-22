package com.project.farmingapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.farmingapp.R
import com.project.farmingapp.adapter.WeatherAdapter
import com.project.farmingapp.model.WeatherApi
import com.project.farmingapp.model.data.WeatherRootList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel: ViewModel() {
    lateinit var Adapter: WeatherAdapter
    private fun getWeather() {
        val weather1: Call<WeatherRootList> =WeatherApi.weatherInstances.getWeather("27.2046","77.4977")
        weather1.enqueue(object: Callback<WeatherRootList> {
            override fun onFailure(call: Call<WeatherRootList>, t: Throwable) {
                Log.d("bharat","fail ho gya",t)



            }

            override fun onResponse(
                call: Call<WeatherRootList>,
                response: Response<WeatherRootList>
            ) {

                val rootdata:WeatherRootList? =response.body()
                if (rootdata!=null)
                {val RecyclerViewWE =R.id.rcylr_weather
                    Adapter= WeatherAdapter(this@MainActivity,rootdata.list)
                    RecyclerViewWE.adapter=Adapter
                   RecyclerViewWE.layoutManager= LinearLayoutManager(this@MainActivity)

                    //temp.text=rootdata.weather.main.toString()
                    Log.d("bharat",rootdata.toString())
                }
            }

        })
    }

}