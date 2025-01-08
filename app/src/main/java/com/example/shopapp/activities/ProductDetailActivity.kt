package com.example.shopapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.shopapp.MainActivity
import com.example.shopapp.databinding.ActivityProductDetailBinding
import com.example.shopapp.method.functions.currencyFormat
import com.example.shopapp.method.getProductById
import com.example.shopapp.roomdb.AppDatabase
import com.example.shopapp.roomdb.ProductDao
import com.example.shopapp.roomdb.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)

        getProductDetail(intent.getStringExtra("id"))


        setContentView(binding.root)

        binding.imageView4.setOnClickListener {
            super.onBackPressed()
            finish()
        }

    }

    private fun getProductDetail(productId: String?) {
        getProductById(productId!!){
            val list = it.productImages
                val productName = it.productName
                val productSp = it.productSp
                val productDescription = it.productDescription
                binding.tvName.text = productName
                val price = it.productSp?.toInt()?.let { currencyFormat(it) }
                binding.tvPrice.text = price
                binding.tvDes.text = productDescription
            val slideList = ArrayList<SlideModel>()
                for (data in list){
                    slideList.add(SlideModel(it.productCoverImage, ScaleTypes.CENTER_INSIDE))
                    slideList.add(SlideModel(data, ScaleTypes.CENTER_INSIDE))
                }

                cartAction(productId, productName, it.productCoverImage, productSp )

                binding.imageSlider.setImageList(slideList)
        }
//        Firebase.firestore.collection("products")
//            .document(productId!!).get().addOnSuccessListener {
//                val list = it.get("productImages") as ArrayList<String>
//                val productName = it.getString("productName")
//                val productSp = it.getString("productSp")
//                val productDescription = it.getString("productDescription")
//                binding.tvName.text = productName
//                binding.tvPrice.text = productSp
//                binding.tvDes.text = productDescription
//
//                val slideList = ArrayList<SlideModel>()
//                for (data in list){
//                    slideList.add(SlideModel(it.getString("productCoverImage"), ScaleTypes.CENTER_INSIDE))
//                    slideList.add(SlideModel(data, ScaleTypes.CENTER_INSIDE))
//                }
//
//                cartAction(productId, productName, it.getString("productCoverImage"), productSp )
//
//                binding.imageSlider.setImageList(slideList)
//
//            }.addOnFailureListener {
//                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
//            }
    }

    private fun cartAction(productId: String, productName: String?, productCoverImg: String?, productSp: String?) {
        val productDao = AppDatabase.getInstance(this).productDao()
        if (productDao.isExist(productId)!=null){
            binding.tvAddToCart.text = "Go to cart"
        }else{
            binding.tvAddToCart.text = "Add to cart"
        }

        binding.tvAddToCart.setOnClickListener {
            if (productDao.isExist(productId)!=null){
                openCart()
            }else{
                addToCart(productDao,productId,productName, productCoverImg,productSp)
            }
        }

    }

    private fun addToCart(
        productDao: ProductDao,
        productId: String,
        productName: String?,
        productCoverImg: String?,
        productSp: String?)
    {
        val data = ProductModel(productId,productName,productCoverImg,productSp )
        lifecycleScope.launch(Dispatchers.IO){
            productDao.insertProduct(data)
            binding.tvAddToCart.text = "Go to cart"
        }
    }

    private fun openCart() {
        val reference = this.getSharedPreferences("info", MODE_PRIVATE )
        val editor = reference.edit()
        editor.putBoolean("isCart",true)
        editor.apply()

        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

}