package com.example.socialmedia.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import com.example.socialmedia.LoginActivity
import com.example.socialmedia.Models.PostModel
import com.example.socialmedia.Models.UserModel
import com.example.socialmedia.R
import com.example.socialmedia.RegisterActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.sql.Time
import java.time.LocalDateTime
import java.time.LocalTime

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class AddFrag : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var storage:StorageReference
    var postcount:String="0"
    var tpostpicuri: Uri?=null
    val database = Firebase.database("https://social-media-2cb36-default-rtdb.europe-west1.firebasedatabase.app")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textdesc=view.findViewById<EditText>(R.id.textdescpriction)

        val postimageview=view.findViewById<ImageView>(R.id.imageView)
        val button=view.findViewById<Button>(R.id.btn_post)
        storage= FirebaseStorage.getInstance().reference
        button.setOnTouchListener { view, event ->
            // Define the scale animation
            val scaleAnimation = ScaleAnimation(
                1f, // From X
                1.2f, // To X (scale up by 20%)
                1f, // From Y
                1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            scaleAnimation.duration = 200 // Animation duration in milliseconds
            scaleAnimation.fillAfter = false
            scaleAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    // Reset the button scale to its original size
                    button.scaleX = 1f
                    button.scaleY = 1f
                }
                override fun onAnimationRepeat(animation: Animation?) {}
            })
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Handle touch down event
                    button.startAnimation(scaleAnimation)
                    true // Consume the ACTION_DOWN event
                }
                MotionEvent.ACTION_UP ->
                    {
                    button.performClick()
                    true // Consume the ACTION_UP event
                }
                else -> false
            }
        }

        var userpic=view.findViewById<CircleImageView>(R.id.noti_profile_rv_img)
        var username=view.findViewById<TextView>(R.id.tv_noti_msg)
        var profession=view.findViewById<TextView>(R.id.tv_noti_rv_time)
        database.reference.child("Users").child("${FirebaseAuth.getInstance().currentUser?.uid}").addValueEventListener(
            object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        var userdata=snapshot.getValue(UserModel::class.java)
                        Picasso.get().load(userdata?.ppic).into(userpic)
                        username.text=userdata?.username
                        profession.text=userdata?.profession
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }
        )

        textdesc.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Toast.makeText(context,"nothing",Toast.LENGTH_SHORT)
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (textdesc.text.length>0){
                    button.setBackgroundResource(R.drawable.enabled_post_btn_bg)
                    button.isEnabled=true
                }
                else
                {
                    button.setBackgroundResource(R.drawable.post_btn_bg)
                    button.isEnabled=false
                }
            }
            override fun afterTextChanged(s: Editable?) {
                Toast.makeText(context,"not nothing",Toast.LENGTH_SHORT)
            }
        })


        val getpostimg=registerForActivityResult(ActivityResultContracts.GetContent()){
            postimageview.visibility=View.VISIBLE
            postimageview.setImageURI(it!!)
            tpostpicuri=it
        }
        val btngetimg=view.findViewById<ImageButton>(R.id.imageButton)
        btngetimg.setOnClickListener{
            getpostimg.launch("image/*")
        }

        button.setOnClickListener{
            database.reference.child("Users").child("${FirebaseAuth.getInstance().currentUser?.uid}")
                .child("postcount").addListenerForSingleValueEvent(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()){
                            postcount=snapshot.getValue(String::class.java)!!
                        }
                        else {
                            database.reference.child("Users").child("${FirebaseAuth.getInstance().currentUser?.uid}")
                                .child("postcount").setValue("1")
                            postcount="1"
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

            val imageref=storage.child("photos").child("${FirebaseAuth.getInstance().currentUser?.uid}")
                .child("posts").child(LocalTime.now().toString())
            imageref.putFile(tpostpicuri!!).addOnSuccessListener {

                    Toast.makeText(context,"Image uploadedd successfully",Toast.LENGTH_SHORT).show()
                    database.reference.child("Users").child("${FirebaseAuth.getInstance().currentUser?.uid}")
                        .child("postcount").setValue("${postcount.toInt()+1}")

                    imageref.downloadUrl.addOnSuccessListener{postdlink->

                        val pm=PostModel("noname",textdesc.text.toString(),0,0,0,false,false,false,"${FirebaseAuth.getInstance().currentUser?.uid}",postdlink.toString(),LocalDateTime
                            .now().toString(),"${postcount.toInt()+1}")

                        database.reference.child("Users").child("${FirebaseAuth.getInstance().currentUser?.uid}").child("posts")
                            .child("${postcount.toInt()+1}").setValue(pm)



                    }.addOnSuccessListener({
                        postimageview.visibility=View.GONE
                        textdesc.text.clear()
                    })

                }


        }
    }
}