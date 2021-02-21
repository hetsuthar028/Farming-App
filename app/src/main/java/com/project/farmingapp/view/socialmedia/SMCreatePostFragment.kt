package com.project.farmingapp.view.socialmedia

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.project.farmingapp.R
import kotlinx.android.synthetic.main.fragment_s_m_create_post.*
import kotlinx.android.synthetic.main.nav_header.view.*
import java.io.IOException
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SMCreatePostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SMCreatePostFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var authUser: FirebaseAuth? = null
    private var postID: UUID? = null
    private var bitmap: Bitmap? = null
    lateinit var socialMediaPostsFragment: SocialMediaPostsFragment
    val db = FirebaseFirestore.getInstance()
    val data2 = HashMap<String, Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        storageReference = FirebaseStorage.getInstance().reference
        authUser = FirebaseAuth.getInstance()
        firebaseStore = FirebaseStorage.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_s_m_create_post, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SMCreatePostFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SMCreatePostFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data2["uploadType"] = ""
        uploadImageButton.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST
            )
            data2["uploadType"] = "image"
        }

        uploadVideoButton.setOnClickListener {
            val intent = Intent()
            intent.type = "video/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Video"),
                PICK_IMAGE_REQUEST
            )
            data2["uploadType"] = "video"
        }

        val googleLoggedUser = authUser!!.currentUser!!.displayName
        if (googleLoggedUser.isNullOrEmpty()) {
            db.collection("users").document(authUser!!.currentUser!!.email!!)
                .get()
                .addOnCompleteListener {
                    val data = it.result
                    data2["name"] = data!!.getString("name").toString()
                    Log.d("Google User", data!!.getString("name"))
                }
        }
        else {
            data2["name"] = googleLoggedUser.toString()
            Log.d("Normal User", googleLoggedUser)
        }

        createPostBtnSM.setOnClickListener {

            if(postTitleSM.text.toString().isNullOrEmpty())
            {
                Toast.makeText(activity!!.applicationContext,"Please enter title",Toast.LENGTH_SHORT).show()
            }
            else
            {
                uploadImage().setImageBitmap(bitmap)
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            filePath = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, filePath)


//                uploadImage().setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage() {
        if (filePath != null) {
            postID = UUID.randomUUID()
            val ref = storageReference?.child("posts/" + postID.toString())
            val uploadTask = ref?.putFile(filePath!!)

            val urlTask =
                uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation ref.downloadUrl
                })?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        addUploadRecordWithImageToDb(downloadUri.toString(), postID!!)
                    } else {
                        // Handle failures
                    }
                }?.addOnFailureListener {

                }
        } else {
            data2["uploadType"]=""
            addUploadRecordWithImageToDb(null, null)
        }
    }

    private fun addUploadRecordWithTextToDb() {
        addUploadRecordWithImageToDb(null, null)
    }

    private fun addUploadRecordWithImageToDb(uri: String?, postID: UUID?) {

        if (!uri.isNullOrEmpty()) {
            data2["imageUrl"] = uri.toString()
            data2["imageID"] = postID.toString()

        }

//        val docRef = FirebaseFirestore.getInstance().collection("users").document(authUser!!.currentUser.toString())

//        db.collection("users/" + authUser!!.currentUser?.email.toString() + "/posts")
//            .add(data)
//            .addOnSuccessListener { documentReference ->
//                Toast.makeText(activity!!.applicationContext, "Saved to DB", Toast.LENGTH_LONG)
//                    .show()
//            }
//            .addOnFailureListener { e ->
//                Toast.makeText(
//                    activity!!.applicationContext,
//                    "Error saving to DB",
//                    Toast.LENGTH_LONG
//                ).show()
//            }


        val data3 = HashMap<String, Any>()
//        data3["gender"] = "male"
//        data3["name"] = authUser!!.currentUser?.email.toString()
//        data2["imageUrl"] = uri
//        data2["uploadTime"] = System.currentTimeMillis()
//        data2["users"] = data3

        val postTimeStamp = System.currentTimeMillis()
        data2["userID"] = authUser!!.currentUser?.email.toString()
        data2["timeStamp"] = postTimeStamp
//        data2["name"] = googleLoggedUser.toString()
        data2["title"] = postTitleSM.text.toString()
        data2["description"] = descPostSM.text.toString()
//        data2["userProfileImage"] = authUser!!.currentUser!!.photoUrl.toString()

        db.collection("posts")
            .add(data2)
            .addOnSuccessListener { documentReference ->

                val data = HashMap<String, Any>()
                val posts = arrayListOf<String>()
                val postRecordID = documentReference.id.toString()

                posts.add(postRecordID)
                data["posts"] = posts

                db.collection("users")
                    .document("${authUser!!.currentUser?.email.toString()}")
                    .update("posts", FieldValue.arrayUnion(postRecordID))
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(
                            activity!!.applicationContext,
                            "Post Created",
                            Toast.LENGTH_LONG
                        ).show()

                        socialMediaPostsFragment = SocialMediaPostsFragment()
                        val transaction = activity!!.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_layout, socialMediaPostsFragment, "smPostList")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .setReorderingAllowed(true)
                            .addToBackStack("smPostList")
                            .commit()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            activity!!.applicationContext,
                            "Error saving to DB",
                            Toast.LENGTH_LONG
                        ).show()

                    }
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    activity!!.applicationContext,
                    "Error saving to DB",
                    Toast.LENGTH_LONG
                ).show()
            }
    }
}

private fun Any.setImageBitmap(bitmap: Bitmap?) {

}