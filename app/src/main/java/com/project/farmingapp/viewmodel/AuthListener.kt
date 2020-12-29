package com.project.farmingapp.viewmodel

interface AuthListener {

    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)

}