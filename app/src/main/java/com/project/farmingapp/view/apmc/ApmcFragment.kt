package com.project.farmingapp.view.apmc

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.farmingapp.R
import com.project.farmingapp.adapter.ApmcAdapter
import com.project.farmingapp.model.APMCApi
import com.project.farmingapp.model.data.APMCMain
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_apmc.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Field
import java.util.*


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
    lateinit var adapter: ApmcAdapter
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

//        bottomNav.selectedItemId = R.id.bottomNavAPMC

        getApmc()
        return inflater.inflate(R.layout.fragment_apmc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        var states = arrayOf(
            "All states",
            "Andhra Pradesh",
            "Chandigarh",
            "Chattisgarh",
            "Gujarat",
            "Hariyana",
            "Himachal Pradesh",
            "Jammu & Kashmir",
            "Jharkhand",
            "Karnataka",
            "Kerala",
            "Madhya Pradesh",
            "Maharashtra",
            "Odisha",
            "Pudu Cherry",
            "Punjab",
            "Rajasthan",
            "Tamil Nadu",
            "Telangana",
            "Uttar Pradesh",
            "Uttarakhand",
            "West Bengal"
        )

        var citiesInGujarat: Array<String> = arrayOf(
            "All Cities",
            "Ahmedabad",
            "Amreli",
            "Anand",
            "Anjar",
            "Babra",
            "Bagasara",
            "Bardoli",
            "Bawla",
            "Bayad",
            "Bhabhar",
            "Bharuch",
            "Chikli",
            "Dahod",
            "Deesa",
            "Gandhinagar",
            "Godhra",
            "Himmatnagar",
            "Idar",
            "Jamnagar",
            "Junagadh",
            "Kalol",
            "Khambhat",
            "Lalpur",
            "Lunawada",
            "Mahuwa",
            "Mehsana",
            "Morbi",
            "Nadiyad",
            "Navsari",
            "Palanpur",
            "Patan",
            "Rajkot",
            "Sanand",
            "Sihor",
            "Talod",
            "Unava",
            "Vadodara",
            "Valsad",
            "Wankaner"
        )
        var citiesInMaha: Array<String> = arrayOf(
            "All Cities",
            "Anand",
            "Bardoli",
            "Dahod",
            "Deesa",
            "Gandhinagar",
            "Junagadh",
            "Kalol",
            "Khambhat",
            "Talod",
            "Unava",
            "Vadodara",
            "Valsad",
            "Wankaner"
        )

        var aa = ArrayAdapter(
            activity!!.applicationContext,
            android.R.layout.simple_spinner_dropdown_item,
            states
        )
//        var aa2 = ArrayAdapter(activity!!.applicationContext, android.R.layout.simple_spinner_dropdown_item, citiesInGujarat)
        spinner1.adapter = aa
//        spinner2.adapter = aa2
//        spinner1.setSelection(0, false)
//        spinner2.setSelection(0, false)


        val someMap = mapOf("Chandigarh" to citiesInMaha, "Gujarat" to citiesInGujarat)


        spinner1.onItemSelectedListener = object :
            AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0) {
                    Toast.makeText(activity!!.applicationContext, "All states", Toast.LENGTH_LONG)
                        .show()
                } else {
                    var aa2 = ArrayAdapter(
                        activity!!.applicationContext,
                        android.R.layout.simple_spinner_dropdown_item,
                        someMap[states[p2]]!!
                    )
                    spinner2.adapter = aa2
                    aa2.notifyDataSetChanged()
                }
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(activity!!.applicationContext, "Something", Toast.LENGTH_LONG).show()
            }
        }

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
        val apmc1: Call<APMCMain> = APMCApi.apmcInstances.getapmc(20)
        apmc1.enqueue(object : Callback<APMCMain> {
            override fun onFailure(call: Call<APMCMain>, t: Throwable) {
                Log.d("bharat222", "fail ho gya", t)
            }

            override fun onResponse(
                call: Call<APMCMain>,
                response: Response<APMCMain>
            ) {

                val apmcdata = response.body()
                if (apmcdata != null) {
//                    var data= mutableListOf<APMCMain>()
//                    data.add(apmcdata)
                    Log.d("APMCFrag", apmcdata.records.toString())
                    adapter = ApmcAdapter(activity!!.applicationContext, apmcdata.records)
                    recycleAPMC.adapter = adapter
                    recycleAPMC.layoutManager = LinearLayoutManager(activity!!.applicationContext)

                    //temp.text=rootdata.weather.main.toString()
                    Log.d("bharat222", apmcdata.toString())
                }
            }

        })
    }
}