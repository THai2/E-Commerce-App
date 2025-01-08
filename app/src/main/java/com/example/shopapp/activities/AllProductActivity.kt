package com.example.shopapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.shopapp.R
import com.example.shopapp.adapters.ProductAdapter
import com.example.shopapp.databinding.ActivityAllProductBinding
import com.example.shopapp.method.getAllProduct
import com.example.shopapp.method.getBy0To999
import com.example.shopapp.method.getBy999To1999
import com.example.shopapp.method.getOver2
import com.example.shopapp.model.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class AllProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView4.setOnClickListener {
            super.onBackPressed()
            finish()
        }


        getAllProduct {
            binding.productRecyclerView.adapter = ProductAdapter(this, it)
            if (it.size > 1){
                binding.progressBar.visibility = View.GONE
            }else{
                binding.progressBar.visibility = View.VISIBLE
            }
        }

        var productFilter: MutableList<String> = mutableListOf("0-999k","999k - 1tr999", ">= 2tr")
        val arrayNameAdapter = ArrayAdapter(this, R.layout.item_view_layout, productFilter)

        binding.spinner.adapter = arrayNameAdapter

        if (binding.spinner.selectedItemPosition == 0){
            getBy0To999 {
                binding.productRecyclerView.adapter = ProductAdapter(this, it)
                if (it.size > 1){
                binding.progressBar.visibility = View.GONE
            }else{
                binding.progressBar.visibility = View.VISIBLE
            }
                binding.productRecyclerView.adapter?.notifyDataSetChanged()

            }
        } else if(binding.spinner.selectedItemPosition == 1){
        if(binding.spinner.selectedItemPosition == 1){
            getBy999To1999 {
                binding.productRecyclerView.adapter = ProductAdapter(this, it)
                if (it.size > 1){
                    binding.progressBar.visibility = View.GONE
                }else{
                    binding.progressBar.visibility = View.VISIBLE
                }
                binding.productRecyclerView.adapter?.notifyDataSetChanged()

            }
        }else if(binding.spinner.selectedItemPosition == 2){
            getOver2 {
                binding.productRecyclerView.adapter = ProductAdapter(this, it)
                if (it.size > 1){
                    binding.progressBar.visibility = View.GONE
                }else{
                    binding.progressBar.visibility = View.VISIBLE
                }
                binding.productRecyclerView.adapter?.notifyDataSetChanged()

            }
        }

        binding.productRecyclerView.adapter?.notifyDataSetChanged()


    }

//    private fun getDropdown() {
//        var productFilter: MutableList<String> = mutableListOf("0-999k","999k - 1tr999", ">= 2tr")
//        val arrayNameAdapter = ArrayAdapter(this, R.layout.item_view_layout, productFilter)
//
//        binding.spinner.adapter = arrayNameAdapter
//
//        if (binding.spinner.selectedItemPosition == 0){
//            getBy0To999 {
//                binding.productRecyclerView.adapter = ProductAdapter(this, it)
//            }
//        }
//    }


}}

