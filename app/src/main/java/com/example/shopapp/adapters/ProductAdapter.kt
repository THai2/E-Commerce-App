package com.example.shopapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopapp.activities.FavoriteActivity
import com.example.shopapp.activities.ProductDetailActivity
import com.example.shopapp.databinding.ItemProductLayoutBinding
import com.example.shopapp.method.functions.currencyFormat
import com.example.shopapp.model.ProductModel
import com.example.shopapp.roomdb.AppDatabase
import com.example.shopapp.roomdb.FavoriteProductDao
import com.example.shopapp.roomdb.FavoriteProductModel
import com.example.shopapp.roomdb.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class ProductAdapter(val context: Context, val list: MutableList<ProductModel>)
    : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>()
{

    inner class ProductViewHolder(val binding: ItemProductLayoutBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductLayoutBinding.inflate(LayoutInflater.from(context), parent,false)
        return ProductViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val data = list[position]

        Glide.with(context).load(data.productCoverImage).into(holder.binding.imgProductCover)
        holder.binding.tvProductName.text = data.productName
        holder.binding.tvProductCategory.text = data.productCategory
        holder.binding.tvProductPrice.text = data.productMrp

        val price = data.productSp?.toInt()?.let { currencyFormat(it) }

        holder.binding.button2.text = price


        holder.binding.button2.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("id", list[position].productId)
            context.startActivity(intent)
        }

        holder.binding.button.setOnClickListener {
            val data = FavoriteProductModel(
                data.productId!!,
                data.productName,
                data.productCoverImage,
                data.productCategory,
                data.productSp
            )
            val favoriteProductDao: FavoriteProductDao = AppDatabase.getInstance(context).favoriteProductDao()
            GlobalScope.launch(){
                favoriteProductDao.insertProduct(data)
            }
            Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show()
        }



        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("id", list[position].productId)
            context.startActivity(intent)
        }


    }



}