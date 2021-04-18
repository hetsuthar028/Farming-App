package com.project.farmingapp.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.style.TypefaceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.project.farmingapp.R
import com.project.farmingapp.utilities.CellClickListener
import kotlinx.android.synthetic.main.single_selection_attributes_ecomm.view.*

class AttributesSelectionAdapter(var context: Context, var allData: List<Map<String, Any>>, private val cellClickListener: CellClickListener): RecyclerView.Adapter<AttributesSelectionAdapter.AttributesSelectionViewHolder>() {
    class AttributesSelectionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AttributesSelectionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_selection_attributes_ecomm, parent, false)
        return AttributesSelectionAdapter.AttributesSelectionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allData.size
    }

    override fun onBindViewHolder(holder: AttributesSelectionViewHolder, position: Int) {
        val currentData = allData[position] as Map<String, Any>


        for ((key, values) in currentData){

            cellClickListener.onCellClickListener("1 ${key}")

            holder.itemView.attributeTitle.text = key
            var allValues = values as ArrayList<String>
            var currentValue = allValues[0].toString().split(" ")
            holder.itemView.attribute1.text = currentValue[0].toString()
            holder.itemView.attribute1Price.text = currentValue[1].toString()

            currentValue = allValues[1].toString().split(" ")
            holder.itemView.attribute2.text = currentValue[0].toString()
            holder.itemView.attribute2Price.text = currentValue[1].toString()

            currentValue = allValues[2].toString().split(" ")
            holder.itemView.attribute3.text = currentValue[0].toString()
            holder.itemView.attribute3Price.text = currentValue[1].toString()

//            holder.itemView.attribute1.text = currentValue[0].toString()
//            holder.itemView.attribute1Price.text = currentValue[1].toString()

            holder.itemView.cardSize1.setOnClickListener {
                cellClickListener.onCellClickListener("1 ${key}")
                Toast.makeText(context, "You Clicked 1", Toast.LENGTH_SHORT).show()
                holder.itemView.cardSize1.backgroundTintList = context.getResources().getColorStateList(R.color.colorPrimary)
                holder.itemView.attribute1.setTextColor(Color.parseColor("#FFFFFF"))
                holder.itemView.attribute1Price.setTextColor(Color.parseColor("#FFFFFF"))
                holder.itemView.attribute1.setTypeface(null, Typeface.BOLD)
                holder.itemView.attribute1Price.setTypeface(null, Typeface.BOLD)

                holder.itemView.cardSize2.backgroundTintList = context.getResources().getColorStateList(R.color.secondary)
                holder.itemView.attribute2.setTextColor(Color.parseColor("#FF404A3A"))
                holder.itemView.attribute2Price.setTextColor(Color.parseColor("#FF404A3A"))
                holder.itemView.attribute2.setTypeface(null, Typeface.NORMAL)
                holder.itemView.attribute2Price.setTypeface(null, Typeface.NORMAL)

                holder.itemView.cardSize3.backgroundTintList = context.getResources().getColorStateList(R.color.secondary)
                holder.itemView.attribute3.setTextColor(Color.parseColor("#FF404A3A"))
                holder.itemView.attribute3Price.setTextColor(Color.parseColor("#FF404A3A"))
                holder.itemView.attribute3.setTypeface(null, Typeface.NORMAL)
                holder.itemView.attribute3Price.setTypeface(null, Typeface.NORMAL)
            }

            holder.itemView.cardSize2.setOnClickListener {
                cellClickListener.onCellClickListener("2 ${key}")


                holder.itemView.cardSize2.backgroundTintList = context.getResources().getColorStateList(R.color.colorPrimary)
                holder.itemView.attribute2.setTextColor(Color.parseColor("#FFFFFF"))
                holder.itemView.attribute2Price.setTextColor(Color.parseColor("#FFFFFF"))
                holder.itemView.attribute2.setTypeface(null, Typeface.BOLD)
                holder.itemView.attribute2Price.setTypeface(null, Typeface.BOLD)

                holder.itemView.cardSize3.backgroundTintList = context.getResources().getColorStateList(R.color.secondary)
                holder.itemView.attribute3.setTextColor(Color.parseColor("#FF404A3A"))
                holder.itemView.attribute3Price.setTextColor(Color.parseColor("#FF404A3A"))
                holder.itemView.attribute3.setTypeface(null, Typeface.NORMAL)
                holder.itemView.attribute3Price.setTypeface(null, Typeface.NORMAL)

                holder.itemView.cardSize1.backgroundTintList = context.getResources().getColorStateList(R.color.secondary)
                holder.itemView.attribute1.setTextColor(Color.parseColor("#FF404A3A"))
                holder.itemView.attribute1Price.setTextColor(Color.parseColor("#FF404A3A"))
                holder.itemView.attribute1.setTypeface(null, Typeface.NORMAL)
                holder.itemView.attribute1Price.setTypeface(null, Typeface.NORMAL)

                Toast.makeText(context, "You Clicked 2", Toast.LENGTH_SHORT).show()
            }

            holder.itemView.cardSize3.setOnClickListener {
                cellClickListener.onCellClickListener("3 ${key}")
                Toast.makeText(context, "You Clicked 3", Toast.LENGTH_SHORT).show()

                holder.itemView.cardSize3.backgroundTintList = context.getResources().getColorStateList(R.color.colorPrimary)
                holder.itemView.attribute3.setTextColor(Color.parseColor("#FFFFFF"))
                holder.itemView.attribute3Price.setTextColor(Color.parseColor("#FFFFFF"))
                holder.itemView.attribute3.setTypeface(null, Typeface.BOLD)
                holder.itemView.attribute3Price.setTypeface(null, Typeface.BOLD)

                holder.itemView.cardSize1.backgroundTintList = context.getResources().getColorStateList(R.color.secondary)
                holder.itemView.attribute1.setTextColor(Color.parseColor("#FF404A3A"))
                holder.itemView.attribute1Price.setTextColor(Color.parseColor("#FF404A3A"))
                holder.itemView.attribute1.setTypeface(null, Typeface.NORMAL)
                holder.itemView.attribute1Price.setTypeface(null, Typeface.NORMAL)

                holder.itemView.cardSize2.backgroundTintList = context.getResources().getColorStateList(R.color.secondary)
                holder.itemView.attribute2.setTextColor(Color.parseColor("#FF404A3A"))
                holder.itemView.attribute2Price.setTextColor(Color.parseColor("#FF404A3A"))
                holder.itemView.attribute2.setTypeface(null, Typeface.NORMAL)
                holder.itemView.attribute2Price.setTypeface(null, Typeface.NORMAL)
            }
        }
    }
}