package com.example.socialmedia.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.Adapter.PostAdapter
import com.example.socialmedia.Adapter.StoryAdapter
import com.example.socialmedia.Models.FollowerModel
import com.example.socialmedia.Models.PostModel
import com.example.socialmedia.R
import com.example.socialmedia.Models.StoryModel
import com.example.socialmedia.Models.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFrag : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    var dlcp="kk"
    var dlpp="kk"
    var postarray=ArrayList<PostModel>()




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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    lateinit var storyrecyclerView:RecyclerView
    lateinit var postrecyclerView:RecyclerView

    lateinit var storyadapter:StoryAdapter
    lateinit var postadapter:PostAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var array=ArrayList<StoryModel>()
        var arraypost=ArrayList<PostModel>()

        storyrecyclerView=view.findViewById(R.id.story_rv)
        array.add(StoryModel("Rajnish",R.drawable.storya,1,R.drawable.profilea))
        array.add(StoryModel("Ankit",R.drawable.storyb,1,R.drawable.profileb))
        array.add(StoryModel("Mamta",R.drawable.storyc,1,R.drawable.profilea))
        array.add(StoryModel("Priyanshu",R.drawable.storyd,1,R.drawable.profilea))

        postrecyclerView=view.findViewById(R.id.post_rv)
        var postlayoutrv=LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
        postadapter=PostAdapter(view.context)
        postrecyclerView.layoutManager=postlayoutrv
        postrecyclerView.isNestedScrollingEnabled=true
        postrecyclerView.adapter=postadapter



        storyadapter= StoryAdapter(view.context,array)
        var layoutrv=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        storyrecyclerView.layoutManager=layoutrv
        storyrecyclerView.isNestedScrollingEnabled=false
        storyrecyclerView.adapter=storyadapter

        var discover=view.findViewById<TextView>(R.id.tv_home_discover)
        val database= Firebase.database("https://social-media-2cb36-default-rtdb.europe-west1.firebasedatabase.app")

        val kk= FirebaseStorage.getInstance().reference
        database.reference.child("Users")
        discover.setOnClickListener {


            kk.child("default_img").child("dp1.jpg").downloadUrl.addOnSuccessListener {
                dlcp = it.toString()
                dlpp = it.toString()
            }
            database.reference.child("default_links").child("dppic").setValue(dlpp)
            database.reference.child("default_links").child("dcpic").setValue(dlcp)
        }


        database.reference.child("Users").child("${FirebaseAuth.getInstance().currentUser?.uid}")
            .child("following").addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (k in snapshot.children){
                            arraypost.clear()
                            var fm=k.getValue(FollowerModel::class.java)
                            database.reference.child("Users").child(fm!!.followerId).child("posts").addValueEventListener(
                                object :ValueEventListener{
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        if (snapshot.exists()){

                                            for (x in snapshot.children){
                                                var tapost=x.getValue(PostModel::class.java)
                                                arraypost.add(tapost!!)
                                            }
                                            postadapter.updatelist(arraypost)
                                        }
                                    }
                                    override fun onCancelled(error: DatabaseError) {
                                        TODO("Not yet implemented")
                                    }

                                }
                            )
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
//        database.reference.child("Users").child("${FirebaseAuth.getInstance().currentUser?.uid}").child("posts")
//            .addValueEventListener(object :ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                arraypost.clear()
//                for (i in snapshot.children){
//                    var tpostmodel=i.getValue(PostModel::class.java)
//                    arraypost.add(tpostmodel!!)
//                }
//
//                postadapter.updatelist(arraypost)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })


        var dashboardpic=view.findViewById<CircleImageView>(R.id.noti_profile_rv_img)

        database.reference.child("Users").child("${FirebaseAuth.getInstance().currentUser?.uid}").addValueEventListener(
            object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        var userdata=snapshot.getValue(UserModel::class.java)
                        Picasso.get().load(userdata?.ppic).into(dashboardpic)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }
        )
    }
}