package com.project.farmingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.farmingapp.R
import com.project.farmingapp.model.data.WeatherList

class CurrentWeatherAdapter(val context: Context, val weatherrootdatas:List<WeatherList>):
    RecyclerView.Adapter<CurrentWeatherAdapter.CurrentWeatherViewHolder>() {
    class CurrentWeatherViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var Ctemp=itemView.findViewById<TextView>(R.id.temp)
        var Cwedesc=itemView.findViewById<TextView>(R.id.desc)
        var Cwelogo=itemView.findViewById<ImageView>(R.id.icon)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrentWeatherAdapter.CurrentWeatherViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.single_currentweather,parent,false)
        return CurrentWeatherAdapter.CurrentWeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CurrentWeatherAdapter.CurrentWeatherViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}