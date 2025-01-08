package com.example.shopapp.method

import android.util.Log
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.shopapp.api.ApiClient
import com.example.shopapp.model.DataSliderModel
import com.example.shopapp.model.ProductModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val TAG:String ?= "CHECK_RESPONSE"
fun getSlider(callback: (ArrayList<DataSliderModel>) -> Unit) {
    var list = ArrayList<DataSliderModel>()
    ApiClient.getRetrofitClient().getSlider().enqueue(object : Callback<ArrayList<DataSliderModel>> {
        override fun onResponse(
            call: Call<ArrayList<DataSliderModel>>,
            response: Response<ArrayList<DataSliderModel>>
        ) {
            val responseBody = response.body()!!
            if (response.isSuccessful){
                for (doc in responseBody){
                    Log.i(TAG, "onRespone : ${doc}")
                    list.add(doc)

                }
                callback.invoke(list)
            }
        }

        override fun onFailure(call: Call<ArrayList<DataSliderModel>>, t: Throwable) {
            Log.i(TAG, "onRespone : ${t.message}")
        }

    })
}