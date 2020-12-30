package com.project.farmingapp.model

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository {

    lateinit var googleSignInClient: GoogleSignInClient
    val firebaseAuth = FirebaseAuth.getInstance()
    lateinit var firebaseDb: FirebaseFirestore
    fun signInWithEmail(email: String, password: String, otherData: HashMap<String, String?>): LiveData<String> {

        firebaseDb = FirebaseFirestore.getInstance()
        val data = MutableLiveData<String>()
        val data2 = MutableLiveData<String>()
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {

//                Log.d("AuthRepo", it.result!!.additionalUserInfo.toString())
//                Log.d("AuthRepo2", data.value)

                firebaseDb!!.collection("users").document("${email}")
                    .set(otherData)
                    .addOnSuccessListener {
                        data.value = "Success"
//                        Toast.makeText(this, "Data added", Toast.LENGTH_SHORT).show()
//                        Intent(this, LoginActivity::class.java).also {
//                            startActivity(it)
//                        }
//                        data2.value = "Success"
                    }
                    .addOnFailureListener { Exception ->
                        {
                            data.value = "Failure"
//                            data2.value = "Failure"
//                            Toast.makeText(this, "Error: ${Exception}", Toast.LENGTH_SHORT).show()
                        }
                    }

            } else if (it.isCanceled) {

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