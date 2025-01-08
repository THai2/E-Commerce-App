package com.example.shopapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopapp.activities.ProductDetailActivity
import com.example.shopapp.databinding.ItemFavoriteLayoutBinding
import com.example.shopapp.method.functions.currencyFormat
import com.example.shopapp.roomdb.AppDatabase
import com.example.shopapp.roomdb.FavoriteProductDao
import com.example.shopapp.roomdb.FavoriteProductModel
import com.example.shopapp.roomdb.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteAdapter(val context: Context, val list: List<FavoriteProductModel>)
    : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    inner class FavoriteViewHolder(val binding: ItemFavoriteLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val data = list[position]

        Glide.with(context).load(data.productImage).into(holder.binding.imageView)
        holder.binding.textView.text = data.productName
        val price = data.productSp?.toInt()?.let { currencyFormat(it) }
        holder.binding.textView2.text = price

        val favoriteProductDao: FavoriteProductDao = AppDatabase.getInstance(context).favoriteProductDao()
        holder.binding.imageView2.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){
                favoriteProductDao.deleteProduct(
                    FavoriteProductModel(
                    data.productId,
                    data.productName,
                    data.productImage,
                    data.productCategory,
                    data.productSp)
                )
            }
            Toast.makeText(context, "Unfavorite successfully", Toast.LENGTH_SHORT).show()
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("id", list[position].productId)
            context.startActivity(intent)
        }

    }
}