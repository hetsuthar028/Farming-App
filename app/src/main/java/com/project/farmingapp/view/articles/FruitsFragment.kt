package com.project.farmingapp.view.articles

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.project.farmingapp.R
import com.project.farmingapp.model.data.WeatherRootList
import com.project.farmingapp.viewmodel.ArticleViewModel
import com.project.farmingapp.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_fruits.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FruitsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FruitsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private lateinit var viewModel: ArticleViewModel
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fruits, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FruitsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FruitsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProviders.of(requireActivity())
            .get<ArticleViewModel>(ArticleViewModel::class.java)
        viewModel.getMyArticle("apple")
        randomFruit.setOnClickListener {
            Toast.makeText(activity!!.applicationContext, "From Fruits", Toast.LENGTH_LONG).show()
            val hash = hashMapOf<String, Any>(
                "ss" to "ss"
            )
//            viewModel.updateArticle(hash)

//            Log.d("FruitsFragment", data.value.toString())
        }
        viewModel.getArticle()
            .observe(viewLifecycleOwner, object : Observer<HashMap<String, Any>> {
                override fun onChanged(t: HashMap<String, Any>?) {
                    Log.d("FruitFragment", t.toString())
                }

            })
    }
}