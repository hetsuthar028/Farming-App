package com.project.farmingapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.project.farmingapp.model.data.Post

lateinit var userProfilePostsLiveData : MutableLiveData<List<DocumentSnapshot>>
class UserProfilePostsViewModel : ViewModel() {

    val userProfilePostsLiveData = MutableLiveData<ArrayList<HashMap<String, Any>>>()
    val userProfilePostsLiveData2 = MutableLiveData<List<String>>()

    val liveData1 = MutableLiveData<List<String>>()
    val liveData2 = MutableLiveData<ArrayList<DocumentSnapshot>>()
    val liveData3 = MutableLiveData<ArrayList<DocumentSnapshot>>()


    fun getUserPosts(userID: String?) {
        val firebaseFirestore = FirebaseFirestore.getInstance()
        val firebaseFirestore2 = FirebaseFirestore.getInstance()

        firebaseFirestore.collection("users").document("${userID}")
            .get()
            .addOnSuccessListener {
                val allPostsIDs: List<String>? = it.get("posts") as List<String>

                userProfilePostsLiveData2.value = allPostsIDs
                Log.d("User All Posts 3", userProfilePostsLiveData2.value.toString())

                var localPostList : ArrayList<HashMap<String, Any>>?= null

                for (i in allPostsIDs!!) {
                    firebaseFirestore2.collection("posts").document(i)
                        .get()
                        .addOnSuccessListener {
                            Log.d("User All Posts", it.data.toString())
//                            localPostList?.add(it)


//                            var localData = it.data


                            val someData = it.data as HashMap<String, Any>
                            localPostList?.add(someData)
                            if(localPostList!=null){
                                Log.d("User All Posts 2", localPostList.toString())
                            }

                            Log.d("User All Posts 4", localPostList?.size.toString())
                            userProfilePostsLiveData.value?.add(it.data as HashMap<String, Any>)
                        }
                        .addOnFailureListener {
                            Log.d("User All Posts", "Some Failure Occured while fetching user posts")
                        }
                }

//                Log.d("User All Posts 2", localPostList.toString())


            }.addOnFailureListener {
                Log.d("User All Posts 2", "Some Failure Occured while fetching user posts")
            }

    }

    fun getUserPostsIDs(userID: String?){
        val firebaseFirestore = FirebaseFirestore.getInstance()

        firebaseFirestore.collection("users").document(userID!!)
            .get()
            .addOnSuccessListener {
                liveData1.value = it.get("posts") as List<String>
            }
    }

    fun getAllPostsOfUser(listOfIDs : List<String>){
        val firebaseFirestore = FirebaseFirestore.getInstance()
        var someList = ArrayList<DocumentSnapshot>()
        var i = 0
        var j = listOfIDs.size
        for (i in 0..j-1){
            firebaseFirestore.collection("posts").document(listOfIDs[i])
                .get()
                .addOnSuccessListener {
                        someList.add(it)
                        Log.d("LiveData2 - 2", it.toString())
                        Log.d("LiveData2 - 2", someList.toString())
//                        liveData2.value?.add(it!!)

                }
        }
        if (i == j){
            liveData2.value = someList
            Log.d("LiveData2 - 1", liveData2.value.toString())
        }

//        if(!someList.isNullOrEmpty()){
////            liveData2.value = someList
//            Log.d("LiveData2", someList.toString())
//
//        }
    }

    fun getAllPosts(userId: String?){
        val firebaseFirestore = FirebaseFirestore.getInstance()

        firebaseFirestore.collection("posts").whereEqualTo("userID", userId)
            .get()
            .addOnSuccessListener {
                liveData3.value = it.documents as ArrayList<DocumentSnapshot>
                Log.d("UserPrlPostsViewModel", "Updated data")
            }
            .addOnFailureListener {
                Log.d("Error", "Error in all docs")
            }
    }
}