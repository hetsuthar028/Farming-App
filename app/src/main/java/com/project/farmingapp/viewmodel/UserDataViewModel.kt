package com.project.farmingapp.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.project.farmingapp.R
import com.project.farmingapp.view.user.UserFragment
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.nav_header.view.*

class UserDataViewModel : ViewModel() {

    var userliveData = MutableLiveData<DocumentSnapshot>()

    fun getUserData(userId: String) {
        val firebaseFireStore = FirebaseFirestore.getInstance()

        firebaseFireStore.collection("users").document(userId)
            .get()
            .addOnCompleteListener {
                userliveData.value = it.result
            }
    }

    fun updateUserField(context: Context, userID: String, about: String?, city: String?) {

        if (about !=null) {
            val firebaseFireStore = FirebaseFirestore.getInstance()
            firebaseFireStore.collection("users").document("${userID}")
                .update(
                    mapOf(
                        "about" to about
                    )
                )
                .addOnSuccessListener {
                    Log.d("UserDataViewModel", "User About Data Updated")
                    getUserData(userID)
                }
                .addOnFailureListener {
                    Log.d("UserDataViewModel", "Failed to Update About User Data")
                    Toast.makeText(context, "Failed to Update About. Try Again!", Toast.LENGTH_SHORT).show()
                }
        }

        if (city !=null) {
            val firebaseFireStore = FirebaseFirestore.getInstance()
            firebaseFireStore.collection("users").document("${userID}")
                .update(
                    mapOf(
                        "city" to city
                    )
                )
                .addOnSuccessListener {
                    Log.d("UserDataViewModel", "User City Data Updated")
                    getUserData(userID)
                }
                .addOnFailureListener {
                    Log.d("UserDataViewModel", "Failed to Update City User Data")
                    Toast.makeText(context, "Failed to Update City Try Again!", Toast.LENGTH_SHORT).show()
                }
        }
        Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show()
    }

    fun deleteUserPost(userId: String, postId: String){
        val firebaseFirestore = FirebaseFirestore.getInstance()

        firebaseFirestore.collection("posts").document(postId)
            .delete()
            .addOnSuccessListener {
                Log.d("User Data View Model", "Post Deleted")
                UserProfilePostsViewModel().getAllPosts(userId)

            }
            .addOnFailureListener {
                Log.d("User Data View Model", "Failed to delete post")
            }
    }
}