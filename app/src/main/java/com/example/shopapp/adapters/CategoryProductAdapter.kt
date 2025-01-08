package com.example.shopapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopapp.activities.ProductDetailActivity
import com.example.shopapp.databinding.ItemCategoryProductLayoutBinding
import com.example.shopapp.method.functions.currencyFormat
import com.example.shopapp.model.ProductModel
import java.text.NumberFormat
import java.util.*

class CategoryProductAdapter(val context: Context, val list: ArrayList<ProductModel>)
    : RecyclerView.Adapter<CategoryProductAdapter.CategoryProductViewHolder>() {
    inner class CategoryProductViewHolder(val binding: ItemCategoryProductLayoutBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProductViewHolder {
        val binding = ItemCategoryProductLayoutBinding.inflate(LayoutInflater.from(context), parent,false)
        return CategoryProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoryProductViewHolder, position: Int) {



        val data = list[position]
        Glide.with(context).load(data.productCoverImage).into(holder.binding.imgProduct)
        holder.binding.tv1.text = data.productName

        val price = data.productSp?.toInt()?.let { currencyFormat(it) }
        holder.binding.tv2.text = price

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("id", list[position].productId)
            context.startActivity(intent)
        }


    }
}