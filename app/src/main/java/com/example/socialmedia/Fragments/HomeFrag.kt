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
import com.example.socialmedia.Models.PostModel
import com.example.socialmedia.R
import com.example.socialmedia.Models.StoryModel
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFrag : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var dlcp="kk"
    var dlpp="kk"



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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFrag.
         */
        // TODO: Rename and change types and number of parameters
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



        arraypost.add(PostModel("gaurav","NIEt,Bachlaor of Technology",R.drawable.gaurav,R.drawable.storyb,47,21,7))
        arraypost.add(PostModel("Ravi","Good man,Ballia,Post Office",R.drawable.profilea,R.drawable.storyc,49,25,11,false, isliked = true, isshared = false))
        arraypost.add(PostModel("Shubhangi","TD College Ballia,Bachlaor of Commerce",R.drawable.shubhangi,R.drawable.storyd,59,21,17,false,true,true))
        arraypost.add(PostModel("gaurav","NIEt,Bachlaor of Technology",R.drawable.gaurav,R.drawable.storyb,47,21,7))

        postrecyclerView=view.findViewById(R.id.post_rv)
        var postlayoutrv=LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
        postadapter=PostAdapter(view.context,arraypost)
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
        discover.setOnClickListener({


            kk.child("default_img").child("dp1.jpg").downloadUrl.addOnSuccessListener {
                dlcp=it.toString()
                dlpp=it.toString()
            }
            database.reference.child("default_links").child("dppic").setValue(dlpp)
            database.reference.child("default_links").child("dcpic").setValue(dlcp)
        })


    }
}