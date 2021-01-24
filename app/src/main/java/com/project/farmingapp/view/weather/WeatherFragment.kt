package com.project.farmingapp.view.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.farmingapp.R
import com.project.farmingapp.adapter.WeatherAdapter
import com.project.farmingapp.databinding.FragmentWeatherBinding
import com.project.farmingapp.model.WeatherApi
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

        // init ViewModel
        val viewModel = ViewModelProviders.of(requireActivity())
            .get<WeatherViewModel>(WeatherViewModel::class.java)


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

        val resp = getWeather()
        Log.d("Fragment123", resp.toString())

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

    }

    fun getWeather(){
        val response: Call<WeatherRootList> =
            WeatherApi.weatherInstances.getWeather("27.2046", "77.4977")

        var data: WeatherRootList? = null

        response.enqueue(object : Callback<WeatherRootList> {
            override fun onFailure(call: Call<WeatherRootList>, t: Throwable) {
                Log.d("WeatherRepository", "Error Occured")
            }

            override fun onResponse(
                call: Call<WeatherRootList>,
                response: Response<WeatherRootList>
            ) {
                if (response.isSuccessful) {
                    var data = response.body()!!
                    Adapter = WeatherAdapter(activity!!.applicationContext, data.list)
                    rcylr_weather.adapter = Adapter
                    rcylr_weather.layoutManager = LinearLayoutManager(activity!!.applicationContext)
                    Log.d("bharat", data.toString())

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