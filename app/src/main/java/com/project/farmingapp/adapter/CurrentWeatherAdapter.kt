package com.project.farmingapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.farmingapp.R
import com.project.farmingapp.model.data.WeatherList

class CurrentWeatherAdapter(val context: Context, val weatherrootdatas:List<WeatherList>):
    RecyclerView.Adapter<CurrentWeatherAdapter.CurrentWeatherViewHolder>() {
    class CurrentWeatherViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var Ctemp=itemView.findViewById<TextView>(R.id.temp)
        var Cwedesc=itemView.findViewById<TextView>(R.id.desc)
        var Cwelogo=itemView.findViewById<ImageView>(R.id.icon)
        var CminTemp=itemView.findViewById<TextView>(R.id.minTemp)
        var CmaxTemp=itemView.findViewById<TextView>(R.id.maxTemp)
        var Chumidity=itemView.findViewById<TextView>(R.id.humidity)
        var CtodayTitle=itemView.findViewById<TextView>(R.id.todayTitle)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrentWeatherAdapter.CurrentWeatherViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.single_currentweather,parent,false)
        return CurrentWeatherAdapter.CurrentWeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return weatherrootdatas.size
    }

    override fun onBindViewHolder(holder: CurrentWeatherAdapter.CurrentWeatherViewHolder, position: Int) {
        val weathernew =weatherrootdatas[position]
        holder.Ctemp.text = (weathernew.main.temp - 273.15).toInt().toString() + "\u2103"
        holder.Cwedesc.text = weathernew.weather[0].description.toString()

        holder.CtodayTitle.text = "Today " + weathernew.dt_txt.toString().slice(10..15)

        Log.d("Something", weathernew.dt_txt.toString().slice(10..-1))

//        var tempMin = ""
//        for(a in weathernew.main.temp_min){
//        }
        var ss = weathernew.main.temp_min.length
        holder.CminTemp.text = (weathernew.main.temp_min.toDouble() - 273.1).toInt().toString()+ "\u2103"

        holder.CmaxTemp.text = (weathernew.main.temp_max.toDouble() - 273.1).toInt().toString() + "\u2103"
        holder.Chumidity.text = weathernew.main.humidity.toString()
        var iconcode=weathernew.weather[0].icon.toString()
        var iconurl = "https://openweathermap.org/img/w/" + iconcode + ".png";

        Glide.with(holder.itemView.context)
            .load(iconurl)
            .into(holder.Cwelogo)
    }
}