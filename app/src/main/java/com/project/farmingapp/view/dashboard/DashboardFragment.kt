package com.project.farmingapp.view.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.project.farmingapp.R
import com.project.farmingapp.model.WeatherApi
import com.project.farmingapp.model.data.WeatherRootList
import com.project.farmingapp.view.weather.WeatherFragment
import com.project.farmingapp.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [dashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class dashboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var weatherFragment: WeatherFragment
    private lateinit var viewModel: WeatherViewModel
    var data: WeatherRootList? = null
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
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherCard.setOnClickListener {
            weatherFragment = WeatherFragment()

            val transaction = activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_layout, weatherFragment, "name2")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit()
            data?.let { it1 -> viewModel.messageToB(it1) }
        }
        getWeather()

        viewModel = ViewModelProviders.of(requireActivity())
            .get<WeatherViewModel>(WeatherViewModel::class.java)

        viewModel.getMessageA()
            .observe(viewLifecycleOwner, object : Observer<WeatherRootList?> {
                override fun onChanged(t: WeatherRootList?) {
                    Log.d("DashFrag Data Changed A", "B")
                }
            })

//        articlesTitle.setOnClickListener {
//            viewModel.messageToB()
//        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment dashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            dashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun getWeather() {
        val response: Call<WeatherRootList> =
            WeatherApi.weatherInstances.getWeather("23.0225", "72.5714")

        response.enqueue(object : Callback<WeatherRootList> {
            override fun onFailure(call: Call<WeatherRootList>, t: Throwable) {
                Log.d("WeatherRepository", "Error Occured")
            }

            override fun onResponse(
                call: Call<WeatherRootList>,
                response: Response<WeatherRootList>
            ) {
                if (response.isSuccessful) {
                    data = response.body()!!
                    Log.d("Dashboard Fragment", data.toString())
                    weathTempTextWeathFrag.text =
                        (data!!.list[0].main.temp - 273).toInt().toString() + "\u2103"

                    humidityTextWeathFrag.text =
                        "Humidity: " + data!!.list[0].main.humidity.toString() + " %"
                    windTextWeathFrag.text =
                        "Wind: " + data!!.list[0].wind.speed.toString() + " km/hr"

                    var iconcode = data!!.list[0].weather[0].icon
                    var iconurl = "https://openweathermap.org/img/w/" + iconcode + ".png";
                    Glide.with(activity!!.applicationContext).load(iconurl)
                        .into(weathIconImageWeathFrag)
                }
            }
        })
    }
}