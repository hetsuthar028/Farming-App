package com.project.farmingapp.viewmodel

import android.util.Log
import android.widget.Toast
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

class EcommViewModel : ViewModel() {
    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    var ecommLiveData = MutableLiveData<List<DocumentSnapshot>>()
    var specificCategoryItems = MutableLiveData<List<DocumentSnapshot>>()
    var specificItem = MutableLiveData<DocumentSnapshot>()
    fun loadAllEcommItems(): MutableLiveData<List<DocumentSnapshot>> {

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFireStore = FirebaseFirestore.getInstance()

        firebaseFireStore!!.collection("products").get()
            .addOnSuccessListener {
                Log.d("ecommviewmodel", it.documents[0].getString("title").toString())
                ecommLiveData.value = it.documents
                Log.d("ecommviewmodel", it.documents.toString())

            }
            .addOnFailureListener {
                Log.d("ecommviewmodel", it.message)
            }
        return ecommLiveData
    }

    fun loadSpecificTypeEcomItem(itemType: String) {
        firebaseFireStore = FirebaseFirestore.getInstance()

        firebaseFireStore!!.collection("products")
            .whereEqualTo("type", itemType)
            .get()
            .addOnSuccessListener {
                Log.d("ecommviewmodel", it.documents[0].getString("title").toString())
                ecommLiveData.value = it.documents
                Log.d("ecommviewmodel", it.documents.toString())

            }
            .addOnFailureListener {
                Log.d("ecommviewmodel", it.message)
            }

    }

    fun getSpecificCategoryItems(itemType: String): MutableLiveData<List<DocumentSnapshot>> {
        firebaseFireStore.collection("products")
            .whereEqualTo("type", itemType)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    specificCategoryItems.value = it.result!!.documents
                    Log.d("EcommViewModel", it.result!!.documents.toString())
                }
            }
            .addOnFailureListener {
                Log.e("EcommViewModel", "Error Loading Specific Category Items")
            }
        return specificCategoryItems
    }

    fun getSpecificItem(itemID: String): MutableLiveData<DocumentSnapshot> {
        firebaseFireStore.collection("products")
            .document(itemID)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("EcommViewModel", it.result!!.data.toString())
                    specificItem.value = it.result
                } else {
                    Log.e("EcommViewModel", "Failed Getting Data")
                }
            }.addOnFailureListener {
                Log.e("EcommViewModel", "Failed Getting Data")
            }
        return specificItem
    }
}