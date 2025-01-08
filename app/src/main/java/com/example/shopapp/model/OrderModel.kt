package com.example.shopapp.model

data class OrderModel(
    val name :ArrayList<String> ?=ArrayList(),
    val orderId :String ?= "",
    val userId :String ?= "",
    val status :String ?= "",
    val totalCost :String ?= "",
    val productId :ArrayList<String> ?=ArrayList(),
    val price :ArrayList<String> ?=ArrayList(),
    val paymentMethod :String ?= "",
    val idZaloPayMethod :String ?= "",
    val listItemTotal :ArrayList<String> ?= ArrayList()
){

}
