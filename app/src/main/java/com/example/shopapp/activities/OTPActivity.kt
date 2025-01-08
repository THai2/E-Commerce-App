package com.example.shopapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shopapp.MainActivity
import com.example.shopapp.R
import com.example.shopapp.databinding.ActivityOtpactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OTPActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpactivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)

        binding.btnVerify.setOnClickListener{
            if(binding.edUserOTP.text!!.isEmpty()){
                Toast.makeText(this, "Please provide OTP", Toast.LENGTH_SHORT).show()
            }else{
                verifyUser(binding.edUserOTP.text!!.toString())
            }
        }

        setContentView(binding.root)
    }

    private fun verifyUser(userOTP: String) {


        val credential = PhoneAuthProvider.getCredential(intent.getStringExtra("verificationId")!!, userOTP)

        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
                    val editor = preferences.edit()

                    editor.putString("userPhoneNumber", intent.getStringExtra("userPhoneNumber")!!)
                    editor.apply()

                    startActivity(Intent(this,MainActivity::class.java))
                    finish()

                    val user = task.result?.user
                } else {

                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
    }
}