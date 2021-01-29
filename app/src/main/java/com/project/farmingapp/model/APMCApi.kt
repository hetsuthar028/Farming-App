package com.project.farmingapp.model

import com.project.farmingapp.model.data.WeatherRootList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.data.gov.in/resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd000001987c65666f9c49656f0f9ef4fa3650e7&format=json&offset=0&limit=400

const val BASE_URL2 ="https://api.data.gov.in/"
const val API_KEY2 ="63259e8886cbe4d575c24358fb860b1b"
interface apmcInterface {
    @GET("data/2.5/forecast?appid=$API_KEY")
    fun getWeather(@Query("lat")lat:String, @Query("lon")lon:String): Call<WeatherRootList>
}

object APMCApi {
    val weatherInstances:weatherInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherInstances =retrofit.create(weatherInterface::class.java)
    }
}