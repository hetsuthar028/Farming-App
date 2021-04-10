package com.project.farmingapp.view.ecommerce

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.api.Distribution
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.DocumentSnapshot
import com.project.farmingapp.R
import com.project.farmingapp.adapter.CartItemsAdapter
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.single_cart_item.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val firebaseAuth = FirebaseAuth.getInstance()
        val cartRef = firebaseDatabase.getReference("${firebaseAuth.currentUser!!.uid}").child("cart")

        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val items = dataSnapshot.value as Map<String, Object>

                var totalCartPrice = 0
                for((key, value ) in items){
                    val currVal = value as Map<String, Object>
                    totalCartPrice += currVal.get("qty").toString().toInt() * currVal.get("basePrice").toString().toInt()
                }


                totalItemsValue.text = items.size.toString()
                totalCostValue.text = "\u20B9" +  totalCartPrice.toString()
                val adapter = CartItemsAdapter(activity!!.applicationContext, items)
                recyclerCart.adapter = adapter
                recyclerCart.layoutManager = LinearLayoutManager(activity!!.applicationContext)
                // ...
            }
        }


        cartRef.addValueEventListener(postListener)

//        cartRef.get().addOnCompleteListener {
//            Log.d("CartFrag", it.result!!.value.toString())
//            val data = it.result!!.value as Map<String, Object>
//            Log.d("CartFrag", data.toString())
////            if(it.result!!.value is List<>){
////                Log.d("CartFrag", "Yes")
////            }
//
////            val adapter = CartItemsAdapter(activity!!.applicationContext, data)
////            recyclerCart.adapter = adapter
////            recyclerCart.layoutManager = LinearLayoutManager(activity!!.applicationContext)
//
//            val currentData = data.entries.toTypedArray()
//            Log.d("CartFrag2", currentData[0].toString())
//
//
//
//            val curr = currentData[0].value as Map<String, Object>
//            Log.d("CartFrag3", curr.get("qty").toString())
//
//        }


//        cartRef.get()


        buyAllBtn.setOnClickListener {
            
        }


    }


}