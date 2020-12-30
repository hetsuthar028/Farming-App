package com.project.farmingapp.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import com.project.farmingapp.model.AuthRepository

class AuthViewModel : ViewModel() {

    var name: String? = null
    var mobNo: String? = null
    var email: String? = null
    var city: String? = null
    var password: String? = null
    var confPassword: String? = null
    var userType:String? = "normal"
    var authListener: AuthListener? = null

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

    fun googleSignupButtonClicked(view: View) {
        //

//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//        googleSignInClient = GoogleSignIn.getClient(this, gso)


    }


}