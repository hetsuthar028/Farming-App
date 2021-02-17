package com.project.farmingapp.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.project.farmingapp.viewmodel.ArticleViewModel
import com.project.farmingapp.viewmodel.WeatherViewModel

class ArticleRepository {
    lateinit var firebaseDb: FirebaseFirestore
    var data = MutableLiveData<HashMap<String, Any>>()
    val data2 = MutableLiveData<String>()
    private lateinit var viewModel: ArticleViewModel
    fun getSpecificFruitArticle(name: String): LiveData<String> {

        firebaseDb = FirebaseFirestore.getInstance()
        Log.d("ArticleRepo1", "Ss")
        firebaseDb.collection("article_fruits").document("${name}")
            .get()
            .addOnSuccessListener {
//                var ss = it.data
                viewModel = ArticleViewModel()


//                data.value = it.data as HashMap<String, Any>?
                viewModel.updateArticle(it.data as HashMap<String, Any>)


//                data.value = it.data as HashMap<String, Any>?
                data2.value = "Success"
                Log.d("ArticleRepo2", data2.value.toString())

            }
            .addOnFailureListener {
                Log.d("ArticleRepo3", "ss")
            }
        return data2
    }
}