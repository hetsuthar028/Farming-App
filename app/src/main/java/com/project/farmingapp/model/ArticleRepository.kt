package com.project.farmingapp.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.project.farmingapp.viewmodel.ArticleViewModel
import com.project.farmingapp.viewmodel.WeatherViewModel

class ArticleRepository {
    lateinit var firebaseDb: FirebaseFirestore

    private lateinit var viewModel: ArticleViewModel
    fun getSpecificFruitArticle(name: String): MutableLiveData<HashMap<String, Any>> {
        var data = MutableLiveData<HashMap<String, Any>>()
        firebaseDb = FirebaseFirestore.getInstance()

        firebaseDb.collection("article_fruits").document("${name}")
            .get()
            .addOnSuccessListener {
//                var ss = it.data
                viewModel = ArticleViewModel()


                data.value = it.data as HashMap<String, Any>?
                viewModel.updateArticle(it.data as HashMap<String, Any>)


                data.value = it.data as HashMap<String, Any>?
                Log.d("ArticleRepo", data.value.toString())
            }
            .addOnFailureListener {

            }
        return data
    }
}