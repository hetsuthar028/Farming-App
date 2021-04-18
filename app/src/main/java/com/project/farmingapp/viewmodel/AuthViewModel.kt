package com.project.farmingapp.viewmodel

import android.content.Intent
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.project.farmingapp.R
import com.project.farmingapp.model.AuthRepository
import com.project.farmingapp.view.auth.SignupActivity

class AuthViewModel : ViewModel() {

    var name: String? = null
    var mobNo: String? = null
    var email: String? = null
    var city: String? = null
    var password: String? = null
    var confPassword: String? = null
    var userType: String? = "normal"
    var authListener: AuthListener? = null

    //login
    var loginmail:String?=null
    var loginpwd:String?=null

    lateinit var authRepository: AuthRepository
    lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

    fun signupButtonClicked(view: View) {
        authListener!!.onStarted()
        if (name.isNullOrEmpty() || mobNo.toString().length != 10 || mobNo == null || password.isNullOrEmpty() || confPassword.isNullOrEmpty() || city.isNullOrEmpty()) {
            // Failure
            authListener!!.onFailure("Error Occurred")
            return
        }
        // Success
        var data = hashMapOf(
            "name" to name,
            "mobNo" to mobNo,
            "email" to email,
            "city" to city,
            "userType" to userType
        )
        val authRepo = AuthRepository().signInWithEmail(email!!, password!!, data)
        authListener?.onSuccess(authRepo)
    }

    fun returnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        authListener!!.onStarted()
        var data2 = hashMapOf(
            "userType" to userType
        )
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    authRepository = AuthRepository()
                    var returned = authRepository.signInToGoogle(
                        account.idToken!!,
                        account.email.toString(),
                        data2
                    )
                    Log.d("AuthView", returned.value.toString())
                    authListener?.onSuccess(returned)
                } catch (e: ApiException) {
                    authListener!!.onFailure(e.message.toString())
                }
            } else {
            }
        }
    }


    //login btn function
    fun loginButtonClicked(view: View) {
        authListener!!.onStarted()
        if (loginmail.isNullOrEmpty() || loginpwd.isNullOrEmpty()) {
            // Failure
            authListener!!.onFailure("Error Occurred")
            return
        }
        // Success

        val authRepo = AuthRepository().logInWithEmail(loginmail!!, loginpwd!!)
        authListener?.onSuccess(authRepo)
    }
}