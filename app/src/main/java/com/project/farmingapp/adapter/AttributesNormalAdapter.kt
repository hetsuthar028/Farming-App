package com.project.farmingapp.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.farmingapp.R
import kotlinx.android.synthetic.main.single_normal_attributes_ecomm.view.*

class AttributesNormalAdapter(val context: Context, val allData : List<Map<String, Any>>): RecyclerView.Adapter<AttributesNormalAdapter.AttributesNormalViewHolder>() {
    class AttributesNormalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttributesNormalViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_normal_attributes_ecomm, parent, false)
        return AttributesNormalAdapter.AttributesNormalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allData.size
    }

    override fun onBindViewHolder(holder: AttributesNormalViewHolder, position: Int) {
        val currentData = allData[position]
        for((key, value) in currentData){
            holder.itemView.normalAttributeTitle.text = key.toString() + " - "
            holder.itemView.normalAttributeValue.text = value.toString()
        }
    }
}