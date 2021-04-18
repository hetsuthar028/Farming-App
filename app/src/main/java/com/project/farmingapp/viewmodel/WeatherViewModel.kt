package com.project.farmingapp.viewmodel

import androidx.lifecycle.*
import com.project.farmingapp.adapter.WeatherAdapter
import com.project.farmingapp.model.WeatherRepository
import com.project.farmingapp.model.data.WeatherRootList

class WeatherViewModel: ViewModel() {
    lateinit var Adapter: WeatherAdapter
    lateinit var rootData: MutableLiveData<WeatherRootList>
    var weatherListener: WeatherListener? = null


    val _rootData1 = MutableLiveData<WeatherRootList>()
    val rootData2: LiveData<WeatherRootList> = _rootData1

    fun callWeatherRepository(){
        val response = WeatherRepository().getWeather()
//        val ss = response.value!!.list[0].dt_txt
//        Log.d("ViewModel", ss)
        weatherListener?.onSuccess(response)
    }


}