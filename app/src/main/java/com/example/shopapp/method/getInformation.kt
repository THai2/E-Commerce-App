package com.example.shopapp.method

import android.util.Log
import com.example.shopapp.api.ApiClient
import com.example.shopapp.model.ProductModel
import com.example.shopapp.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val TAG:String ?= "CHECK_RESPONSE"

fun getInformation(userPhoneNumber: String ,callback: (UserModel) -> Unit) {

    ApiClient.getRetrofitClient().getInformation(userPhoneNumber).enqueue(object : Callback<UserModel> {
        override fun onResponse(
            call: Call<UserModel>,
            response: Response<UserModel>
        ) {
            if (response.isSuccessful) {
                // lấy body của response gán vào list
                val r = response.body()
                val user: UserModel
                if (r != null){
                    user = r
                    callback.invoke(user)
                    Log.i(TAG, "onRespone : ${r.userPhoneNumber}")
                }
            }
        }

        override fun onFailure(call: Call<UserModel>, t: Throwable) {
            Log.i(TAG, "onRespone : ${t.message}")
        }

    })
}