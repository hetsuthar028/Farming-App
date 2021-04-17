package com.project.farmingapp.view.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.project.farmingapp.R
import com.project.farmingapp.adapter.PostListUserProfileAdapter
import com.project.farmingapp.viewmodel.ArticleViewModel
import com.project.farmingapp.viewmodel.UserProfilePostsViewModel
import kotlinx.android.synthetic.main.fragment_user.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var viewModel: UserProfilePostsViewModel
/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        val firebaseAuth = FirebaseAuth.getInstance()

        viewModel = ViewModelProviders.of(requireActivity())
            .get<UserProfilePostsViewModel>(UserProfilePostsViewModel::class.java)

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
            if(it !=null){
                viewModel.getAllPostsOfUser(it)
            }
        })


        viewModel.liveData2.observe(viewLifecycleOwner, Observer {
            if (it !=null){
              Log.d("Live Data In Frag", it.toString())
            }
        })

        viewModel.liveData3.observe(viewLifecycleOwner, Observer {
            Log.d("All Posts", it.toString())

            userProfilePostsRecycler.adapter = PostListUserProfileAdapter(activity!!.applicationContext, it)
            userProfilePostsRecycler.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        })


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

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Profile"

        viewModel.userProfilePostsLiveData.observe(viewLifecycleOwner, Observer {
//            val adapter = PostListUserProfileAdapter(activity!!.applicationContext, it)
//            userProfilePostsRecycler.adapter = adapter
//            userProfilePostsRecycler.layoutManager = LinearLayoutManager(activity!!.applicationContext)
            Log.d("Some Part", it.toString())
        })
    }
}