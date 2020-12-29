package com.project.farmingapp.viewmodel

import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.ViewUtils
import androidx.lifecycle.ViewModel

class AuthViewModel:  ViewModel() {

    var name:String? = null
    var mobNo: String? = null
    var email:String? = null
    var gender:String? = null
    var password:String? = null
    var confPassword: String? = null

    var authListener: AuthListener? = null


    fun signupButtonClicked(view: View){
        authListener!!.onStarted()
        if(name.isNullOrEmpty() || mobNo.toString().length != 10 || mobNo == null || password.isNullOrEmpty() || confPassword.isNullOrEmpty() || gender.isNullOrEmpty()){
            // Failure
            authListener!!.onFailure("Error Occurred")
            return
        }
        // Success
        authListener!!.onSuccess()
    }

    fun googleSignupButtonClicked(view: View){
        //

    }


}