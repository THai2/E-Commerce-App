package com.example.shopapp.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.shopapp.R
import com.example.shopapp.databinding.ActivityRegisterBinding
import com.example.shopapp.method.addUser
import com.example.shopapp.model.UserModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var  preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        preferences = this.getSharedPreferences("user", MODE_PRIVATE)
        binding.tvRegister1.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.btnRegister.setOnClickListener{
            validateUser()
        }

        setContentView(binding.root)
    }

    private fun validateUser() {
        if (binding.edUsername.text.isEmpty() ||
            binding.edPhoneNumber.text.isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }else{
            storeData()
        }
    }

    private fun storeData() {
        val builder = AlertDialog.Builder(this)
            .setTitle("Loading....")
            .setMessage("Please wait")
            .setCancelable(false)
            .create()
        builder.show()

        val editor = preferences.edit()

        editor.putString("userPhoneNumber",binding.edPhoneNumber.text.toString())
        editor.putString("userName",binding.edUsername.text.toString())
        editor.apply()

        val data = UserModel (userName = binding.edUsername.text.toString(), userPhoneNumber =  binding.edPhoneNumber.text.toString() )

        addUser(data){
            if (it){
                Toast.makeText(this, "User registered", Toast.LENGTH_SHORT).show()
                builder.dismiss()
                openLogin()
            }else{
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                builder.dismiss()
            }
            }
//        Firebase.firestore.collection("users").document(binding.edPhoneNumber.text.toString())
//            .set(data).addOnSuccessListener {
//                Toast.makeText(this, "User registered", Toast.LENGTH_SHORT).show()
//
//                builder.dismiss()
//
//                openLogin()
//            }.addOnFailureListener {
//                builder.dismiss()
//                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
//            }

    }

    private fun openLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}