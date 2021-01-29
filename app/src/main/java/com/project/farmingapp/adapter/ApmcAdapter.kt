package com.project.farmingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.farmingapp.R
import com.project.farmingapp.model.data.APMCMain
import com.project.farmingapp.model.data.APMCRecords

class ApmcAdapter(val context: Context,val data:List<APMCRecords>):RecyclerView.Adapter<ApmcAdapter.ApmcViewHolder>() {
    class ApmcViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        var market=itemView.findViewById<TextView>(R.id.marketValueTextApmc)
        var location=itemView.findViewById<TextView>(R.id.locationValueTextApmc)
        var commodity=itemView.findViewById<TextView>(R.id.commodityValueTextApmc)
        var min=itemView.findViewById<TextView>(R.id.minValueTextApmc)
        var max=itemView.findViewById<TextView>(R.id.maxValueTextApmc)
        var modal=itemView.findViewById<TextView>(R.id.modalValueTextApmc)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApmcViewHolder {
        val view =LayoutInflater.from(context).inflate(R.layout.apmc_single_list,parent,false)
    return ApmcViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ApmcViewHolder, position: Int) {
        val mainData=data[position]
        holder.market.text=mainData.market
        holder.location.text=mainData.district + " , "+mainData.state
        holder.commodity.text=mainData.commodity
        holder.min.text=mainData.min_price
        holder.max.text=mainData.max_price
        holder.modal.text=mainData.modal_price

    }
}