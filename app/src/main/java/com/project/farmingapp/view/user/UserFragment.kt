package com.project.farmingapp.view.user

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.service.autofill.UserData
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.toColorInt
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.project.farmingapp.R
import com.project.farmingapp.adapter.PostListUserProfileAdapter
import com.project.farmingapp.utilities.CellClickListener
import com.project.farmingapp.view.user.setImageBitmap
import com.project.farmingapp.viewmodel.ArticleViewModel
import com.project.farmingapp.viewmodel.UserDataViewModel
import com.project.farmingapp.viewmodel.UserProfilePostsViewModel
import kotlinx.android.synthetic.main.fragment_user.*
import java.io.IOException
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var viewModel: UserProfilePostsViewModel
private lateinit var userDataViewModel: UserDataViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment(), CellClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val firebaseAuth = FirebaseAuth.getInstance()
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var postID: UUID? = null
    private var storageReference: StorageReference? = null
    private var bitmap: Bitmap? = null
    private var uploadProfOrBack: Int? = null

    val firebaseFirestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }



        viewModel = ViewModelProviders.of(requireActivity())
            .get<UserProfilePostsViewModel>(UserProfilePostsViewModel::class.java)

        userDataViewModel = ViewModelProviders.of(requireActivity())
            .get<UserDataViewModel>(UserDataViewModel::class.java)
        storageReference = FirebaseStorage.getInstance().reference
//        viewModel.getUserPosts(firebaseAuth.currentUser!!.email)
//        viewModel.getUserPostsIDs(firebaseAuth.currentUser!!.email)
        viewModel.getAllPosts(firebaseAuth.currentUser!!.email)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        viewModel.liveData1.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                viewModel.getAllPostsOfUser(it)
            }
        })


        viewModel.liveData2.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Log.d("Live Data In Frag", it.toString())
            }
        })

//        val adapter = PostListUserProfileAdapter(activity!!.applicationContext, it, this)





        viewModel.userProfilePostsLiveData2.observe(viewLifecycleOwner, Observer {
            Log.d("Some Part 2", it.toString())
        })

//        viewModel.getUserPosts(firebaseAuth.currentUser!!.email)
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addAboutTextUserFrag.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        viewModel.userProfilePostsLiveData.observe(viewLifecycleOwner, Observer {
//            val adapter = PostListUserProfileAdapter(activity!!.applicationContext, it)
//            userProfilePostsRecycler.adapter = adapter
//            userProfilePostsRecycler.layoutManager = LinearLayoutManager(activity!!.applicationContext)
            Log.d("Some Part", it.toString())
        })


//        userDataViewModel.getUserData(firebaseAuth.currentUser!!.email as String)
//        var userData = userDataViewModel.userliveData.value

        userDataViewModel.userliveData.observe(viewLifecycleOwner, Observer {
            Log.d("User Fragment", it.data.toString())
        })

        aboutValueUserProfileFrag.setOnClickListener {
            inputLayout1.visibility = View.VISIBLE
            addAboutTextUserFrag.visibility = View.GONE
            aboutValueEditUserProfileFrag.setText(aboutValueUserProfileFrag.text.toString())
            aboutValueUserProfileFrag.visibility = View.GONE
            saveBtnAboutUserProfileFrag.visibility = View.VISIBLE
        }

        addAboutTextUserFrag.setOnClickListener {
            inputLayout1.visibility = View.VISIBLE
            addAboutTextUserFrag.visibility = View.GONE
            saveBtnAboutUserProfileFrag.visibility = View.VISIBLE
        }

        saveBtnAboutUserProfileFrag.setOnClickListener {
            addAboutTextUserFrag.visibility = View.GONE
            aboutValueUserProfileFrag.visibility = View.VISIBLE
            aboutValueUserProfileFrag.text = aboutValueEditUserProfileFrag.text
            saveBtnAboutUserProfileFrag.visibility = View.GONE
            userDataViewModel.updateUserField(activity!!.applicationContext, firebaseAuth.currentUser!!.email.toString() as String, aboutValueEditUserProfileFrag.text.toString() as String, null)
            inputLayout1.visibility = View.GONE
        }

        uploadProgressBarProfile.visibility = View.GONE
        uploadBackProgressProfile.visibility = View.GONE
//        uploadProfilePictureImageToDB.setOnClickListener {
//
//
//
////            uploadImage2().setImageBitmap(bitmap)
//
//            Toast.makeText(activity!!.applicationContext, "Uploading...", Toast.LENGTH_SHORT).show()
//            uploadProfilePictureImageToDB.visibility = View.GONE
//        }


        uploadUserBackgroundImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/* video/*"
            intent.action = Intent.ACTION_PICK
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST
            )
            uploadProfOrBack = 1

            Toast.makeText(activity!!.applicationContext, "Uploading Background Image", Toast.LENGTH_SHORT).show()
        }

        uploadProfilePictureImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/* video/*"
            intent.action = Intent.ACTION_PICK
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST
            )
            uploadProfOrBack = 0
            Toast.makeText(activity!!.applicationContext, "Uploading your Image", Toast.LENGTH_SHORT).show()
        }


        userDataViewModel.userliveData.observe(viewLifecycleOwner, Observer {
            Log.d("User Data in VM Frag", it.get("name").toString())
            Log.d("Data in User", it.toString())
            userNameUserProfileFrag.text = it!!.getString("name")
            userCityUserProfileFrag.text = "City: " + it?.getString("city")
            if(it?.get("profileImage") == null || it?.getString("profileImage").isNullOrBlank()){
                uploadProfilePictureImage.visibility = View.VISIBLE
            } else{
                uploadProfilePictureImage.visibility = View.GONE
                Glide.with(activity!!.applicationContext).load(it?.get("profileImage"))
                    .into(userImageUserFrag)
            }


            if(it?.get("backImage") == null || it?.getString("backImage").isNullOrBlank()){
                uploadUserBackgroundImage.visibility = View.VISIBLE
            } else{
                uploadUserBackgroundImage.visibility = View.GONE
                Glide.with(activity!!.applicationContext).load(it?.getString("backImage"))
                    .into(userBackgroundImage)
            }




            val posts = it?.get("posts") as List<String>
            userPostsCountUserProfileFrag.text = "Posts: " + posts.size.toString()
            userEmailUserProfileFrag.text = firebaseAuth.currentUser!!.email
            val about = it?.getString("about")

            if (about == null || about == "") {
                aboutValueUserProfileFrag.visibility = View.GONE
                inputLayout1.visibility = View.GONE
                saveBtnAboutUserProfileFrag.visibility = View.GONE
            } else {
                aboutValueUserProfileFrag.visibility = View.VISIBLE
                addAboutTextUserFrag.visibility = View.GONE
                inputLayout1.visibility = View.GONE
                saveBtnAboutUserProfileFrag.visibility = View.GONE
                aboutValueUserProfileFrag.text = about
            }

        })

        imageEdit.setOnClickListener {
            uploadProfilePictureImage.visibility = View.VISIBLE
            uploadUserBackgroundImage.visibility = View.VISIBLE
            imageChecked.visibility = View.VISIBLE
            imageEdit.visibility = View.GONE
        }

        imageChecked.setOnClickListener {
            uploadProfilePictureImage.visibility = View.GONE
            uploadUserBackgroundImage.visibility = View.GONE
            imageEdit.visibility = View.VISIBLE
            imageChecked.visibility = View.GONE
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

                if(bitmap!=null){
                    Log.d("UserFragment", bitmap.toString())

                    if(uploadProfOrBack == 0){
                        uploadProgressBarProfile.visibility = View.VISIBLE
                        uploadProfilePictureImage.visibility = View.GONE
                    } else if(uploadProfOrBack == 1){
                        uploadBackProgressProfile.visibility = View.VISIBLE
                        uploadUserBackgroundImage.visibility = View.GONE
                    }



//                    uploadImage3()
//                    uploadImage4()
                    uploadImage2().setImageBitmap(bitmap)
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
//            if (data == null || data.data == null) {
//                return
//            }
//
//            filePath = data.data
//            try {
//
//                bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, filePath)
//
//                if(bitmap!=null){
//                    Log.d("UserFragment", bitmap.toString())
//                    uploadImage2().setImageBitmap(bitmap)
//                }
//
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
//    }



    private fun uploadImage2() {
        if (filePath != null) {
            postID = UUID.randomUUID()
            val ref = storageReference?.child("users/" + postID.toString())
            val uploadTask = ref?.putFile(filePath!!)
            Log.d("UserFragment", filePath.toString())
            val urlTask =
                uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        Toast.makeText(activity!!.applicationContext, "Error3", Toast.LENGTH_SHORT).show()
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation ref.downloadUrl
                })?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        Toast.makeText(activity!!.applicationContext, "Uploading1...", Toast.LENGTH_SHORT).show()
                        uploadUserPhotos(downloadUri.toString(), postID!!)

                    } else {
                        // Handle failures
                        Toast.makeText(activity!!.applicationContext, "Error", Toast.LENGTH_SHORT).show()
                        uploadProgressBarProfile.visibility = View.GONE
                        uploadBackProgressProfile.visibility = View.GONE
                        uploadUserBackgroundImage.visibility = View.VISIBLE
                        uploadProfilePictureImage.visibility = View.VISIBLE
                    }
                }?.addOnFailureListener {
                    Toast.makeText(activity!!.applicationContext, "Error2", Toast.LENGTH_SHORT).show()
                    uploadProgressBarProfile.visibility = View.GONE
                    uploadBackProgressProfile.visibility = View.GONE
                    uploadUserBackgroundImage.visibility = View.VISIBLE
                    uploadProfilePictureImage.visibility = View.VISIBLE
                }
        } else {
//            data2["uploadType"] = ""
//            addUploadRecordWithImageToDb(null, null)
//            Log.d("File Type 2", "Null")
        }
    }




    fun uploadUserPhotos(uri: String?, postID: UUID?){

        if(uploadProfOrBack == 0){
            firebaseFirestore.collection("users").document(firebaseAuth.currentUser!!.email!!)
                .update(mapOf(
                    "profileImage" to uri
                ))
                .addOnSuccessListener {
                    Toast.makeText(activity!!.applicationContext, "Profile Updated", Toast.LENGTH_SHORT).show()
                    uploadProgressBarProfile.visibility = View.GONE
                    imageEdit.visibility = View.VISIBLE
                    imageChecked.visibility = View.GONE
                    userImageUserFrag.setImageBitmap(bitmap)
                    userDataViewModel.getUserData(firebaseAuth!!.currentUser!!.email.toString())
                }
                .addOnFailureListener {
                    uploadProgressBarProfile.visibility = View.GONE
                    userImageUserFrag.visibility = View.VISIBLE
                    Toast.makeText(activity!!.applicationContext, "Failed to Update Profile", Toast.LENGTH_SHORT).show()

                }
        }
        else if(uploadProfOrBack == 1){
            firebaseFirestore.collection("users").document(firebaseAuth.currentUser!!.email!!)
                .update(mapOf(
                    "backImage" to uri
                ))
                .addOnSuccessListener {
                    Toast.makeText(activity!!.applicationContext, "Profile Updated 2", Toast.LENGTH_SHORT).show()
                    uploadBackProgressProfile.visibility = View.GONE
                    userBackgroundImage.setImageBitmap(bitmap)
                    imageEdit.visibility = View.VISIBLE
                    imageChecked.visibility = View.GONE
                    userDataViewModel.getUserData(firebaseAuth!!.currentUser!!.email.toString())
                }
                .addOnFailureListener {
                    uploadBackProgressProfile.visibility = View.GONE
                    userBackgroundImage.setImageBitmap(bitmap)
                    Toast.makeText(activity!!.applicationContext, "Failed to Update Profile", Toast.LENGTH_SHORT).show()
                }
        }


    }

    override fun onCellClickListener(name: String) {
        val dialog = AlertDialog.Builder(activity)

        dialog.setTitle("Your Post")
            .setMessage("Do you want to manipulate your post?")
            .setPositiveButton("View") { dialogInterface, i ->

            }
            .setNegativeButton("Delete") { dialogInterface, i ->
                userDataViewModel.deleteUserPost(firebaseAuth.currentUser!!.email!!, name)
            }
            .setNeutralButton("Cancel"){
                dialogInterface, i ->

            }
            .show()

//        builder.setOnShowListener{
//            builder.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.TRANSPARENT)
//            builder.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED)
//            builder.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(R.color.fontColor!!)
//        }




        Toast.makeText(activity!!.applicationContext, "You Clicked" + name.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.liveData3.observe(this, Observer {
            Log.d("All Posts", it.toString())
            val adapter = PostListUserProfileAdapter(activity!!.applicationContext, it, this)
            userProfilePostsRecycler.adapter = adapter

            userProfilePostsRecycler.layoutManager =
                LinearLayoutManager(activity!!.applicationContext)
            adapter.notifyDataSetChanged()

        })
    }
}
private fun Any.setImageBitmap(bitmap: Bitmap?) {

}