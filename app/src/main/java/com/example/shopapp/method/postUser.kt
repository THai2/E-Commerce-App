package com.example.shopapp.method

import android.util.Log
import com.example.shopapp.api.ApiClient
import com.example.shopapp.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
private val TAG:String ?= "CHECK_RESPONSE"
fun addUser(user: UserModel, callback: (Boolean) -> Unit) {
    var rs : Boolean
    val call = ApiClient.getRetrofitClient().addUser(user)

    call.enqueue(object: Callback<UserModel> {
        override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
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

        override fun onFailure(call: Call<UserModel>, t: Throwable) {
            callback.invoke(false)
            Log.d(TAG,"onRespone : ${t.message}")
        }

    })
}