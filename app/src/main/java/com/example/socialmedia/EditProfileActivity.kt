package com.example.socialmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.socialmedia.Models.UserModel
import com.example.socialmedia.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class EditProfileActivity : AppCompatActivity() {
    lateinit var usrname:TextInputEditText
    lateinit var nickname:TextInputEditText
    lateinit var profession:TextInputEditText
    lateinit var bio:TextInputEditText
    lateinit var dob:TextInputEditText
    lateinit var save:Button
    lateinit var changecpic:ImageView
    lateinit var changeppic:ImageView


    lateinit var storage: StorageReference


    lateinit var cpiclink:String
    lateinit var ppiclink:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)


        usrname=findViewById(R.id.ed_name)
        nickname=findViewById(R.id.ed_nick_name)
        profession=findViewById(R.id.ed_profession)
        bio=findViewById(R.id.ed_bio)
        dob=findViewById(R.id.ed_dob)
        save=findViewById(R.id.btnsave)
        var cuser=FirebaseAuth.getInstance().currentUser


        val database = Firebase.database("https://social-media-2cb36-default-rtdb.europe-west1.firebasedatabase.app")
        storage= FirebaseStorage.getInstance().reference

        val getcpic=registerForActivityResult(ActivityResultContracts.GetContent()){
            var imageref=storage.child("photos")
                .child("${FirebaseAuth.getInstance().currentUser?.uid}").child("cpic")
            imageref.putFile(it!!).addOnSuccessListener {

                imageref.downloadUrl.addOnSuccessListener { url->
                    cpiclink=url.toString()
                    Toast.makeText(this@EditProfileActivity,"Cover pic uploaded",Toast.LENGTH_SHORT).show()
                }
            }
        }



        ppiclink="https://firebasestorage.googleapis.com/v0/b/social-media-2cb36.appspot.com/o/default_img%2Fdp1.jpg?alt=media&token=f328ede1-aeb6-4859-b8f0-a03e0932eeb3"
        cpiclink="https://firebasestorage.googleapis.com/v0/b/social-media-2cb36.appspot.com/o/default_img%2Fdp1.jpg?alt=media&token=f328ede1-aeb6-4859-b8f0-a03e0932eeb3"

        val getppic=registerForActivityResult(ActivityResultContracts.GetContent()){
            var imageref=storage.child("photos")
                .child("${FirebaseAuth.getInstance().currentUser?.uid}").child("ppic")
            imageref.putFile(it!!).addOnSuccessListener {
                imageref.downloadUrl.addOnSuccessListener { url->
                    ppiclink=url.toString()
                    Toast.makeText(this@EditProfileActivity,"Profile pic uploaded",Toast.LENGTH_SHORT).show()
                }
            }
        }
        changeppic=findViewById(R.id.btn_change_profile)
        changecpic=findViewById(R.id.btn_change_cover)

        changecpic.setOnClickListener{
            getcpic.launch("image/*")
        }

        changeppic.setOnClickListener{
            getppic.launch("image/*")
        }




        save.setOnClickListener{





            var a="Name Not Updated Successfully"
            var b="You must wait on edit screen for some time"

            var c=usrname.text.toString()
            var d=profession.text.toString()
            var e=bio.text.toString()

            if (c.isEmpty()){
                c=a
            }
            if (d.isEmpty()){
                d="Edit your profession"
            }
            if(e.isEmpty()){
                e=b
            }
            var usr=UserModel(username = c,email=cuser?.email.
                    toString(), profession = d,
                password = "no pass saved", cpic = cpiclink, ppic = ppiclink, bio = e,
                nickname = nickname.text.toString(), dob = dob.text.toString(), userid = "${Firebase.auth.currentUser?.uid}")

            database.reference.child("Users").
            child("${FirebaseAuth.getInstance().
            currentUser?.uid}").setValue(usr).addOnSuccessListener({
                Toast.makeText(this@EditProfileActivity,"data updated succcessfully",Toast.LENGTH_SHORT).show()

            })



        }










    }
}