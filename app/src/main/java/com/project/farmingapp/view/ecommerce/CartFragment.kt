package com.project.farmingapp.view.ecommerce

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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
import com.project.farmingapp.viewmodel.EcommViewModel
import kotlinx.android.synthetic.main.fragment_cart.*
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

    private var param1: String? = null
    private var param2: String? = null
    var isOpened: Boolean = false
    var totalCount = 0
    var totalPrice = 0
    var items = HashMap<String, Object>()
    lateinit var ecommViewModel: EcommViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        ecommViewModel = EcommViewModel()
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
                        Log.d("Total Items", key.toString())
                        Log.d("Total Items", value.toString())
                        ecommViewModel.getSpecificItem("${key}")
                            .observe(viewLifecycleOwner, Observer {
                                totalCartPrice += currVal.get("quantity").toString()
                                    .toInt() * it.get("price").toString().toInt() + it.get("delCharge").toString().toInt()
                                Log.d("Total Price", currVal.get("quantity").toString())
                                Log.d("Total Price", it.get("price").toString())
                                Log.d("Total Price - 2", (currVal.get("quantity").toString().toInt()*it.get("price").toString().toInt()).toString())
                                totalItemsValue.text = items.size.toString()
                                totalCostValue.text = "\u20B9" + totalCartPrice.toString()
                            })
                        Log.d("Total Price - 3", key.toString())
                    }



                    if (isOpened == true) {
                        totalItemsValue.text = items.size.toString()
                        totalCostValue.text = "\u20B9" + totalCartPrice.toString()
                    }

                    val adapter =
                        CartItemsAdapter(this@CartFragment, items, this@CartFragment)
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
//            prePaymentfragment = PrePaymentFragment()
//            val bundle = Bundle()
//
//            val transaction = activity!!.supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.frame_layout, prePaymentfragment)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .setReorderingAllowed(true)
//                .addToBackStack("name")
//                .commit()
//val TotalPrice=totalCostValue.text.toString()
            //var products_id:ArrayList<String>
            // products_id.add()
//            Intent (activity!!.applicationContext, RazorPayActivity::class.java).also {
//                it.putExtra("tp",totalPrice)
//                it.putExtra()
//                startActivity(it)

            // }
        }
    }

    override fun addToOrders(productId: String, quantity: Int, itemCost: Int, deliveryCost: Int) {
        Intent(activity!!.applicationContext, RazorPayActivity::class.java).also {
            //  it.putExtra("tp", "123")
            it.putExtra("productId", productId)
            it.putExtra("itemCost", itemCost.toString())
            it.putExtra("quantity", quantity.toString())
            it.putExtra("deliveryCost", deliveryCost.toString())
            startActivity(it)
        }
    }
}