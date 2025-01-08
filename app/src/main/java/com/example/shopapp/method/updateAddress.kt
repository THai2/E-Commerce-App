package com.example.shopapp.method

import android.util.Log
import com.example.shopapp.api.ApiClient
import com.example.shopapp.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val TAG:String ?= "CHECK_RESPONSE"
//fun updateAddress(userPhoneNumber: String, duong : String,c1 : String, c2 : String, c3 : String,option : String, callback: (Boolean) -> Unit) {
//    var rs : Boolean
//    val call = ApiClient.getRetrofitClient().updateAddress(userPhoneNumber,duong, c1, c2, c3, option)
//
//    call.enqueue(object: Callback<UserModel> {
//        override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
//            if (response.isSuccessful) {
//                rs = true
//                Log.d(TAG,"Success")
//            }
//            else {
//                rs = false
//                Log.d(TAG,"Failed")
//            }
//            callback.invoke(rs)
//        }
//
//        override fun onFailure(call: Call<UserModel>, t: Throwable) {
//            Log.d(TAG,"onRespone : ${t.message}")
//        }
//
//    })
//}