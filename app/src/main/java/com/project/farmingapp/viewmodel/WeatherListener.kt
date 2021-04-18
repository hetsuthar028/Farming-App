package com.project.farmingapp.viewmodel

import androidx.lifecycle.LiveData

interface WeatherListener {
    fun onSuccess(authRepo: LiveData<String>)
}