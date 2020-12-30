package com.project.farmingapp.model

import android.content.Context
import android.provider.Settings.System.getString
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.project.farmingapp.R

class AuthRepository {

    lateinit var googleSignInClient: GoogleSignInClient
    val firebaseAuth = FirebaseAuth.getInstance()
    fun signInWithEmail(email: String, password: String): LiveData<String> {


        val data = MutableLiveData<String>()
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                data.value = "Success"
                Log.d("AuthRepo", it.result!!.additionalUserInfo.toString())
                Log.d("AuthRepo2", data.value)
            } else if (it.isCanceled) {
                data.value = "Failure"
            }

        }.addOnFailureListener {
            Log.d("AuthRepo", it.message)
            data.value = it.message
        }

        return data
    }

    fun signInGoogle(){

//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//        googleSignInClient = GoogleSignIn.getClient(this, gso)

    }

}