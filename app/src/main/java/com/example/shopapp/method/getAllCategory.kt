package com.example.shopapp.method

import android.util.Log
import com.example.shopapp.api.ApiClient
import com.example.shopapp.model.CategoryModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private val TAG:String ?= "CHECK_RESPONSE"
fun getAllCategory(callback: (MutableList<CategoryModel>) -> Unit) {
 val list = mutableListOf<CategoryModel>()
 ApiClient.getRetrofitClient().getAllCategory().enqueue(object : Callback<List<CategoryModel>> {
  override fun onResponse(
   call: Call<List<CategoryModel>>,
   response: Response<List<CategoryModel>>
  ) {
   if (response.isSuccessful) {
    // lấy body của response gán vào list
    for (category in response.body()!!){
     Log.i(TAG, "onRespone : ${category.cat}")
     list.add(category)
    }
    // trả list nhận được ra ngoài
    callback.invoke(list)
   }

  }

  override fun onFailure(call: Call<List<CategoryModel>>, t: Throwable) {
   Log.i(TAG, "onRespone : ${t.message}")
  }

 })
}