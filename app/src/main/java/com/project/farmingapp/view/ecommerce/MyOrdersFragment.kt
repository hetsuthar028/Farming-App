package com.project.farmingapp.view.ecommerce

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.project.farmingapp.R
import com.project.farmingapp.adapter.MyOrdersAdapter
import com.project.farmingapp.utilities.CartItemBuy
import com.project.farmingapp.utilities.CellClickListener
import kotlinx.android.synthetic.main.fragment_my_orders.*
import kotlin.collections.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyOrdersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyOrdersFragment : Fragment(), CellClickListener, CartItemBuy {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseFirestore: FirebaseFirestore

    var orders = HashMap<String, Object>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_orders, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyOrdersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyOrdersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orderRef = firebaseDatabase.getReference("${firebaseAuth.currentUser!!.uid}").child("orders")

        (activity as AppCompatActivity).supportActionBar?.title = "My Orders"

        val orderListener = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    orders = snapshot.value as HashMap<String, Object>
                    var myOrdersAdapter = MyOrdersAdapter(this@MyOrdersFragment, orders, this@MyOrdersFragment, this@MyOrdersFragment)
                    myOrderRecycler.adapter = myOrdersAdapter
                    myOrderRecycler.layoutManager = LinearLayoutManager(activity!!.applicationContext)
                }
            }
        }

        orderRef.addValueEventListener(orderListener)

    }

    override fun onCellClickListener(name: String) {
        val ecommerceItemFragment = EcommerceItemFragment()
        val transaction = activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, ecommerceItemFragment, name)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .setReorderingAllowed(true)
            .addToBackStack("ecommItem")
            .commit()
    }

    override fun addToOrders(productId: String, quantity: Int, itemCost: Int, deliveryCost: Int) {
        Intent(activity!!.applicationContext, RazorPayActivity::class.java).also {
            it.putExtra("productId", productId)
            it.putExtra("itemCost", itemCost.toString())
            it.putExtra("quantity", quantity.toString())
            it.putExtra("deliveryCost", deliveryCost.toString())
            startActivity(it)
        }
    }
}