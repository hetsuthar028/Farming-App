package com.project.farmingapp.adapter

import android.content.Context
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.project.farmingapp.R
import com.project.farmingapp.utilities.CartItemBuy
import kotlinx.android.synthetic.main.single_cart_item.*
import kotlinx.android.synthetic.main.single_cart_item.view.*

class CartItemsAdapter(val context: Context, val allData: HashMap<String, Object>,val cartitembuy:CartItemBuy) :
    RecyclerView.Adapter<CartItemsAdapter.CartItemsViewHolder>() {
    class CartItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_cart_item, parent, false)
        return CartItemsAdapter.CartItemsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allData.size
    }

    override fun onBindViewHolder(holder: CartItemsViewHolder, position: Int) {
        val currentData = allData.entries.toTypedArray()[position]
//        Log.d("CartFrag", currentData[0].value.toString())

        val firebaseFirestore = FirebaseFirestore.getInstance()
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val firebaseAuth = FirebaseAuth.getInstance()

        val itemQtyRef =
            firebaseDatabase.getReference("${firebaseAuth.currentUser!!.uid}").child("cart")
                .child("${currentData.key}").child("qty")

        val itemRef =
            firebaseDatabase.getReference("${firebaseAuth.currentUser!!.uid}").child("cart")
                .child("${currentData.key}")

holder.itemView.cartItemBuyBtn.setOnClickListener {
    var qty=holder.itemView.quantityCountEcomm.text.toString().toInt()
var totalPrice=holder.itemView.cartItemBuyBtn.text.toString().split("₹") as ArrayList<String>
    Log.d("totalPrice",totalPrice[1].toString())
cartitembuy.addToOrders("${currentData.key}",qty,totalPrice[1].toString().toInt())
}

        holder.itemView.removeCartBtn.setOnClickListener {
            itemRef.removeValue()
        }

        holder.itemView.increaseQtyBtn.setOnClickListener {

            holder.itemView.quantityCountEcomm.text =
                (holder.itemView.quantityCountEcomm.text.toString().toInt() + 1).toString()
            itemQtyRef.setValue(holder.itemView.quantityCountEcomm.text.toString().toInt())
        }

        holder.itemView.decreaseQtyBtn.setOnClickListener {
            if (holder.itemView.quantityCountEcomm.text.toString().toInt() != 1) {
                holder.itemView.quantityCountEcomm.text =
                    (holder.itemView.quantityCountEcomm.text.toString().toInt() - 1).toString()
                itemQtyRef.setValue(holder.itemView.quantityCountEcomm.text.toString().toInt())
            }
        }

        val curr = currentData.value as Map<String, Object>
        firebaseFirestore.collection("products").document("${currentData.key}")
            .get()
            .addOnCompleteListener {
                holder.itemView.itemNameCart.text = it.result!!.get("title").toString()
                holder.itemView.itemPriceCart.text = "\u20B9" + it.result!!.getString("price")
                holder.itemView.deliveryChargeCart.text =
                    "Delivery Charge: " + "\u20B9" + it.result!!.getString("delCharge")
                holder.itemView.cartItemAvailability.text = it.result!!.getString("availability")
                holder.itemView.cartItemFirm.text = "- " + it.result!!.getString("retailer")
                holder.itemView.quantityCountEcomm.text = curr.get("qty").toString()

                holder.itemView.cartItemBuyBtn.text =
                    "Buy Now: " + "\u20B9" + (curr.get("qty").toString()
                        .toInt() * it.result!!.get("price").toString()
                        .toInt() + it.result!!.getString("delCharge")!!.toInt()).toString()
                val allImages = it.result!!.get("imageUrl") as ArrayList<String>
                Glide.with(context).load(allImages[0]).into(holder.itemView.cartItemImage)




            }
    }

}