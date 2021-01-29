package com.project.farmingapp.view.apmc

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.farmingapp.R
import com.project.farmingapp.adapter.ApmcAdapter
import com.project.farmingapp.model.APMCApi
import com.project.farmingapp.model.data.APMCMain
import kotlinx.android.synthetic.main.fragment_apmc.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ApmcFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ApmcFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
lateinit var adapter:ApmcAdapter
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
        getApmc()
        return inflater.inflate(R.layout.fragment_apmc, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ApmcFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ApmcFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getApmc() {
        val apmc1: Call<APMCMain> =APMCApi.apmcInstances.getapmc(20)
        apmc1.enqueue(object: Callback<APMCMain> {
            override fun onFailure(call: Call<APMCMain>, t: Throwable) {
                Log.d("bharat222","fail ho gya",t)



            }

            override fun onResponse(
                call: Call<APMCMain>,
                response: Response<APMCMain>
            ) {

                val apmcdata:APMCMain? =response.body()
                if (apmcdata!=null)
                {
                    var data= mutableListOf<APMCMain>()
                    data.add(apmcdata)
                    adapter= ApmcAdapter(activity!!.applicationContext,apmcdata.records)
                    recycleAPMC.adapter=adapter
                    recycleAPMC.layoutManager= LinearLayoutManager(activity!!.applicationContext)

                    //temp.text=rootdata.weather.main.toString()
                    Log.d("bharat222",apmcdata.toString())
                }
            }

        })
    }
}