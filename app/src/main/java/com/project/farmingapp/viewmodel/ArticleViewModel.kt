package com.project.farmingapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.project.farmingapp.model.ArticleRepository
import com.project.farmingapp.model.data.WeatherRootList

class ArticleViewModel: ViewModel() {

    var message1 = MutableLiveData<HashMap<String, Any>>()


    fun getArticle(): MutableLiveData<HashMap<String, Any>> {
        Log.d("ArticleViewModelGet", message1.value.toString())
        return message1
    }

    fun getMyArticle(name: String){
        val data = ArticleRepository().getSpecificFruitArticle(name)
        Log.d("ArtViewModel", data.value.toString())
    }

    fun updateArticle(data: HashMap<String, Any>){
        Log.d("ArticleViewModel", data.toString())
        message1.value = data

    }

}