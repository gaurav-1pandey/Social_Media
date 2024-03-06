package com.example.socialmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.database




import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class MySharedPreferences(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    // Function to save a string value to SharedPreferences
    fun saveString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    // Function to retrieve a string value from SharedPreferences
    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    // Add similar functions for other data types as needed (e.g., Int, Boolean, etc.)
}



class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var userpass: String
    lateinit var useremail: String
    lateinit var etemail: EditText
    lateinit var etpass: EditText

    lateinit var shard:MySharedPreferences




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var anim=findViewById<LottieAnimationView>(R.id.lot_reg)
        anim.setOnClickListener({
            anim.playAnimation()
        })

        auth = Firebase.auth
        shard=MySharedPreferences(applicationContext)



        var btnlogin=findViewById<Button>(R.id.btn_login)
        btnlogin.setOnClickListener {
//            startActivity(Intent(this,MainActivity::class.java))

            etemail = findViewById(R.id.ed_reg_name)
            etpass = findViewById(R.id.ed_login_pass)

            useremail = etemail.text.toString()
            userpass = etpass.text.toString()

            auth.signInWithEmailAndPassword(useremail, userpass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login Successfull", Toast.LENGTH_SHORT).show()
                        shard.saveString("islogin", "yes")

                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "No user found, check your connection or kindly register ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

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

    override fun onStart() {
        super.onStart()
        if (shard.getString("islogin")=="yes"){
            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            finish()
        }
    }
}