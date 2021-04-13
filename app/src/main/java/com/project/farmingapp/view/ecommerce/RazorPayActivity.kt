package com.project.farmingapp.view.ecommerce

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

class RazorPayActivity : AppCompatActivity(),PaymentResultListener{
    lateinit var firebaseAuth: FirebaseAuth
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

    var postId:UUID?=null

    val currentDate = sdf.format(Date())
    lateinit var realtimeDatabase: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_razor_pay)
        postId=UUID.randomUUID()

        //val totalCost=intent.getStringExtra("tp")
       var products_id=intent.getStringArrayListExtra("products_id")
        var items_cost=intent.getIntegerArrayListExtra("items_cost")
        var items_qty=intent.getIntegerArrayListExtra("items_qty")
        Log.d("tp",products_id.toString())
        Log.d("tp",items_cost.toString())
        Log.d("tp",items_qty.toString())
       // Toast.makeText(this,abc,Toast.LENGTH_LONG).show()
//Log.d("tp",abc.toString())
    orderNowBtn.setOnClickListener {
        val name= fullNamePrePay.text.toString()
        val locality= localityPrePay.text.toString()
        val city=cityPrePay.text.toString()
        val state=statePrePay.text.toString()
        val pincode=pincodePrePay.text.toString()
        val mobile =mobileNumberPrePay.text.toString()
        if(name.isNullOrEmpty() ||
            locality.isNullOrEmpty() ||
            city.isNullOrEmpty() ||
            state.isNullOrEmpty() ||
            pincode.isNullOrEmpty() ||
            mobile.isNullOrEmpty()
        )
        {

            Toast.makeText(this,"edjedj",Toast.LENGTH_LONG).show()
        }

        //add inputfield validation

        else
        {

                firebaseAuth = FirebaseAuth.getInstance()
                realtimeDatabase = FirebaseDatabase.getInstance()

                    val orderRef = realtimeDatabase.getReference("${firebaseAuth.currentUser!!.uid}").child("orders").child("${postId}")
    val currDate = System.currentTimeMillis()
                orderRef.setValue(orders(name, locality, city, state, pincode, mobile,currentDate,products_id,items_cost,items_qty))


                //add inputfield validation
                Toast.makeText(this,"Done",Toast.LENGTH_LONG).show()
                startPayment()



        }
    }





    }
    private fun startPayment() {
/*
* You need to pass current activity in order to let Razorpay create CheckoutActivity
* */
        val activity: Activity =this
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name","FarmingApp")
            options.put("description","Demoing Charges")
//You can omit the image option to fetch the image from dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("currency","INR")
            options.put("amount","120")

            val prefill = JSONObject()

            prefill.put("email","Enter Your Email")
            prefill.put("contact","Enter Your Mobile No")

            options.put("prefill",prefill)
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
    override fun onPaymentError(p0: Int, p1: String?) {
        try{

            Toast.makeText(this,"Payment failed ", Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            Log.e("failed","Exception in onPaymentSuccess", e)
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        try{


            Toast.makeText(this,"Payment Successful", Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            Log.e("success","Exception in onPaymentSuccess", e)
        }
    }


}