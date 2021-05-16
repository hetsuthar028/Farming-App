package com.project.farmingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentSnapshot
import com.project.farmingapp.R
import com.project.farmingapp.utilities.CellClickListener

class YojnaAdapter(val context: Context, val yojnaData: List<DocumentSnapshot>, private val cellClickListener: CellClickListener): RecyclerView.Adapter<YojnaAdapter.YojnaListviewHolder>() {
    class YojnaListviewHolder(itemview: View):RecyclerView.ViewHolder(itemview) {
        var yojnaName = itemView.findViewById<TextView>(R.id.yojnatitle)
        var yojnaImg = itemView.findViewById<ImageView>(R.id.yojnaImg)
        var date = itemView.findViewById<TextView>(R.id.yojnaDate)
        var yojnaCard = itemView.findViewById<CardView>(R.id.singlelistyojnacard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YojnaListviewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_yojna_list, parent, false)
        return YojnaAdapter.YojnaListviewHolder(view)
    }

    override fun getItemCount(): Int {
        return yojnaData.size
    }

    override fun onBindViewHolder(holder: YojnaListviewHolder, position: Int) {
      val singleYojna=yojnaData[position]
        holder.yojnaName.text = singleYojna.data!!.get("title").toString()
        holder.yojnaCard.setOnClickListener {
            cellClickListener.onCellClickListener(singleYojna.data!!.get("title").toString())
        }
        val url = singleYojna.data!!.get("title").toString()
        Glide.with(holder.itemView.context)
            .load(url)
            .into(holder.yojnaImg)
    }


}