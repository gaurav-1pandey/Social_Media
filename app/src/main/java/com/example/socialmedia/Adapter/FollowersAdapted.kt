package com.example.socialmedia.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.Models.FollowerModel
import com.example.socialmedia.R
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
class FollowersAdapted(var context: Context):RecyclerView.Adapter<myfriendsvh>(){

    var database: FirebaseDatabase =Firebase.database("https://social-media-2cb36-default-rtdb.europe-west1.firebasedatabase.app")
    lateinit var ppiclink:String

    var list= ArrayList<FollowerModel>()
    fun updatelist(listx: ArrayList<FollowerModel>) {
        list.clear()
        list.addAll(listx)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myfriendsvh {
        val inflater= LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_profile_friends_rv,parent,false)
        return myfriendsvh(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: myfriendsvh, position: Int) {


        database.reference.child("Users").child(list.get(position).followerId).child("ppic").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    ppiclink=snapshot.getValue(String::class.java)!!
                    Picasso.get().load(ppiclink).placeholder(R.drawable.profilea)
                        .into(holder.profile_img)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"loading failed",Toast.LENGTH_SHORT).show()
            }

        })


    }
}
class myfriendsvh(var view:View):RecyclerView.ViewHolder(view){
    var profile_img=view.findViewById<CircleImageView>(R.id.noti_profile_rv_img)
    var profile_view=view.findViewById<View>(R.id.noti_profile_rv_img)
}