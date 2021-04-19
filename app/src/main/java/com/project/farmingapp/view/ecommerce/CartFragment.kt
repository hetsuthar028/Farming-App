package com.project.farmingapp.view.ecommerce

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.farmingapp.PrePaymentFragment
import com.project.farmingapp.R
import com.project.farmingapp.adapter.CartItemsAdapter
import com.project.farmingapp.utilities.CartItemBuy
import kotlinx.android.synthetic.main.fragment_cart.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment(), CartItemBuy {

    lateinit var prePaymentfragment: PrePaymentFragment

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var isOpened: Boolean = false
    var totalCount = 0
    var totalPrice = 0
    var items = HashMap<String, Object>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

//        val navView: NavigationView =
//        supportA?.setDisplayHomeAsUpEnabled(true)


//        isOpened = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
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
        val cartRef =
            firebaseDatabase.getReference("${firebaseAuth.currentUser!!.uid}").child("cart")

        (activity as AppCompatActivity).supportActionBar?.title = "Cart"
        isOpened = true
        Log.d("Cart6", isOpened.toString())

//        val header = navView.getHeaderView(0);
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {

                    items = dataSnapshot.value as HashMap<String, Object>

                    var totalCartPrice = 0
                    for ((key, value) in items) {
                        val currVal = value as Map<String, Object>
                        Log.d("Cart3", value.toString())
                        totalCartPrice += currVal.get("qty").toString()
                            .toInt() * currVal.get("basePrice").toString().toInt()
                    }

                    if (isOpened == true) {
                        Log.d("Cart5", isOpened.toString())
                        totalItemsValue.text = items.size.toString()
                        totalCostValue.text = "\u20B9" + totalCartPrice.toString()

                    }
                    Log.d("Cart4", items.size.toString())

                    val adapter =
                        CartItemsAdapter(activity!!.applicationContext, items, this@CartFragment)
                    recyclerCart.adapter = adapter
                    recyclerCart.layoutManager = LinearLayoutManager(activity!!.applicationContext)
                    progress_cart.visibility = View.GONE
                    loadingTitleText.visibility = View.GONE

                } else {
                    Toast.makeText(
                        activity!!.applicationContext,
                        "Item Not Exist",
                        Toast.LENGTH_SHORT
                    ).show()
                    progress_cart.visibility = View.GONE
                    loadingTitleText.visibility = View.GONE
                }
            }
        }


        cartRef.addValueEventListener(postListener)
        buyAllBtn.setOnClickListener {

        }
    }

    override fun addToOrders(productId: String, qty: Int, totalPrice: Int) {
        var product_id = ArrayList<String>()
        var item_cost = ArrayList<Int>()
        var item_qty = ArrayList<Int>()
        product_id.add(productId)
        item_cost.add(totalPrice)
        item_qty.add(qty)
        Intent(activity!!.applicationContext, RazorPayActivity::class.java).also {
            //  it.putExtra("tp", "123")
            it.putStringArrayListExtra("products_id", product_id)
            it.putIntegerArrayListExtra("items_cost", item_cost)
            it.putIntegerArrayListExtra("items_qty", item_qty)
            startActivity(it)
        }
    }

}