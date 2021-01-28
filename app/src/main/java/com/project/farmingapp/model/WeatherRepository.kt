package com.project.farmingapp.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.farmingapp.model.data.WeatherRootList
import com.project.farmingapp.viewmodel.WeatherListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {

    val data = MutableLiveData<WeatherRootList>()

    fun getWeather(): LiveData<String> {
        val response: Call<WeatherRootList> =
            WeatherApi.weatherInstances.getWeather("23.0225", "72.5714")

        val weathRes = MutableLiveData<String>()

        response.enqueue(object : Callback<WeatherRootList> {
            override fun onFailure(call: Call<WeatherRootList>, t: Throwable) {
                Log.d("WeatherRepository", "Error Occured")
            }

            override fun onResponse(
                call: Call<WeatherRootList>,
                response: Response<WeatherRootList>
            ) {
                if (response.isSuccessful) {
                    data.value = response.body()
                    weathRes.value = "DONE"
                } else {
                    weathRes.value = "FAILED"
                }
            }
        })
        return weathRes
    }
}