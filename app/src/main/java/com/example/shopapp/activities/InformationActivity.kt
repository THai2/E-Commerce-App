package com.example.shopapp.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.shopapp.MainActivity
import com.example.shopapp.R
import com.example.shopapp.databinding.ActivityInformationBinding
import com.example.shopapp.method.getInformation
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class InformationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityInformationBinding

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = this.getSharedPreferences("user", MODE_PRIVATE)

        loadUserInformation()

        binding.btnSave.setOnClickListener{
            validateData(
                binding.edPhoneNumber.text.toString(),
                binding.edUsername.text.toString(),
                binding.edInf0.text.toString(),
                binding.edInf1.text.toString(),
                binding.edInf2.text.toString(),
                binding.edInf3.text.toString(),
                binding.edInf.text.toString(),
            )

        }
            //startActivity(intent)
        binding.imageView4.setOnClickListener {
            super.onBackPressed()
            finish()

        }


    }

    private fun validateData(phoneNumber: String, userName: String, edInf0: String, edInf1: String,
                             edInf2: String, edInf3: String, edInf: String) {
        if(phoneNumber.isEmpty() || userName.isEmpty()){
            Toast.makeText(this, "Fill all text", Toast.LENGTH_SHORT).show()
        }else{
            storeData(edInf0, edInf1, edInf2, edInf3, edInf, userName)
        }

    }

    private fun storeData(
        inf0: String,
        inf1: String,
        inf2: String,
        inf3: String,
        infNote: String,
        userName: String
    ) {
        val map = HashMap<String, Any>()
        map["duong"] = inf0
        map["c1"] = inf1
        map["c2"] = inf2
        map["c3"] = inf3
        map["option"] = infNote
        map["userName"] = userName

        Firebase.firestore.collection("users")
            .document(preferences.getString("userPhoneNumber","")!!)
            .update(map).addOnSuccessListener {
                Toast.makeText(this, "Successful change", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }

    private fun loadUserInformation() {
        getInformation(preferences.getString("userPhoneNumber","")!!){
            binding.edPhoneNumber.setText(it.userPhoneNumber)
                binding.edUsername.setText(it.userName)
                binding.edInf0.setText(it.duong)
                binding.edInf1.setText(it.c1)
                binding.edInf2.setText(it.c2)
                binding.edInf3.setText(it.c3)
                binding.edInf.setText(it.option)
        }
//        Firebase.firestore.collection("users")
//            .document(references.getString("userPhoneNumber","")!!)
//            .get().addOnSuccessListener {
//                binding.edPhoneNumber.setText(it.getString("userPhoneNumber"))
//                binding.edUsername.setText(it.getString("userName"))
//                binding.edInf0.setText(it.getString("duong"))
//                binding.edInf1.setText(it.getString("c1"))
//                binding.edInf2.setText(it.getString("c2"))
//                binding.edInf3.setText(it.getString("c3"))
//                binding.edInf.setText(it.getString("option"))
//
//            }.addOnFailureListener {
//                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
//            }
    }
}