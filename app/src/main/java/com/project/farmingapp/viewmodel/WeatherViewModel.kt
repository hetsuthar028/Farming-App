package com.project.farmingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.farmingapp.adapter.WeatherAdapter
import com.project.farmingapp.model.WeatherRepository
import com.project.farmingapp.model.data.Weather
import com.project.farmingapp.model.data.WeatherRootList

class WeatherViewModel : ViewModel() {
    lateinit var Adapter: WeatherAdapter
    lateinit var rootData: MutableLiveData<WeatherRootList>
    var weatherListener: WeatherListener? = null

    private var message1 = MutableLiveData<WeatherRootList>()
    private var message2 = MutableLiveData<WeatherRootList>()

    val _rootData1 = MutableLiveData<WeatherRootList>()
    val rootData2: LiveData<WeatherRootList> = _rootData1

    fun messageToB(msg: WeatherRootList){
        message2.value = msg
    }

    fun messageToA(msg: WeatherRootList){
        message1.value = msg
    }

    fun getMessageA(): MutableLiveData<WeatherRootList> {
        return message1
    }

    fun getMessageB(): MutableLiveData<WeatherRootList>{
        return message2
    }

    fun callWeatherRepository() {
        val response = WeatherRepository().getWeather()
//        val ss = response.value!!.list[0].dt_txt
//        Log.d("ViewModel", ss)
        weatherListener?.onSuccess(response)
    }
}