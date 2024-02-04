package com.example.socialmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.Firebase
import com.google.firebase.database.database

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var anim=findViewById<LottieAnimationView>(R.id.lot_reg)
        anim.setOnClickListener({
            anim.playAnimation()
        })


        var btnlogin=findViewById<Button>(R.id.btn_login)
        btnlogin.setOnClickListener({
            startActivity(Intent(this,MainActivity::class.java))
        })

        var tv_reg=findViewById<TextView>(R.id.tv_register_user)

        tv_reg.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }

//        val myRef = database.getReference("message")
//
//        myRef.setValue("Hello, World!")
//
//
//        database.reference.child("users").child("12345").setValue("userModel")
//            .addOnSuccessListener {
//                Log.d("Firebase", "Data written successfully")
//            }
//            .addOnFailureListener { e ->
//                Log.e("Firebase", "Error writing data to the database", e)
//            }

    }
}