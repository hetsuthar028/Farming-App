package com.project.farmingapp.view.socialmedia

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.behavior.SwipeDismissBehavior
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.project.farmingapp.R
import com.project.farmingapp.adapter.CurrentWeatherAdapter
import com.project.farmingapp.adapter.SMPostListAdapter
import com.project.farmingapp.viewmodel.ArticleViewModel
import com.project.farmingapp.viewmodel.SocialMediaViewModel
import kotlinx.android.synthetic.main.fragment_social_media_posts.*
import kotlinx.android.synthetic.main.fragment_weather.*

import com.project.farmingapp.adapter.PaginationListener.Companion.PAGE_START
import kotlin.coroutines.EmptyCoroutineContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var viewModel: SocialMediaViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [SocialMediaPostsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SocialMediaPostsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var smCreatePostFragment: SMCreatePostFragment
    private var adapter : SMPostListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        viewModel = ViewModelProviders.of(requireActivity())
            .get<SocialMediaViewModel>(SocialMediaViewModel::class.java)

//        viewModel.loadPosts()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_social_media_posts, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SocialMediaPostsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SocialMediaPostsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun getData() {
        val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

        firebaseFirestore.collection("posts").orderBy("timeStamp", Query.Direction.DESCENDING).get()
            .addOnSuccessListener {
                Log.d("Posts data", it.documents.toString())
                adapter = SMPostListAdapter(activity!!.applicationContext, it.documents)
                postsRecycler.adapter = adapter
                postsRecycler.layoutManager = LinearLayoutManager(activity!!.applicationContext)
            }
            .addOnFailureListener {

            }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        smCreatePostFragment = SMCreatePostFragment()
        createPostFloating.setOnClickListener {
            val transaction = activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_layout, smCreatePostFragment, "smCreate")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setReorderingAllowed(true)
                .addToBackStack("smCreate")
                .commit()
        }
    }


}