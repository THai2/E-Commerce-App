package com.example.shopapp.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "favoriteProducts")
data class FavoriteProductModel(
    @PrimaryKey
    @Nonnull
    val productId: String,
    @ColumnInfo(name = "productName") val productName: String?= "",
    @ColumnInfo(name = "productImage") val productImage: String?= "",
    @ColumnInfo(name = "productCategory") val productCategory: String?= "",
    @ColumnInfo(name = "productSp") val productSp: String?= ""

)
