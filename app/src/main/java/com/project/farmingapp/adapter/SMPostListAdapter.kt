package com.project.farmingapp.adapter

import android.content.Context
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.project.farmingapp.R
import kotlinx.android.synthetic.main.post_with_image_sm.view.*


class SMPostListAdapter(val context: Context, val postListData : List<DocumentSnapshot>): RecyclerView.Adapter<SMPostListAdapter.SMPostListViewModel>() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseFirestore: FirebaseFirestore
    class SMPostListViewModel(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SMPostListViewModel {
        val view = LayoutInflater.from(context).inflate(R.layout.post_with_image_sm, parent, false)
        return SMPostListAdapter.SMPostListViewModel(view)
    }

    override fun getItemCount(): Int {
        return postListData.size
    }

    override fun onBindViewHolder(holder: SMPostListViewModel, position: Int) {
        val currentPost = postListData[position]



        holder.itemView.userNamePostSM.text = currentPost.get("name").toString()
        holder.itemView.userPostTitleValue.text = currentPost.get("title").toString()
        holder.itemView.userPostDescValue.text = currentPost.get("description").toString()
        holder.itemView.userPostUploadTime.text = DateUtils.getRelativeTimeSpanString(currentPost.get("timeStamp") as Long)

        val imageUrl = currentPost.get("imageUrl")
        Log.d("Post without Image1", imageUrl.toString())


        val uploadType = currentPost.get("uploadType").toString()
        if (uploadType == "video"){

//            val mediaController: MediaController = MediaController(context.applicationContext)
////            val uri: Uri = Uri.parse()
//            mediaController.setAnchorView(holder.itemView.postVideoSM)
//
//            holder.itemView.postVideoSM.setZOrderMediaOverlay(true)
//            holder.itemView.postVideoSM.setMediaController(mediaController)
//
//            Log.d("Upload Type 1 ", uploadType)
//            holder.itemView.postVideoSM.setVideoPath(currentPost.getString("imageUrl"))
////            videoView1.requestFocus()
//            holder.itemView.postVideoSM.setOnPreparedListener {
//
//            }
//            holder.itemView.postVideoSM.start()
//
//            holder.itemView.postImageSM.visibility = View.GONE
//            holder.itemView.postVideoSM.visibility = View.VISIBLE


            // Web View

            val webSet: WebSettings = holder.itemView.postVideoSM.settings
            webSet.javaScriptEnabled = true
            webSet.loadWithOverviewMode = true
            webSet.useWideViewPort = true


            holder.itemView.postVideoSM.setWebViewClient(object : WebViewClient() {
                // autoplay when finished loading via javascript injection
                override fun onPageFinished(view: WebView, url: String) {
//                    holder.itemView.postVideoSM.loadUrl("javascript:(function() { document.getElementsByTagName('video')[0].play(); })()")
                }
            })


            holder.itemView.postVideoSM.loadUrl(currentPost.get("imageUrl").toString())
//            holder.itemView.postVideoSM.stopLoading()
            holder.itemView.postImageSM.visibility = View.GONE
            holder.itemView.postVideoSM.visibility = View.VISIBLE


        } else if (uploadType == "image"){
            Glide.with(context).load(currentPost.get("imageUrl")).into(holder.itemView.postImageSM)
            holder.itemView.postVideoSM.visibility = View.GONE

            holder.itemView.postImageSM.visibility = View.VISIBLE
            Log.d("Upload Type 2 ", uploadType)
        }else if (uploadType.isEmpty() ){
            Log.d("Post without Image2", imageUrl.toString())
            holder.itemView.postImageSM.visibility = View.GONE
            holder.itemView.postVideoSM.visibility = View.GONE
            Log.d("Upload Type 3 ", uploadType)
        }

        firebaseAuth = FirebaseAuth.getInstance()

        holder.itemView.userProfileImageCard.animation = AnimationUtils.loadAnimation(context, R.anim.fade_transition)
        holder.itemView.post_container.animation = AnimationUtils.loadAnimation(context, R.anim.fade_transition)


        holder.itemView.post_container.animation = AnimationUtils.loadAnimation(context, R.anim.fade_transition)
//        Glide.with(context).load(firebaseAuth.currentUser!!.photoUrl.toString()).into(holder.itemView.userProfileImagePost)
        holder.itemView.userPostDescValue.setOnClickListener {
            holder.itemView.userPostDescValue.maxLines = Int.MAX_VALUE
        }



        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("users").document("${currentPost.get("userID")}").get()
            .addOnSuccessListener {
                val profileImage = it.get("profileImage").toString()
                if (!profileImage.isNullOrEmpty()){
                    Glide.with(context).load(it.get("profileImage").toString()).into(holder.itemView.userProfileImagePost)
                }
            }
    }
}