package com.project.farmingapp.view.weather

import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.farmingapp.R
import com.project.farmingapp.adapter.CurrentWeatherAdapter
import com.project.farmingapp.adapter.WeatherAdapter
import com.project.farmingapp.databinding.FragmentWeatherBinding
import com.project.farmingapp.model.WeatherApi
import com.project.farmingapp.model.data.Weather
import com.project.farmingapp.model.data.WeatherList
import com.project.farmingapp.model.data.WeatherRootList
import com.project.farmingapp.viewmodel.WeatherListener
import com.project.farmingapp.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : Fragment(), WeatherListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var Adapter: WeatherAdapter
    lateinit var Adapter2: CurrentWeatherAdapter
//    lateinit var viewModel: WeatherViewModel

//    private val viewModel: WeatherViewModel? = null
//
//    private val viewModel2 = WeatherViewModel()

    private lateinit var viewModel: WeatherViewModel

//    lateinit var data: WeatherRootList

    private var fragmentWeatherBinding: FragmentWeatherBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        // init ViewModel
        val viewModel = ViewModelProviders.of(requireActivity())
            .get<WeatherViewModel>(WeatherViewModel::class.java)

        val bundle = this.arguments
        if (bundle != null) {
            Log.d("WeatherFrag Bundle", bundle.getString("key"))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        val binding : WeatherFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)


        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)


//        val data =  WeatherViewModel().getWeather(this.context)
//        Adapter= WeatherAdapter(this.context!!, data.list)
//        rcylr_weather.adapter = Adapter
//        rcylr_weather.layoutManager= LinearLayoutManager(this.context!!)
//        Log.d("bharat",data.toString())
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val binding = FragmentWeatherBinding.bind(view)
//        fragmentWeatherBinding = binding

//        val viewModelRoutesFragment = ViewModelProvider(requireActivity()).get(
//            WeatherViewModel::class.java
//        )

//        val resp = getWeather()
//        Log.d("Fragment123", resp.toString())

//        viewModel!!.callWeatherRepository()
//        WeatherViewModel().callWeatherRepository()
//        Log.d("Frag", ss.value.toString())
//        binding.textView2.setOnClickListener {
//            Toast.makeText(it.context, "Something", Toast.LENGTH_LONG).show()
//        }


        // Direct


//        Adapter= WeatherAdapter(this.context!!, data.list)
//        rcylr_weather.adapter = Adapter
//        rcylr_weather.layoutManager= LinearLayoutManager(this.context!!)
//        Log.d("bharat",data.toString())

        viewModel = ViewModelProviders.of(requireActivity())
            .get<WeatherViewModel>(WeatherViewModel::class.java)
        viewModel.getMessageB()
            .observe(viewLifecycleOwner, object : Observer<WeatherRootList?> {
                override fun onChanged(t: WeatherRootList?) {
                    Log.d("DashFrag Data Changed B", t.toString())

                    var data: WeatherRootList? = t
                    var firstDate = data!!.list[0].dt_txt.slice(8..9)
                    var otherDates = firstDate
                    var i = 1
                    var data2 = mutableListOf<WeatherList>()

                    while (otherDates == firstDate) {
                        data2!!.add(data.list[i - 1])
                        otherDates = data.list[i].dt_txt.slice(8..9)
                        i += 1
                    }

//                    Log.d("Final OtherDates", data2.toString())
//                    Log.d("Fragment", data.list.size.toString())

                    var data3 = mutableListOf<WeatherList>()
                    for (a in i - 1..39) {
                        if (data.list[a].dt_txt.slice(11..12) == "12") {
                            Log.d("Something date", data.list[a].dt_txt)
                            data3.add(data.list[a])
                        }
                    }

//                    Log.d("New List", data3.toString())

                    Adapter = WeatherAdapter(activity!!.applicationContext, data3)
                    Adapter2 = CurrentWeatherAdapter(activity!!.applicationContext, data2)

                    rcylr_weather.adapter = Adapter
                    rcylr_weather.layoutManager = LinearLayoutManager(activity!!.applicationContext)

                    currentWeather_rcycl.adapter = Adapter2
                    currentWeather_rcycl.layoutManager = LinearLayoutManager(
                        activity!!.applicationContext,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
//                    Log.d("bharat", data.toString())
                }
            })
    }

    fun getWeather() {
        val response: retrofit2.Call<WeatherRootList> =
            WeatherApi.weatherInstances.getWeather("23.0225", "72.5714")

        var data: WeatherRootList? = null

        response.enqueue(object : Callback<WeatherRootList> {
            override fun onFailure(call: retrofit2.Call<WeatherRootList>, t: Throwable) {
                Log.d("WeatherRepository", "Error Occured")
            }

            override fun onResponse(
                call: retrofit2.Call<WeatherRootList>,
                response: Response<WeatherRootList>
            ) {
                if (response.isSuccessful) {
                    var data = response.body()!!


//                    var firstDate = data.list[0].dt_txt.slice(8..9)
//                    var otherDates = firstDate
//                    var i = 1
//                    var data2 = mutableListOf<WeatherList>()
//                    while (otherDates == firstDate){
//
//
//
//                        data2!!.add(data.list[i-1])
//
//
//                        otherDates = data.list[i].dt_txt.slice(8..9)
////                        Log.d("OtherDates", otherDates +" " +  i)
//                        i+=1
//                    }
//
//
//
//
//                    Log.d("Final OtherDates", data2.toString())
////                    for (jj in data.list){
////                        Log.d("List", jj.dt_txt.slice(8..9))
////                    }
//                    Log.d("Fragment", data.list.size.toString())
//
//                    var data3 = mutableListOf<WeatherList>()
//
//                    for (a in i-1..39){
//                        if(data.list[a].dt_txt.slice(11..12) == "12"){
//                            Log.d("Something date", data.list[a].dt_txt)
//                            data3.add(data.list[a])
//                        }
//                    }
//
//                    Log.d("New List", data3.toString())
//
//                    Adapter = WeatherAdapter(activity!!.applicationContext, data3)
//                    Adapter2 = CurrentWeatherAdapter(activity!!.applicationContext, data2)
//
//                    rcylr_weather.adapter = Adapter
//                    rcylr_weather.layoutManager = LinearLayoutManager(activity!!.applicationContext)
//
//                    currentWeather_rcycl.adapter = Adapter2
//                    currentWeather_rcycl.layoutManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.HORIZONTAL, false)
//                    Log.d("bharat", data.toString())

                } else {

                }
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WeatherFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onSuccess(authRepo: LiveData<String>) {
        authRepo.observe(this, Observer {
            Log.d("Frag", authRepo.value.toString())
        })
        Toast.makeText(this.context, "SS", Toast.LENGTH_LONG).show()
    }
}