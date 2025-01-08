package com.example.shopapp.model

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class ProductModel(
    @SerializedName("productId")
    val productId: String ?= "",
    @SerializedName("productName")
    val productName: String ?= "",
    @SerializedName("productDescription")
    val productDescription: String ?= "",
    @SerializedName("productCoverImage")
    val productCoverImage: String ?= "",
    @SerializedName("productCategory")
    val productCategory: String ?= "",
    @SerializedName("productMrp")
    val productMrp: String ?= "",
    @SerializedName("productSp")
    val productSp: String ?= "",
    @SerializedName("productImages")
    val productImages: ArrayList<String> = ArrayList()
) {
}