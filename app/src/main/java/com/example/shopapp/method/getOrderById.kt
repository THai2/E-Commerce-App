package com.example.shopapp.method

import android.util.Log
import com.example.shopapp.api.ApiClient
import com.example.shopapp.model.OrderModel
import com.example.shopapp.model.ProductModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val TAG:String ?= "CHECK_RESPONSE"

fun getOrderById(orderId: String ,callback: (OrderModel) -> Unit) {

    ApiClient.getRetrofitClient().getOrderById(orderId).enqueue(object : Callback<OrderModel> {
        override fun onResponse(
            call: Call<OrderModel>,
            response: Response<OrderModel>
        ) {
            if (response.isSuccessful) {
                // lấy body của response gán vào list
                val r = response.body()
                val order: OrderModel
                if (r != null){
                    order = r
                    callback.invoke(order)
                    Log.i(TAG, "onRespone : ${r.orderId}")
                }
            }
        }

        override fun onFailure(call: Call<OrderModel>, t: Throwable) {
            Log.i(TAG, "onRespone : ${t.message}")
        }

    })
}