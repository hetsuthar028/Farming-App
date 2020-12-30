package com.project.farmingapp.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class AuthRepository {

    fun signInWithEmail(context: Context, email: String, password: String){
        val firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                Log.d("AuthRepo", it.result!!.additionalUserInfo.toString())
                Toast.makeText(context, it.result.toString(), Toast.LENGTH_LONG).show()
            } else if(it.isCanceled){
                Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            Log.d("AuthRepo", it.message)
            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
        }

    }

}