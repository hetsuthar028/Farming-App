package com.project.farmingapp.model

import com.project.farmingapp.model.data.APMCMain
import com.project.farmingapp.model.data.WeatherRootList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.data.gov.in/resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd000001987c65666f9c49656f0f9ef4fa3650e7&format=json&offset=0&limit=400
const val BASE_URL2 ="https://api.data.gov.in/"
const val API_KEY2 ="579b464db66ec23bdd000001987c65666f9c49656f0f9ef4fa3650e7"
interface apmcInterface {
    @GET("resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=$API_KEY2&format=json&offset=0&limit=7000")
    fun getapmc(@Query("limit")limit:Int): Call<APMCMain>
    @GET("resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=$API_KEY2&format=json&offset=0&limit=7000")
    fun getSomeData(@Query("filters[district]")filter:String):Call<APMCMain>

}
object APMCApi {
    val apmcInstances:apmcInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apmcInstances =retrofit.create(apmcInterface::class.java)
    }
}