package com.project.farmingapp.model.data

data class APMCCustomRecords(val state:String,val district:String,val market:String,val commodity:MutableList<String>,val min_price:MutableList<String>,val max_price:MutableList<String>)