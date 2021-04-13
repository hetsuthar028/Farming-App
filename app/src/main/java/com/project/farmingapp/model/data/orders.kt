package com.project.farmingapp.model.data

import kotlinx.android.synthetic.main.activity_razor_pay.*
import java.sql.Timestamp

data class orders( var name:String,
                   var locality:String,
                   var city:String,
                   var state:String,
                   var pincode:String,
                   var mobile:String,
                   var time:String,
                   var products_id:List<String>,
                   var items_cost:List<Int>,
                   var items_qty:List<Int>

                   )