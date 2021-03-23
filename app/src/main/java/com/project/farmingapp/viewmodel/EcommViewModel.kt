package com.project.farmingapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.project.farmingapp.adapter.EcommerceAdapter
import kotlinx.android.synthetic.main.fragment_ecommerce.*

class EcommViewModel:ViewModel() {
    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseFireStore: FirebaseFirestore? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    var ecommLiveData = MutableLiveData<List<DocumentSnapshot>>()
    fun loadAllEcommItems(){

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFireStore = FirebaseFirestore.getInstance()



        firebaseFireStore!!.collection("products").get()
            .addOnSuccessListener {
                Log.d("ecommviewmodel", it.documents[0].getString("title").toString())
                ecommLiveData.value=it.documents
                Log.d("ecommviewmodel", it.documents.toString())

            }
            .addOnFailureListener {
Log.d("ecommviewmodel",it.message)
            }
    }

    fun loadSpecificTypeEcomItem(itemType: String){
        firebaseFireStore = FirebaseFirestore.getInstance()



        firebaseFireStore!!.collection("products")
            .whereEqualTo("type", itemType)
            .get()
            .addOnSuccessListener {
                Log.d("ecommviewmodel", it.documents[0].getString("title").toString())
                ecommLiveData.value=it.documents
                Log.d("ecommviewmodel", it.documents.toString())

            }
            .addOnFailureListener {
                Log.d("ecommviewmodel",it.message)
            }

    }
}