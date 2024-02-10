package com.example.socialmedia.Fragments

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.Adapter.ProfileFriendsAdapted
import com.example.socialmedia.EditProfileActivity
import com.example.socialmedia.Models.FollowerModel
import com.example.socialmedia.Models.UserModel
import com.example.socialmedia.R
//import com.flaviofaria.kenburnsview.KenBurnsView
//import com.flaviofaria.kenburnsview.Transition
import com.flaviofaria.kenburnsview.KenBurnsView
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class ProfileFrag : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    lateinit var friendsAdapter:ProfileFriendsAdapted
    lateinit var rvfriend:RecyclerView
    lateinit var arrayfriend:ArrayList<FollowerModel>

    lateinit var tv_bio:TextView

    lateinit var btnchosimg:ImageView
    lateinit var kenBurnsView: KenBurnsView

    lateinit var storageReference:StorageReference
    lateinit var imageL:String
    lateinit var gotUser:UserModel

    lateinit var tv_name:TextView
    lateinit var tv_prof:TextView
    lateinit var tv_follower:TextView
    lateinit var tv_friends:TextView
    lateinit var tv_photos:TextView




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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        kenBurnsView = view.findViewById(R.id.img_anim)
        arrayfriend=ArrayList()
//        arrayfriend.add(FollowerModel(R.drawable.profilea))
//        arrayfriend.add(FollowerModel(R.drawable.profileb))
//        arrayfriend.add(FollowerModel(R.drawable.profilec))
//        arrayfriend.add(FollowerModel(R.drawable.profiled))
//        arrayfriend.add(FollowerModel(R.drawable.profilea))
//        arrayfriend.add(FollowerModel(R.drawable.profileb))
//        arrayfriend.add(FollowerModel(R.drawable.profilec))
//        arrayfriend.add(FollowerModel(R.drawable.profiled))
        rvfriend=view.findViewById(R.id.profile_friend_rv)
        friendsAdapter= ProfileFriendsAdapted(view.context,arrayfriend)
        var pf=view.findViewById<CircleImageView>(R.id.noti_profile_rv_img)
        rvfriend.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        rvfriend.adapter=friendsAdapter


        btnchosimg= view.findViewById(R.id.btn_choose_image)
        tv_bio=view.findViewById(R.id.textView11)



        storageReference=FirebaseStorage.getInstance().reference
        val database = Firebase.database("https://social-media-2cb36-default-rtdb.europe-west1.firebasedatabase.app")



        val getImage=registerForActivityResult(ActivityResultContracts.GetContent() ){

            var imageref=storageReference.child("cover_photo").child("${FirebaseAuth.getInstance().currentUser?.uid}")
            imageref.putFile(it!!).addOnSuccessListener {
                imageref.downloadUrl.addOnSuccessListener { downloaduri->
                    val imageuri=downloaduri.toString()
                    Toast.makeText(view.context,"upload passed",Toast.LENGTH_SHORT).show()
                    database.reference.child("Users").child("${FirebaseAuth.getInstance().currentUser?.uid}").child("cpic").setValue(imageuri)
                }
            }


        }

        tv_name=view.findViewById(R.id.tv_profile_name)
        val ppic=view.findViewById<CircleImageView>(R.id.noti_profile_rv_img)
        tv_prof=view.findViewById(R.id.tv_profile_prof)




        GlobalScope.launch(Dispatchers.IO) {
            // Fetch data from Firebase in the background thread
            database.reference.child("Users").child("${FirebaseAuth.getInstance().currentUser?.uid}")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            gotUser = snapshot.getValue(UserModel::class.java)!!
                            // Use withContext(Dispatchers.Main) to update UI elements
                            GlobalScope.launch(Dispatchers.Main) {
                                // Update UI with fetched data
                                Picasso.get().load(gotUser.cpic).into(kenBurnsView)
                                kenBurnsView.resume()

                                tv_name.text = gotUser.username
                                tv_prof.text = gotUser.profession
                                tv_bio.text = gotUser.bio
                                Picasso.get().load(gotUser.ppic).into(ppic)
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle database error
                    }
                })
        }


//        database.reference.child("Users").child("${FirebaseAuth.getInstance().currentUser?.uid}").addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()){
//                    gotUser=snapshot.getValue(UserModel::class.java)!!
//                    Toast.makeText(view.context,"${gotUser.username}",Toast.LENGTH_SHORT).show()
//                    Log.d("callbyme","${gotUser.cpic}")
//
//
//                    Picasso.get().load(gotUser.cpic).into(kenBurnsView)
//                    kenBurnsView.resume()
//
//                    tv_name.text=gotUser.username
//
//                    tv_prof.text=gotUser.profession
//                    tv_bio.text=gotUser.bio
//
//
//
//                    Picasso.get().load(gotUser.ppic).into(ppic)
//                }
//
//
//
//
//
//
//
//
//
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })

        tv_name.setOnClickListener{
            startActivity(Intent(view.context,EditProfileActivity::class.java))
        }




        btnchosimg.setOnClickListener{
//            openGallery()

            getImage.launch("image/*")

        }





    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
//            val selectedImage: Uri = data.data!!
//
//            // Set the selected image to the ImageView
//            kenBurnsView.setImageURI(selectedImage)
//        }
//    }


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1)
    }



}