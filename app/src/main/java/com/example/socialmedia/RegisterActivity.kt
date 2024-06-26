package com.example.socialmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.example.socialmedia.Models.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var useremail: String
    lateinit var username: String
    lateinit var userprofession: String
    lateinit var userpass: String
    lateinit var userlist:ArrayList<UserModel>
    lateinit var etemail: EditText
    lateinit var etname: EditText
    lateinit var etprofession: EditText
    lateinit var etpass: EditText
    lateinit var btnSignup: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth
        val database = Firebase.database("https://social-media-2cb36-default-rtdb.europe-west1.firebasedatabase.app")
        var anim = findViewById<LottieAnimationView>(R.id.lot_reg)
        anim.setOnClickListener({
            anim.playAnimation()
        })
        userlist=ArrayList()
        var tv_already_user = findViewById<TextView>(R.id.tv_already_user)
        tv_already_user.setOnClickListener {
            database.getReference("Users").addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (usr in snapshot.children){
                        val u=usr.getValue(UserModel::class.java)
                        u?.let {
                            userlist.add(it)
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@RegisterActivity,"${error.message}",Toast.LENGTH_SHORT).show()
                }
            })
            for (i in userlist){
                Toast.makeText(this,"${i.username} ${i.email}",Toast.LENGTH_SHORT).show()
            }
        }
        btnSignup = findViewById(R.id.btn_reg)
        etname = findViewById(R.id.ed_reg_name)
        etemail = findViewById(R.id.ed_reg_email)
        etprofession = findViewById(R.id.ed_reg_profession)
        etpass = findViewById(R.id.ed_pass)
        btnSignup.setOnClickListener {
            username = etname.text.toString()
            userpass = etpass.text.toString()
            useremail = etemail.text.toString()
            userprofession = etprofession.text.toString()
            if (useremail.isEmpty() || userpass.isEmpty() || useremail.isEmpty() || userprofession.isEmpty()) {
                Toast.makeText(this, "Please fill all the required feilds", Toast.LENGTH_SHORT)
                    .show()
            } else {
                auth.createUserWithEmailAndPassword(useremail, userpass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "User Created Successfully", Toast.LENGTH_SHORT)
                                .show()
                            var userID: String = task.result.user!!.uid
                            var user = UserModel(username, useremail, userprofession, userpass)
                            user.userid=userID
                            database.reference.child("Users").child(userID).setValue(user)
                            Toast.makeText(
                                this,
                                "User Inserted in DB Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}
