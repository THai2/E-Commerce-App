package com.example.shopapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.shopapp.MainActivity
import com.example.shopapp.R
import com.example.shopapp.adapters.CartAdapter
import com.example.shopapp.adapters.FavoriteAdapter
import com.example.shopapp.databinding.ActivityFavoriteBinding
import com.example.shopapp.roomdb.AppDatabase

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val dao = AppDatabase.getInstance(this).favoriteProductDao()
        dao.getAllProduct().observe(this){
            binding.favoriteProductRecyclerView.adapter = FavoriteAdapter(this, it)

        }

        binding.imageView4.setOnClickListener {
            super.onBackPressed()
            finish()

        }



    }
}