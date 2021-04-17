package com.project.farmingapp.view.articles

import android.R.attr.fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.project.farmingapp.R
import com.project.farmingapp.adapter.ArticleListAdapter
import com.project.farmingapp.utilities.CellClickListener
import com.project.farmingapp.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.fragment_article_list.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ArticleListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArticleListFragment : Fragment(), CellClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel: ArticleViewModel
    lateinit var Adapter: ArticleListAdapter
    lateinit var fruitFragment: FruitsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        viewModel = ViewModelProviders.of(requireActivity())
            .get<ArticleViewModel>(ArticleViewModel::class.java)

        viewModel.getAllArticles("article_fruits")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.message3.observe(viewLifecycleOwner, Observer {

//            var datadata =   List<com.google.firebase.firestore.DocumentSnapshot>()


            Log.d("Art All Data", it[0].data.toString())


            Adapter = ArticleListAdapter(activity!!.applicationContext, it, this)
            recyclerArticleListFrag.adapter = Adapter
            recyclerArticleListFrag.layoutManager = GridLayoutManager(activity!!.applicationContext, 2)

//            Log.d("FruitFrag1", it.toString())
//            val attributes: Map<String, String> = it.get("attributes") as Map<String, String>
//            val desc = it.get("description").toString()
//
//            val diseases: List<String> = it.get("diseases") as List<String>
//            Log.d("Diseases", diseases.toString())

//            diseaseTextValueFruitFragArt.text = "\n" + diseases[diseases.size - 1].toString()
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_list, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ArticleListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ArticleListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

//    fun getList(){
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Articles"

    }



    override fun onCellClickListener(name: String) {
        fruitFragment = FruitsFragment()
        val bundle = Bundle()
        bundle.putString("name", name)
        fruitFragment.setArguments(bundle)
        val transaction = activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fruitFragment, name)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .setReorderingAllowed(true)
            .addToBackStack("name")
            .commit()
        Toast.makeText(activity!!.applicationContext, "Clicked" + name, Toast.LENGTH_SHORT).show()
    }
}