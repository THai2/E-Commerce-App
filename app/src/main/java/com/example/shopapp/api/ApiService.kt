package com.example.shopapp.api

import com.example.shopapp.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Accept:application/json")
    @GET("get-all-products")
    fun getAllProducts(): Call<List<ProductModel>>

    @GET("get-slider")
    fun getSlider(): Call<ArrayList<DataSliderModel>>

    @GET("get-all-category")
    fun getAllCategory(): Call<List<CategoryModel>>

    @GET("get-product-by-price-0-999k")
    fun getBy0To999(): Call<List<ProductModel>>

    @GET("get-product-by-price-1tr-2tr")
    fun getBy999To1999(): Call<List<ProductModel>>

    @GET("get-after-2tr")
    fun getOver2(): Call<List<ProductModel>>

    @GET("get-product/{productId}")
    fun getProductById(@Path("productId") productId: String): Call<ProductModel>

    @GET("get-information/{userPhoneNumber}")
    fun getInformation(@Path("userPhoneNumber") userPhoneNumber: String): Call<UserModel>

    @GET("get-product-category/{productCategory}")
    fun getProductByCategory(@Path("productCategory") productCategory: String): Call<List<ProductModel>>

    @GET("get-product-promotion")
    fun getProductByPromotion(): Call<List<ProductModel>>

    @GET("get-my-all-orders/{userPhoneNumber}")
    fun getMyAllOrders(@Path("userPhoneNumber") userPhoneNumber: String): Call<List<OrderModel>>

    @GET("get-order/{orderId}")
    fun getOrderById(@Path("orderId") orderId: String): Call<OrderModel>

    @POST("add-users")
    fun addUser(@Body user: UserModel): Call<UserModel>

    @POST("add-order")
    fun addOrder(@Body orderModel: OrderModel): Call<OrderModel>

    @FormUrlEncoded
    @PATCH("update-information/{userPhoneNumber}")
    fun updateInformation(@Path("userPhoneNumber") userPhoneNumber: String,
                          @FieldMap map: HashMap< String,@JvmSuppressWildcards Any>): Call<UserModel>

    @FormUrlEncoded
    @PATCH("update-status/{orderId}")
    fun updateStatusOrder(@Path("orderId") orderId: String, @Field("status") status:String): Call<OrderModel>

//    @FormUrlEncoded
//    @PATCH("update-information/{userPhoneNumber}")
//    fun updateAddress(@Path("userPhoneNumber") userPhoneNumber: String,
//                      @Field("duong") duong: String,
//                      @Field("c1") c1: String,
//                      @Field("c2") c2: String,
//                      @Field("c3") c3: String,
//                      @Field("option") option: String, ): Call<UserModel>

}