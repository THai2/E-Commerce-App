package com.example.shopapp.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.shopapp.activities.AddressActivity
import com.example.shopapp.adapters.CartAdapter
import com.example.shopapp.databinding.FragmentCartBinding
import com.example.shopapp.method.functions.currencyFormat
import com.example.shopapp.roomdb.AppDatabase
import com.example.shopapp.roomdb.ProductModel

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)

        val reference = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor = reference.edit()
        editor.putBoolean("isCart",false)
        editor.apply()

        val dao = AppDatabase.getInstance(requireContext()).productDao()
        dao.getAllProduct().observe(requireActivity()){
            binding.cartRecyclerView.adapter = CartAdapter(requireContext(), it)

            getTotalCost(it)

        }




        return binding.root
    }

    private fun getTotalCost(data: List<ProductModel>?) {
        var total = 0
        var totalItem = 0
        var totalCurrent = 0
        for (item in data!!){
            totalItem+= item.productCount!!.toInt()
            totalCurrent = (item.productSp!!.toInt())*item.productCount!!
            total += totalCurrent
        }
//        binding.textView1.text = "Total item in cart is: ${data.size}"
        binding.textView1.text = "Total item in cart is: ${totalItem}"
        val price = total?.toInt()?.let { currencyFormat(it) }
        binding.textView2.text = "Total cost: $price"

        binding.btnCheckout.setOnClickListener {
            val intent = Intent(context, AddressActivity::class.java )
            intent.putExtra("totalCost", total)
            startActivity(intent)
        }

    }

}