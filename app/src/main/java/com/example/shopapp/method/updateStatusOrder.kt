package com.example.shopapp.method

import android.util.Log
import com.example.shopapp.api.ApiClient
import com.example.shopapp.model.OrderModel
import com.example.shopapp.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val TAG:String ?= "CHECK_RESPONSE"
fun updateStatusOrder(orderId: String,status: String, callback: (Boolean) -> Unit) {
    var rs : Boolean
    val call = ApiClient.getRetrofitClient().updateStatusOrder(orderId, status)

    call.enqueue(object: Callback<OrderModel> {
        override fun onResponse(call: Call<OrderModel>, response: Response<OrderModel>) {
            if (response.isSuccessful) {
                rs = true
                Log.d(TAG,"Success")
            }
            else {
                rs = false
                Log.d(TAG,"Failed")
            }
            callback.invoke(rs)
        }

        override fun onFailure(call: Call<OrderModel>, t: Throwable) {
            Log.d(TAG,"onRespone : ${t.message}")
        }

    })
}