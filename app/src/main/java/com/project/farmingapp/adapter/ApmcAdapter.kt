package com.project.farmingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.farmingapp.R
import com.project.farmingapp.model.data.APMCCustomRecords
import com.project.farmingapp.model.data.APMCMain
import com.project.farmingapp.model.data.APMCRecords

class ApmcAdapter(val context: Context, val data: List<APMCCustomRecords>) :
    RecyclerView.Adapter<ApmcAdapter.ApmcViewHolder>() {
    class ApmcViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var market = itemView.findViewById<TextView>(R.id.apmcNameValue)
        var location = itemView.findViewById<TextView>(R.id.apmcLocationValue)
        var commodity = itemView.findViewById<TextView>(R.id.comodityname)
        var min = itemView.findViewById<TextView>(R.id.minvalue)
        var max = itemView.findViewById<TextView>(R.id.maxvalue)
//        var modal=itemView.findViewById<TextView>(R.id.modalValueTextApmc)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApmcViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.apmc_single_list, parent, false)
        return ApmcViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ApmcViewHolder, position: Int) {
        val mainData = data[position]
        holder.market.text = mainData.market
        holder.location.text = mainData.district + " , " + mainData.state
        for (i in mainData.commodity) {
            holder.commodity.text = holder.commodity.text .toString()+ i + "\n"
        }
        holder.commodity.text = holder.commodity.text.toString().trimEnd()

        for (i in mainData.min_price){
            holder.min.text = holder.min.text.toString() + i + "\n"
        }
        holder.min.text = holder.min.text.toString().trimEnd()

        for(i in mainData.max_price){
            holder.max.text = holder.max.text.toString() + i + "\n"
        }
        holder.max.text = holder.max.text.toString().trimEnd()

//        holder.modal.text=mainData.modal_price

    }
}