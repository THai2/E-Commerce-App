package com.example.shopapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopapp.activities.ProductDetailActivity
import com.example.shopapp.databinding.ItemCartLayoutBinding
import com.example.shopapp.method.functions.currencyFormat
import com.example.shopapp.roomdb.AppDatabase
import com.example.shopapp.roomdb.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartAdapter(val context: Context,val list: List<ProductModel>)
    :RecyclerView.Adapter<CartAdapter.CartViewHolder>()
{
    inner class CartViewHolder(val binding :ItemCartLayoutBinding):
    RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        Glide.with(context).load(list[position].productImage).into(holder.binding.imageView)
        holder.binding.textView.text = list[position].productName
        val price = list[position].productSp?.toInt()?.let { currencyFormat(it) }
        holder.binding.textView2.text = price
        holder.binding.textView9.text = list[position].productCount.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ProductDetailActivity::class.java )
            intent.putExtra("id", list[position].productId)
            context.startActivity(intent)
        }



        val dao = AppDatabase.getInstance(context).productDao()
        holder.binding.imageView2.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO){
                dao.deleteProduct(ProductModel(list[position].productId,
                    list[position].productName,list[position].productImage, list[position].productSp))

            }
        }

        holder.binding.imageView5.setOnClickListener {
            if(list[position].productCount!! > 1){
                list[position].productCount = list[position].productCount?.plus(1)
                holder.binding.textView9.text = list[position].productCount.toString()
                GlobalScope.launch(Dispatchers.IO){
                    dao.updateProduct(list[position])
                }
            }else if (list[position].productCount!! == 1){
                list[position].productCount = list[position].productCount?.plus(1)
                holder.binding.textView9.text = list[position].productCount.toString()
                holder.binding.imageView6.visibility = View.GONE
                GlobalScope.launch(Dispatchers.IO){
                    dao.updateProduct(list[position])

                }
            }
        }

        holder.binding.imageView6.setOnClickListener {
            if(list[position].productCount!! > 1){
                list[position].productCount = list[position].productCount?.minus(1)
                holder.binding.textView9.text = list[position].productCount.toString()
                GlobalScope.launch(Dispatchers.IO){
                    dao.updateProduct(list[position])
                }
            }else if(list[position].productCount!! == 1){
                GlobalScope.launch(Dispatchers.IO){
                    dao.deleteProduct(ProductModel(list[position].productId,
                        list[position].productName,list[position].productImage, list[position].productSp))

                }
            }



        }
    }
    }