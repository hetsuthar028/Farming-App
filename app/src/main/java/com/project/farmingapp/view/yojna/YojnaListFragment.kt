package com.project.farmingapp.view.yojna

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.farmingapp.R
import com.project.farmingapp.adapter.ArticleListAdapter
import com.project.farmingapp.adapter.YojnaAdapter
import com.project.farmingapp.utilities.CellClickListener
import com.project.farmingapp.view.articles.FruitsFragment
import com.project.farmingapp.viewmodel.ArticleViewModel
import com.project.farmingapp.viewmodel.YojnaViewModel
import kotlinx.android.synthetic.main.fragment_yojna_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [YojnaListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class YojnaListFragment : Fragment(), CellClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel: YojnaViewModel
    lateinit var Adapter: YojnaAdapter
    lateinit var yojnaFragment: YojnaFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
       viewModel = ViewModelProviders.of(requireActivity())
           .get<YojnaViewModel>(YojnaViewModel::class.java)

       viewModel.getAllYojna("yojnas")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.message3.observe(viewLifecycleOwner, Observer {

            Log.d("Art All Data", it[0].data.toString())


            Adapter = YojnaAdapter(activity!!.applicationContext, it, this)
            rcyclr_yojnaList.adapter = Adapter
            rcyclr_yojnaList.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        })
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_yojna_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Krishi Yojna"
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment YojnaListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            YojnaListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onCellClickListener(name: String) {
       yojnaFragment = YojnaFragment()
        val bundle = Bundle()
        bundle.putString("name", name)
        yojnaFragment.setArguments(bundle)
        val transaction = activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, yojnaFragment, name)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .setReorderingAllowed(true)
            .addToBackStack("yojnaListFrag")
            .commit()
    }

}