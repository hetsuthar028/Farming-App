package com.project.farmingapp.view.yojna

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.project.farmingapp.R
import com.project.farmingapp.viewmodel.YojnaViewModel
import kotlinx.android.synthetic.main.fragment_yojna.*
import kotlinx.coroutines.GlobalScope

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [YojnaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class YojnaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var yojnaViewModel: YojnaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val tag = this.tag.toString()
        Log.d("YojnaFragment", this.tag.toString())

        yojnaViewModel =
            ViewModelProviders.of(requireActivity()).get<YojnaViewModel>(YojnaViewModel::class.java)

        yojnaViewModel.getYojna(this.tag.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_yojna, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment YojnaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            YojnaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Krishi Yojna"
        progressYojna.visibility = View.VISIBLE

        yojnaViewModel.msg.observe(viewLifecycleOwner, Observer {
            yojnaTitle.text = it.get("title").toString()
            yojnaDesc.text = it.get("description").toString()
            yojnaDate.text = it.get("launch").toString()
            yojnaLaunchedBy.text = it.get("headedBy").toString()
            yojnaBudget.text = it.get("budget").toString()
            val eligibility: ArrayList<String> = it.get("eligibility") as ArrayList<String>
            val documents: ArrayList<String> = it.get("documents") as ArrayList<String>
            val objectives: ArrayList<String> = it.get("objective") as ArrayList<String>

            yojnaEligibility.text = ""
            yojnaDocumentsRequired.text = ""
            yojnaObjectives.text = ""

            for (i in 0..(eligibility.size - 1)) {
                yojnaEligibility.text =
                    yojnaEligibility.text.toString() + (i + 1).toString() + ". " + eligibility[i].toString() + "\n"
            }

            for (i in 0..(documents.size - 1)) {
                yojnaDocumentsRequired.text =
                    yojnaDocumentsRequired.text.toString() + (i + 1).toString() + ". " + documents[i].toString() + "\n"
            }

            for (i in 0..(objectives.size - 1)) {
                yojnaObjectives.text =
                    yojnaObjectives.text.toString() + (i + 1).toString() + ". " + objectives[i].toString() + "\n"
            }

            yojnaWebsite.text = it.get("website").toString()
            Glide.with(this).load(it.get("image").toString()).into(yojnaImage)
            progressYojna.visibility = View.GONE
        })
    }
}