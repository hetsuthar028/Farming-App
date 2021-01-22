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

class WeatherAdapter(val context: Context, val weatherrootdatas:List<WeatherList>): RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>(){
    class WeatherViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var wedate=itemView.findViewById<TextView>(R.id.weather_date)
        var wedesc=itemView.findViewById<TextView>(R.id.weather_description)
        var wemain=itemView.findViewById<TextView>(R.id.weather_main)
        var welogo=itemView.findViewById<ImageView>(R.id.weather_logo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.single_weather,parent,false)
        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return weatherrootdatas.size
    }


    override fun onBindViewHolder(holder: WeatherAdapter.WeatherViewHolder, position: Int) {
        val weathernew =weatherrootdatas[position]
        val we=weathernew.weather[0]
        val we2=weathernew.main
        holder.wedate.text=weathernew.dt_txt
        holder.wedesc.text=we.description
        val Temp=we2.temp-273.15
        holder.wemain.text=Temp.toInt().toString()
    }
}