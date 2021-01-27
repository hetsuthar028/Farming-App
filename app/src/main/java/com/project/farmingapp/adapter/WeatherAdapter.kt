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
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class WeatherAdapter(val context: Context, val weatherrootdatas:List<WeatherList>): RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>(){
    class WeatherViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var wedate=itemView.findViewById<TextView>(R.id.weatherDate)
        var wedesc=itemView.findViewById<TextView>(R.id.weatherDescription)
        var wemain=itemView.findViewById<TextView>(R.id.weatherTemperature)
        var welogo=itemView.findViewById<ImageView>(R.id.weatherIcon)

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

        val inputFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat = SimpleDateFormat("dd/MM/yyyy")

        val date: Date = inputFormat.parse(weathernew.dt_txt.slice(0..9))
        val outputDate = outputFormat.format(date)



        Log.d("New Date", outputDate.toString())

        val we=weathernew.weather[0]
        val we2=weathernew.main
        holder.wedate.text= outputDate
        holder.wedesc.text=we.description.capitalize()
        Log.d("weatherTemp", we2.temp.toString())
        val Temp=we2.temp//-273.15
        holder.wemain.text= Temp.toInt().toString()

        var iconcode=weathernew.weather[0].icon.toString()

        var iconurl = "https://openweathermap.org/img/w/" + iconcode + ".png";
        Log.d("weatherlogo", iconcode.toString())
        Glide.with(holder.itemView.context)
            .load(iconurl)
            .into(holder.welogo)
    }
}