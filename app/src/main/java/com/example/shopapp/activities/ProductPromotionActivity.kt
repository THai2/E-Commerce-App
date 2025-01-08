package com.example.shopapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.R
import com.example.shopapp.adapters.CategoryProductAdapter
import com.example.shopapp.databinding.ActivityProductPromotionBinding
import com.example.shopapp.method.getProductByCategory
import com.example.shopapp.method.getProductByPromotion
import com.example.shopapp.model.ProductModel
import java.util.ArrayList

class ProductPromotionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductPromotionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductPromotionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getProduct()

        binding.imageView4.setOnClickListener {
            super.onBackPressed()
            finish()
        }
    }

    private fun getProduct() {
        val list = ArrayList<ProductModel>()
        getProductByPromotion {
            list.clear()
            for(data in it){
                list.add(data)
            }
            binding.productRecyclerView.adapter = CategoryProductAdapter(this, list)
        }
    }
}