package com.example.shopapp.method

import android.util.Log
import com.example.shopapp.api.ApiClient
import com.example.shopapp.model.OrderModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val TAG:String ?= "CHECK_RESPONSE"
fun getMyAllOrders(userPhoneNumber: String ,callback: (ArrayList<OrderModel>) -> Unit) {
    val list = ArrayList<OrderModel>()
    ApiClient.getRetrofitClient().getMyAllOrders(userPhoneNumber).enqueue(object : Callback<List<OrderModel>> {
        override fun onResponse(
            call: Call<List<OrderModel>>,
            response: Response<List<OrderModel>>
        ) {
            if (response.isSuccessful) {
                // lấy body của response gán vào list
                for (myAllOrders in response.body()!!){
                    Log.i(TAG, "onRespone : ${myAllOrders.orderId}")
                    list.add(myAllOrders)
                }
                // trả list nhận được ra ngoài
                callback.invoke(list)
            }
        }

        override fun onFailure(call: Call<List<OrderModel>>, t: Throwable) {
            Log.i(TAG, "onRespone : ${t.message}")
        }

    })
}