package com.project.farmingapp.view.ecommerce

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.project.farmingapp.R
import com.project.farmingapp.model.data.orders
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.activity_razor_pay.*
import kotlinx.android.synthetic.main.fragment_ecommerce_item.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RazorPayActivity : AppCompatActivity(), PaymentResultListener {
    lateinit var firebaseAuth: FirebaseAuth
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

    var postId: UUID? = null
    var name: String = ""
    var locality: String = ""
    var city: String = ""
    var state: String = ""
    var pincode: String = ""
    var mobile: String = ""
    var currentDate = sdf.format(Date())
    lateinit var realtimeDatabase: FirebaseDatabase
    var products_id: ArrayList<String>? = null
    var totalPrice = 0
    var items_cost: ArrayList<Int>? = null
    var items_qty: ArrayList<Int>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_razor_pay)
        postId = UUID.randomUUID()

        //val totalCost=intent.getStringExtra("tp")
        firebaseAuth = FirebaseAuth.getInstance()
        products_id = intent.getStringArrayListExtra("products_id")
        items_cost = intent.getIntegerArrayListExtra("items_cost")
        items_qty = intent.getIntegerArrayListExtra("items_qty")
        Log.d("tp", products_id.toString())
        Log.d("tp", items_cost.toString())
        Log.d("tp", items_qty.toString())
        // Toast.makeText(this,abc,Toast.LENGTH_LONG).show()
//Log.d("tp",abc.toString())
        orderNowBtn.setOnClickListener {
            name = fullNamePrePay.text.toString()
             locality = localityPrePay.text.toString()
             city = cityPrePay.text.toString()
             state = statePrePay.text.toString()
             pincode = pincodePrePay.text.toString()
             mobile = mobileNumberPrePay.text.toString()
            if (name.isNullOrEmpty() ||
                locality.isNullOrEmpty() ||
                city.isNullOrEmpty() ||
                state.isNullOrEmpty() ||
                pincode.isNullOrEmpty() ||
                mobile.isNullOrEmpty()
            ) {
                Toast.makeText(this, "Please Add all Fields", Toast.LENGTH_LONG).show()
            }

            //add inputfield validation

            else {
                //add inputfield validation
                Toast.makeText(this, "Done", Toast.LENGTH_LONG).show()
                startPayment()
            }
        }

        for(i in items_cost!!){
            totalPrice += i
        }

        netValue.text = "Net Value: ₹ " + totalPrice.toString()
    }

    private fun startPayment() {
/*
* You need to pass current activity in order to let Razorpay create CheckoutActivity
* */
        val activity: Activity = this
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name", "FarmingApp")
            options.put("description", "Demoing Charges")

            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("currency", "INR")


            options.put("amount", "${totalPrice*100}")

            val prefill = JSONObject()

            prefill.put("email", "${firebaseAuth.currentUser!!.email}")
            prefill.put("contact", "${mobile}")

            options.put("prefill", prefill)
            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        try {
            Toast.makeText(this, "Payment Failed", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.e("failed", "Exception in onPaymentSuccess", e)
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        try {
            firebaseAuth = FirebaseAuth.getInstance()
            realtimeDatabase = FirebaseDatabase.getInstance()

            var orderRef =
                realtimeDatabase.getReference("${firebaseAuth.currentUser!!.uid}").child("orders")
                    .child("${postId}")
            val currDate = System.currentTimeMillis()

            orderRef.setValue(orders(
                    name!!,
                    locality!!,
                    city!!,
                    state!!,
                    pincode!!,
                    mobile!!,
                    currentDate,
                    products_id!!, items_cost!!, items_qty!!)
            ).addOnCompleteListener {
                Toast.makeText(this, "Payment Successful", Toast.LENGTH_LONG).show()
                finish()
            }.addOnFailureListener{
                Toast.makeText(this, "Payment Failed", Toast.LENGTH_LONG).show()
                Toast.makeText(this, "Please Try Again", Toast.LENGTH_LONG).show()
                finish()
            }
        } catch (e: Exception) {
            Log.e("success", "Exception in onPaymentSuccess", e)
        }
    }


}