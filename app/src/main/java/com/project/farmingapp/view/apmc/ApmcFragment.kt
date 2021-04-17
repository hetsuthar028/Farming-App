package com.project.farmingapp.view.apmc

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.farmingapp.R
import com.project.farmingapp.adapter.ApmcAdapter
import com.project.farmingapp.model.APMCApi
import com.project.farmingapp.model.data.APMCCustomRecords
import com.project.farmingapp.model.data.APMCMain
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_apmc.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Field
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.util.*
import kotlin.collections.ArrayList


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
    var indexSpinner1: Int? = null
    var indexSpinner2: Int? = null
    var someMap: Map<Any, Array<String>>? = null
    var states: Array<String>? = null

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

//        getApmc()
        return inflater.inflate(R.layout.fragment_apmc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "APMC"

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        dateValueTextApmc.text = sdf.format(Date()).toString()



        states = arrayOf(
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
            "Bhavnagar",
            "Bhesan",
            "Bhiloda",
            "Bhuj",
            "Bilimore",
            "Bodeli",
            "Borsad",
            "Botad",
            "Chikhli Apmc",
            "Chotila Apmc",
            "Dabh01 Apmc",
            "Dahod",
            "Dediyapada Apmc",
            "Dehgam",
            "Devgadhbaria Apmc",
            "Dhandhuka Apmc",
            "Dhanera",
            "Dhari",
            "Dhoalka",
            "Dhoraji",
            "Dhrol",
            "Fatehpura Apmc",
            "Gadhada",
            "Gandhinagar Apmc",
            "Gariyadhar Apmc",
            "Godhra",
            "Gondal",
            "Halvad",
            "Himmatnagar",
            "Idar Apmc",
            "Jam Jodhpur",
            "Jamkandorna",
            "Jamkhambhaliya",
            "Jamnagar",
            "Jasdan",
            "Jetpur",
            "Jhagadiya Apmc",
            "Jhalod",
            "Junagadh",
            "Kadi Apmc",
            "Kalavad",
            "Kalol",
            "Kapadwanj",
            "Karjan",
            "Kathlal Apmc",
            "Keshod",
            "Khambhat",
            "Kodinar",
            "Kosamba Apmc",
            "Lalpur",
            "Limkheda Apmc",
            "Lunawada Apmc",
            "Mahemdavad",
            "Mahuva",
            "Mahuva Apmc",
            "Manadal Apmc",
            "Mandvi Apmc",
            "Matar Apmc",
            "Mehsana",
            "Modasa",
            "Morbi",
            "Nadiad",
            "Navsari Apmc",
            "Nizar",
            "Padara",
            "Palanpur",
            "Palitana Apmc",
            "Panthawada",
            "Patan",
            "Patdi Apmc",
            "Pavi-Jetpur",
            "Petalad",
            "Porbandar",
            "Prantij Apmc",
            "Radhanpur Apmc",
            "Rajkot",
            "Rajpipla Apmc",
            "Rajula",
            "Rapar",
            "Sanand",
            "Sanjeli Apmc",
            "Savarkundla",
            "Savli",
            "Selamba Apmc",
            "Siddhpur",
            "Sihor Apmc",
            "Songadh Apmc",
            "Talaja Apmc",
            "Talod",
            "Tarapur",
            "Thara",
            "Tharad",
            "Thasra Apmc",
            "Tilakwada Apmc",
            "Umrala Apmc",
            "Una Apmc",
            "Unava Apmc",
            "Upleta",
            "Vadali Apmc",
            "Vadhvan",
            "Vadodara",
            "Valabhipur Apmc",
            "Valia Apmc",
            "Valsad",
            "Vijapur",
            "Vinchhiya",
            "Visavadar",
            "Visanagar",
            "Vyara",
            "Wankaner APMC"

        )
        var citiesInMaha: Array<String> = arrayOf(
            "All Cities",
            "Aamgaoni",
            "Aatpadi",
            "Achalpur",
            "Aheri",
            "Ahmednagar",
            "Akola",
            "Akot",
            "Amalner",
            "Amravathi",
            "Ambejogai",
            "Anjangaon Surji",
            "Arjuni Morgoan",
            "Arvi",
            "Aurangabad",
            "Ausa",
            "Barmati",
            "Barshi",
            "Basmat",
            "Beed",
            "Bhiwaour"
        )

        var aa = ArrayAdapter(
            activity!!.applicationContext,
            android.R.layout.simple_spinner_dropdown_item,
            states!!
        )
//        var aa2 = ArrayAdapter(activity!!.applicationContext, android.R.layout.simple_spinner_dropdown_item, citiesInGujarat)
        spinner1.adapter = aa
//        spinner2.adapter = aa2
//        spinner1.setSelection(0, false)
//        spinner2.setSelection(0, false)


        someMap = mapOf(
            "Chandigarh" to citiesInMaha,
            "Gujarat" to citiesInGujarat,
            "Maharashtra" to citiesInMaha
        )


        spinner1.onItemSelectedListener = object :
            AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0) {
                    Toast.makeText(activity!!.applicationContext, "All states", Toast.LENGTH_LONG)
                        .show()
                    textAPMCWarning.text = "Please Select State and District"
                    recycleAPMC.visibility = View.GONE
                    textAPMCWarning.visibility = View.VISIBLE
                } else {
                    var aa2 = ArrayAdapter(
                        activity!!.applicationContext,
                        android.R.layout.simple_spinner_dropdown_item,

                        someMap!![states!![p2]]!!
                    )

                    indexSpinner1 = p2
                    spinner2.adapter = aa2
                    aa2.notifyDataSetChanged()
                }
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(activity!!.applicationContext, "Something", Toast.LENGTH_LONG).show()
            }
        }

        spinner2.onItemSelectedListener = object :
            AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0) {
                    Toast.makeText(activity!!.applicationContext, "All District", Toast.LENGTH_LONG)
                        .show()
                    textAPMCWarning.text = "Please Select District"
                    recycleAPMC.visibility = View.GONE
                    textAPMCWarning.visibility = View.VISIBLE
                } else {

                    if (p2 != 0) {
                        getApmc("${someMap!![states!![indexSpinner1!!]]!![p2]}")
                    }
                    indexSpinner2 = p2


//                    var aa2 = ArrayAdapter(
//                        activity!!.applicationContext,
//                        android.R.layout.simple_spinner_dropdown_item,
//                        someMap[states[p2]]!!
//                    )
//                    spinner2.adapter = aa2
//                    aa2.notifyDataSetChanged()
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

    private fun getApmc(district: String) {
        val apmc1: Call<APMCMain> = APMCApi.apmcInstances.getapmc(20)
        var apmc2: Call<APMCMain>? = null
        if (indexSpinner2 != 0) {

//            apmc2 = APMCApi.apmcInstances.getSomeData(someMap!![states!![indexSpinner1?]]!)

            apmc2 = APMCApi.apmcInstances.getSomeData(district)
            Log.d("APMC District", district)


            apmc2!!.enqueue(object : Callback<APMCMain> {
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

//                    val totalRecords = apmcdata.records.size
//                    for (i in 0..totalRecords){
//
//                    }

                        val updatedYear = apmcdata.updated_date.toString().slice(0..3)
                        val updatedMonth = apmcdata.updated_date.toString().slice(5..6)
                        val updatedDate = apmcdata.updated_date.toString().slice(8..9)


                        dateValueTextApmc.text = "$updatedDate/$updatedMonth/$updatedYear"
                        if (apmcdata.records.size == 0) {
                            textAPMCWarning.visibility = View.VISIBLE
                            recycleAPMC.visibility = View.GONE
                            textAPMCWarning.text = "No records found!"
                        } else {
                            textAPMCWarning.visibility = View.GONE
                            recycleAPMC.visibility = View.VISIBLE
                            Log.d("APMCFrag", apmcdata.records.toString())

                            val totalRecords = apmcdata.records.size
                            var firstMarket = ""
                            if (!apmcdata.records[0].market.isNullOrEmpty()) {
                                firstMarket = apmcdata.records[0].market.toString()
                            }

                            val customRecords = ArrayList<APMCCustomRecords>()

                            val list1 = mutableListOf<String>()
                            val list2 = mutableListOf<String>()
                            val list3 = mutableListOf<String>()
                            list1.add(apmcdata.records[0].commodity)
                            list2.add(apmcdata.records[0].min_price)
                            list3.add(apmcdata.records[0].max_price)

                            var previousRecord = APMCCustomRecords(
                                apmcdata.records[0].state,
                                apmcdata.records[0].district,
                                apmcdata.records[0].market,
                                list1,
                                list2,
                                list3
                            )

                            val ss = apmcdata.records[0].market

                            Log.d("PreREc", previousRecord.toString())

//                            var gettingUpdatedRecords: APMCCustomRecords? = null
                            if (totalRecords==1){
                                customRecords.add(previousRecord)
                            } else{
                                var count = 0
                                for (i in 1..totalRecords-1) {
                                    Toast.makeText(activity!!.applicationContext, "Single Record", Toast.LENGTH_SHORT).show()


//                                var firstMarket2 = ""
                                    if (apmcdata.records[i].market == previousRecord.market) {
                                        previousRecord.commodity.add(apmcdata.records[i].commodity)
                                        previousRecord.min_price.add(apmcdata.records[i].min_price)
                                        previousRecord.max_price.add(apmcdata.records[i].max_price)
//                                    list1.add(apmcdata.records[i].commodity)
//                                    list2.add(apmcdata.records[i].min_price)
//                                    list3.add(apmcdata.records[i].max_price)
                                        count = 1
                                    } else {
                                        count = 0
                                        customRecords.add(previousRecord)
                                        list1.add(apmcdata.records[i].commodity)
                                        list2.add(apmcdata.records[i].min_price)
                                        list3.add(apmcdata.records[i].max_price)
                                        previousRecord = APMCCustomRecords(
                                            apmcdata.records[i].state,
                                            apmcdata.records[i].district,
                                            apmcdata.records[i].market,
                                            list1,
                                            list2,
                                            list3
                                        )
                                    }
                                }
                                if(count == 1){
                                    Log.d("LastRec", "Yes")
                                    customRecords.add(previousRecord)
                                }
                            }

                            Log.d("New APMC Data", customRecords.toString())
                            Log.d("Old APMC Data", apmcdata.toString())

                            adapter = ApmcAdapter(activity!!.applicationContext, customRecords)
                            recycleAPMC.adapter = adapter
                            recycleAPMC.layoutManager =
                                LinearLayoutManager(activity!!.applicationContext)

                            //temp.text=rootdata.weather.main.toString()
                            Log.d("bharat222", apmcdata.toString())
                        }

                    }
                }

            })

        }
    }
}