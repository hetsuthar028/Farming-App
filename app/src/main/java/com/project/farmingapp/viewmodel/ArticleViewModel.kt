package com.project.farmingapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import java.io.File


class ArticleViewModel : ViewModel() {

    var message1 = MutableLiveData<HashMap<String, Any>>()
    var message2 = MutableLiveData<String>()
    var message3 = MutableLiveData<List<DocumentSnapshot>>()
    var articleListener: ArticleListener? = null
    private var todoLiveData: LiveData<HashMap<String, Any>>? = null
    lateinit var firebaseDb: FirebaseFirestore
    lateinit var firebaseStorage: FirebaseStorage
    fun getArticle(): MutableLiveData<HashMap<String, Any>> {
        Log.d("ArticleViewModelGet", message1.value.toString())
        return message1
    }

    fun getMyArticle(name: String) {

        firebaseStorage = FirebaseStorage.getInstance()
        firebaseDb = FirebaseFirestore.getInstance()

        Log.d("ArticleRepo1", "Ss")
        firebaseDb.collection("article_fruits").document("${name}")
            .get()
            .addOnSuccessListener {
                message1.value = it.data as HashMap<String, Any>?
                Log.d("ArticleViewModelDirect", message1.value.toString())
            }
            .addOnFailureListener {
                Log.d("ArticleRepo3", "ss")
            }
    }

    fun getAllArticles(name: String){
        firebaseDb = FirebaseFirestore.getInstance()
        firebaseDb.collection(name).get().addOnSuccessListener {
//            var message3 = MutableLiveData<DocumentSnapshot>()

            message3.value = it.documents

            Log.d("All articles", it.documents[1].data.toString())
        }
    }

    fun updateArticle(data: HashMap<String, Any>) {
        Log.d("ArticleViewModel", data.toString())
        message1.value = data
    }

}