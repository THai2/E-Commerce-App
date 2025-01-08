package com.example.shopapp.method

import android.util.Log
import com.example.shopapp.api.ApiClient
import com.example.shopapp.model.ProductModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val TAG:String ?= "CHECK_RESPONSE"

fun getProductById(productId: String ,callback: (ProductModel) -> Unit) {

    ApiClient.getRetrofitClient().getProductById(productId).enqueue(object : Callback<ProductModel> {
        override fun onResponse(
            call: Call<ProductModel>,
            response: Response<ProductModel>
        ) {
            if (response.isSuccessful) {
                // lấy body của response gán vào list
                val r = response.body()
                val product: ProductModel
                if (r != null){
                    product = r
                    callback.invoke(product)
                    Log.i(TAG, "onRespone : ${r.productName}")
                }
            }
        }

        override fun onFailure(call: Call<ProductModel>, t: Throwable) {
            Log.i(TAG, "onRespone : ${t.message}")
        }

    })
}