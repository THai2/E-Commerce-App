package com.example.shopapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.R
import com.example.shopapp.adapters.CategoryProductAdapter
import com.example.shopapp.adapters.ProductAdapter
import com.example.shopapp.method.getProductByCategory
import com.example.shopapp.model.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        getProducts(intent.getStringExtra("cat"))




    }

    private fun getProducts(category: String?) {
        val list = ArrayList<ProductModel>()
        getProductByCategory(category!!){
            list.clear()
                for(data in it){
                    list.add(data)
                }
                val recyclerView = findViewById<RecyclerView>(R.id.categoryProductRecyclerView)
                recyclerView.adapter = CategoryProductAdapter(this, list)
        }
//        val list = ArrayList<ProductModel>()
//        Firebase.firestore.collection("products").whereEqualTo("productCategory", category)
//            .get()
//            .addOnSuccessListener {
//                list.clear()
//                for(doc in it.documents){
//                    val data = doc.toObject(ProductModel::class.java)
//                    list.add(data!!)
//                }
//                val recyclerView = findViewById<RecyclerView>(R.id.categoryProductRecyclerView)
//                recyclerView.adapter = CategoryProductAdapter(this, list)
//            }.addOnFailureListener {
//            }
    }
}