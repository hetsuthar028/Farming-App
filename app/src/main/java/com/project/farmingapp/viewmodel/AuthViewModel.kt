package com.project.farmingapp.viewmodel

import android.view.View
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.project.farmingapp.R
import com.project.farmingapp.model.AuthRepository

class AuthViewModel : ViewModel() {

    var name: String? = null
    var mobNo: String? = null
    var email: String? = null
    var gender: String? = null
    var password: String? = null
    var confPassword: String? = null

    var authListener: AuthListener? = null

    fun signupButtonClicked(view: View) {
        authListener!!.onStarted()
        if (name.isNullOrEmpty() || mobNo.toString().length != 10 || mobNo == null || password.isNullOrEmpty() || confPassword.isNullOrEmpty() || gender.isNullOrEmpty()) {
            // Failure
            authListener!!.onFailure("Error Occurred")
            return
        }
        // Success
        val authRepo = AuthRepository().signInWithEmail(email!!, password!!)
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