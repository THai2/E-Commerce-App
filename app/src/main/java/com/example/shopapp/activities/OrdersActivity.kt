package com.example.shopapp.activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shopapp.R
import com.example.shopapp.adapters.OrderAdapter
import com.example.shopapp.databinding.ActivityOrdersBinding
import com.example.shopapp.method.getMyAllOrders
import com.example.shopapp.model.OrderModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OrdersActivity : AppCompatActivity() {
    private lateinit var preferences: SharedPreferences
    private lateinit var binding: ActivityOrdersBinding

    private lateinit var list : ArrayList<OrderModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = ArrayList()
        preferences = this.getSharedPreferences("user", MODE_PRIVATE)


        getMyAllOrders(preferences.getString("userPhoneNumber","")!!){
            list.clear()
               for (data in it){
                    list.add(data)
                }
                binding.recyclerView.adapter = OrderAdapter(this,list)
        }

//        Firebase.firestore.collection("allOrders")
//            .get().addOnSuccessListener {
//                list.clear()
//                for (doc in it){
//                    val data = doc.toObject(OrderModel::class.java)
//                    list.add(data!!)
//                }
//                binding.recyclerView.adapter = OrderAdapter(this,list)
//            }

        binding.imageView4.setOnClickListener {
            super.onBackPressed()
            finish()
        }

    }
}