package com.example.shopapp.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.shopapp.MainActivity
import com.example.shopapp.activities.FavoriteActivity
import com.example.shopapp.activities.InformationActivity
import com.example.shopapp.activities.OrdersActivity
import com.example.shopapp.databinding.FragmentMoreBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MoreFragment : Fragment() {

    private lateinit var binding: FragmentMoreBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(layoutInflater)

        getInformation()

        binding.cvInformation.setOnClickListener {
            val intent = Intent(context, InformationActivity::class.java )
            startActivity(intent)
        }


        binding.cardView8.setOnClickListener {
            val intent = Intent(context, FavoriteActivity::class.java )
            startActivity(intent)
        }
        binding.cvLogout.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            auth.signOut()
            val intent = Intent(context, MainActivity::class.java )
            startActivity(intent)
        }

        binding.cardView7.setOnClickListener {
            val intent = Intent(context, OrdersActivity::class.java )
            startActivity(intent)
        }

        return binding.root
    }

    private fun getInformation() {
        val preferences = context?.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)


        com.example.shopapp.method.getInformation(
            preferences!!.getString(
                "userPhoneNumber",
                ""
            )!!
        ) {
            binding.textView4.setText(it.userPhoneNumber)
            binding.textView3.setText(it.userName)
        }

//        Firebase.firestore.collection("users")
//            .document(preferences!!.getString("userPhoneNumber","")!!)
//            .get().addOnSuccessListener {
//                binding.textView4.setText(it.getString("userPhoneNumber"))
//                binding.textView3.setText(it.getString("userName"))
//            }
//    }
    }

}