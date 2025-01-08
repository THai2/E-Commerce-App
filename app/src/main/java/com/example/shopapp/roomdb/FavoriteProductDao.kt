package com.example.shopapp.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteProductDao {
    @Insert
    suspend fun insertProduct(favoriteProductModel: FavoriteProductModel)

    @Delete
    suspend fun deleteProduct(favoriteProductModel: FavoriteProductModel)

    @Query("SELECT * FROM favoriteProducts")
    fun getAllProduct() : LiveData<List<FavoriteProductModel>>

    @Query("SELECT * FROM favoriteProducts WHERE productId = :id")
    fun isExist(id : String) : FavoriteProductModel
}