package com.example.shopapp.method

import android.util.Log
import com.example.shopapp.api.ApiClient
import com.example.shopapp.model.CategoryModel
import com.example.shopapp.model.ProductModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val TAG:String ?= "CHECK_RESPONSE"
fun getProductByPromotion(callback: (ArrayList<ProductModel>) -> Unit) {

    val list = ArrayList<ProductModel>()
    ApiClient.getRetrofitClient().getProductByPromotion().enqueue(object : Callback<List<ProductModel>> {
        override fun onResponse(
            call: Call<List<ProductModel>>,
            response: Response<List<ProductModel>>
        ) {
            if (response.isSuccessful) {
                // lấy body của response gán vào list
                for (product in response.body()!!){
                    Log.i(TAG, "onRespone : ${product.productName}")
                    list.add(product)
                }
                // trả list nhận được ra ngoài
                callback.invoke(list)
            }
        }

        override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
            Log.i(TAG, "onRespone : ${t.message}")
        }

    })
}