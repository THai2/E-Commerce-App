package com.example.shopapp.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.shopapp.R
import com.example.shopapp.activities.AllProductActivity
import com.example.shopapp.activities.ProductPromotionActivity
import com.example.shopapp.adapters.CategoryAdapter
import com.example.shopapp.adapters.ProductAdapter
import com.example.shopapp.api.ApiClient
import com.example.shopapp.api.ApiService
import com.example.shopapp.databinding.FragmentHomeBinding
import com.example.shopapp.method.getAllCategory
import com.example.shopapp.method.getSlider
import com.example.shopapp.model.CategoryModel
import com.example.shopapp.model.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val reference = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)

        if (reference.getBoolean("isCart",false)){
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)
        }

        getSliderImg()

        binding.progressBar1.visibility = View.VISIBLE
        getCategory()


        binding.progressBar2.visibility = View.VISIBLE
        getAllProduct()

        binding.allProduct.setOnClickListener {
            startActivity(Intent(context,AllProductActivity::class.java))
        }



        return binding.root
    }

    private fun getAllProduct() {
        com.example.shopapp.method.getAllProduct {
            binding.productRecyclerView.adapter = ProductAdapter(requireContext(),it)
            if (it.size > 1){
                binding.progressBar1.visibility = View.GONE
            }else{
                binding.progressBar1.visibility = View.VISIBLE
            }
        }
    }


    private fun getSliderImg() {
        getSlider {
            val slideList = ArrayList<SlideModel>()
            for (data in it){
                    slideList.add(SlideModel(data.img.toString(), ScaleTypes.CENTER_INSIDE))
            }
            binding.sliderImg.setImageList(slideList)

            binding.sliderImg.setItemClickListener(object : ItemClickListener {
                override fun onItemSelected(position: Int) {
                    // You can listen here.
                    if (position == 1){
                        startActivity(Intent(context,ProductPromotionActivity::class.java))
                    }
                }
                override fun doubleClick(position: Int) {
                    // Do not use onItemSelected if you are using a double click listener at the same time.
                    // Its just added for specific cases.
                    // Listen for clicks under 250 milliseconds.
                } })

        }
//        Firebase.firestore.collection("slider").document("item")
//            .get().addOnSuccessListener {
//                //val list = it.get("img") as ArrayList<String>
//                val slideList = ArrayList<SlideModel>()
//                //for (data in list){
//                    slideList.add(SlideModel(it.get("img").toString(), ScaleTypes.CENTER_INSIDE))
//                //}
//
//                binding.sliderImg.setImageList(slideList)
//                //Glide.with(requireContext()).load(it.get("img")).into(binding.sliderImg)
//            }.addOnFailureListener {
//
//            }
    }


//    private fun getProduct() {
//
//        Firebase.firestore.collection("products").get()
//            .addOnSuccessListener {
//                list.clear()
//                for(doc in it.documents){
//                    val data = doc.toObject(ProductModel::class.java)
//                    list.add(data!!)
//                }
//                binding.productRecyclerView.adapter = ProductAdapter(requireContext(), list)
//                if (list.size > 1){
//                    binding.progressBar1.visibility = View.GONE
//                }else{
//                    binding.progressBar1.visibility = View.VISIBLE
//                }
//            }.addOnFailureListener {
//            }
//    }


    private fun getCategory() {
        getAllCategory {
            binding.categoryRecyclerView.adapter = CategoryAdapter(requireContext(), it)
            if (it.size > 1){
                    binding.progressBar2.visibility = View.GONE
                }else{
                    binding.progressBar2.visibility = View.VISIBLE
               }
        }
    }
//        val list = ArrayList<CategoryModel>()
//        Firebase.firestore.collection("categories").get()
//            .addOnSuccessListener {
//                list.clear()
//                for(doc in it.documents){
//                    val data = doc.toObject(CategoryModel::class.java)
//                    list.add(data!!)
//                }
//                binding.categoryRecyclerView.adapter = CategoryAdapter(requireContext(), list)
//                if (list.size > 1){
//                    binding.progressBar2.visibility = View.GONE
//                }else{
//                    binding.progressBar2.visibility = View.VISIBLE
//                }
//            }
//    }

}