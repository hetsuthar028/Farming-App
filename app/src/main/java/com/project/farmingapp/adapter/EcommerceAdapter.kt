package com.project.farmingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.project.farmingapp.R
import com.project.farmingapp.utilities.CellClickListener
import kotlinx.android.synthetic.main.post_with_image_sm.view.*
import kotlinx.android.synthetic.main.single_ecomm_item.view.*

class EcommerceAdapter(val context: Context, val ecommtListData : List<DocumentSnapshot>, private val cellClickListener: CellClickListener):RecyclerView.Adapter<EcommerceAdapter.EcommercceViewModel>() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseFirestore: FirebaseFirestore
    class EcommercceViewModel(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EcommerceAdapter.EcommercceViewModel {
        val view = LayoutInflater.from(context).inflate(R.layout.single_ecomm_item, parent, false)
        return EcommerceAdapter.EcommercceViewModel(view)
    }

    override fun getItemCount(): Int {
        return ecommtListData.size
    }

    override fun onBindViewHolder(holder: EcommerceAdapter.EcommercceViewModel, position: Int) {
        val currentList = ecommtListData[position]
        holder.itemView.ecommtitle.text = currentList.get("title").toString()
        holder.itemView.ecommPrice.text = "\u20B9 "+currentList.get("price").toString()
        holder.itemView.ecommretailer.text = currentList.get("retailer").toString()
        holder.itemView.ecommItemAvailability.text = currentList.get("availability").toString()
        val allImages = currentList.get("imageUrl") as List<String>
        Glide.with(context).load(allImages[0].toString()).into(holder.itemView.ecommImage)
        holder.itemView.ecommRating.rating = currentList.get("rating").toString().toFloat()

       holder.itemView.setOnClickListener {
           cellClickListener.onCellClickListener(currentList.id.toString())
       }

    }
}