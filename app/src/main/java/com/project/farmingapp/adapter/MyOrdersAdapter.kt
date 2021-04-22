package com.project.farmingapp.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.farmingapp.R
import com.project.farmingapp.utilities.CartItemBuy
import com.project.farmingapp.utilities.CellClickListener
import com.project.farmingapp.view.ecommerce.MyOrdersFragment
import com.project.farmingapp.view.ecommerce.RazorPayActivity
import com.project.farmingapp.viewmodel.EcommViewModel
import kotlinx.android.synthetic.main.single_myorder_item.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap

class MyOrdersAdapter(
    val context: MyOrdersFragment,
    val allData: HashMap<String, Object>,
    val cellClickListener: CellClickListener,
    val cartItemBuy: CartItemBuy
) : RecyclerView.Adapter<MyOrdersAdapter.MyOrdersViewHolder>() {
    class MyOrdersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrdersViewHolder {
        val view = LayoutInflater.from(context.context)
            .inflate(R.layout.single_myorder_item, parent, false)
        return MyOrdersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allData.size
    }

    override fun onBindViewHolder(holder: MyOrdersViewHolder, position: Int) {
        val myOrder = allData.entries.toTypedArray()[position].value as HashMap<String, Object>
        val viewModel = EcommViewModel()

        val currentItemAllData = viewModel.getSpecificItem("${myOrder.get("productId")}")
            .observe(context, androidx.lifecycle.Observer {
                Log.d("MyOrdersAdapter", it.toString())
                holder.itemView.myOrderPinCode.text = myOrder.get("pincode").toString()
                holder.itemView.myOrderItemName.text = it!!.getString("title")
                holder.itemView.myOrderItemPrice.text =
                    "\u20B9" + (myOrder.get("quantity").toString()
                        .toInt() * myOrder.get("itemCost").toString()
                        .toInt() + myOrder.get("deliveryCost").toString().toInt()).toString()
                holder.itemView.myOrderPinCode.text =
                    "Pin Code: " + myOrder.get("pincode").toString()
                holder.itemView.myOderDeliveryCharge.text = myOrder.get("deliveryCost").toString()
                holder.itemView.myOrderDeliveryStatus.text =
                    myOrder.get("deliveryStatus").toString()
                val allImages = it.get("imageUrl") as List<String>
                Glide.with(context).load(allImages[0]).into(holder.itemView.myOderItemImage)
                val date = myOrder.get("time").toString().split(" ") as List<String>
                holder.itemView.myOrderTimeStamp.text = date[0].toString()
            })
        holder.itemView.myOrderPurchaseAgain.setOnClickListener {
            cartItemBuy.addToOrders(myOrder.get("productId").toString(), myOrder.get("quantity").toString().toInt(), myOrder.get("itemCost").toString().toInt(), myOrder.get("deliveryCost").toString().toInt())
        }
        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener("${myOrder.get("productId")}")
        }
    }
}