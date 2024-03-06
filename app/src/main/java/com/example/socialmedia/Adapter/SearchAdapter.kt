package com.example.socialmedia.Adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.Models.FollowerModel
import com.example.socialmedia.Models.UserModel
import com.example.socialmedia.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.time.LocalTime
class SearchAdapter(var context: Context) : RecyclerView.Adapter<mysearchvh>() {




    var storelist = ArrayList<UserModel>()
    var database: FirebaseDatabase=Firebase.database("https://social-media-2cb36-default-rtdb.europe-west1.firebasedatabase.app")
    lateinit var auth: FirebaseAuth
    var list = ArrayList<UserModel>()
    var arrayfollowers:ArrayList<FollowerModel> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mysearchvh {
        var inflater = LayoutInflater.from(parent.context)
        var vi = inflater.inflate(R.layout.layout_search_rv, parent, false)
        return mysearchvh(vi)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    fun filter(s: String) {
        list.clear()
        for (i in storelist) {
            if (i.username.lowercase().contains(s.lowercase()) == true || i.profession.lowercase()
                    .contains(s.lowercase()) == true
            ) {
                list.add(i)
            }
        }
        notifyDataSetChanged()
    }
    fun updatelist(listx: ArrayList<UserModel>) {
        storelist.clear()
        storelist.addAll(listx)
        list.clear()
        list.addAll(storelist)
        notifyDataSetChanged()
    }
    fun updatefollower(lst:ArrayList<FollowerModel>){
        arrayfollowers.clear()
        arrayfollowers.addAll(lst)
        notifyDataSetChanged()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: mysearchvh, position: Int) {
        Picasso.get().load(list.get(position).ppic).placeholder(R.drawable.profilea)
            .into(holder.ppic)
        holder.username.text = list.get(position).username
        holder.profession.text = list.get(position).profession




        var flag=0
        for (i in arrayfollowers){
            if (i.followerId.equals(list.get(position).userid)){
                flag=1

                break
                }
        }
        if (flag==0){
            holder.btn_follow.text="follow"
            holder.btn_follow.setBackgroundResource(R.drawable.btn_follow_bg)
        }
        else{
            holder.btn_follow.text = "Following"
            holder.btn_follow.setBackgroundResource(R.drawable.btn_unfollow_bg)
        }


        holder.btn_follow.setOnClickListener {
            if (holder.btn_follow.text.toString().lowercase().equals("follow")) {
                holder.btn_follow.text = "Following"
                holder.btn_follow.setBackgroundResource(R.drawable.btn_unfollow_bg)
//                database= Firebase.database("https://social-media-2cb36-default-rtdb.europe-west1.firebasedatabase.app")
                auth= Firebase.auth

                var fm=FollowerModel("${auth.currentUser?.uid!!}",LocalTime.now().toString())
                var fm2=FollowerModel("${list.get(position).userid}",LocalTime.now().toString())
                val fc=list.get(position).followerCount.toInt()+1

                database.reference.child("Users")
                    .child(list.get(position).userid)
                    .child("followers")
                    .child("${auth.currentUser?.uid}")
                    .setValue(fm).addOnSuccessListener {
                        database.reference.child("Users")
                            .child(list.get(position).userid)
                            .child("followerCount")
                            .setValue("${fc}").addOnSuccessListener {
                                Toast.makeText(context,"following",Toast.LENGTH_SHORT).show()
                            }
                    }

                database.reference.child("Users").child(auth.currentUser?.uid!!)
                    .child("following")
                    .child("${list.get(position).userid}")
                    .setValue(fm2)
            }
            else {
                holder.btn_follow.text = "follow"
                holder.btn_follow.setBackgroundResource(R.drawable.btn_follow_bg)
            }
        }
    }
}
class mysearchvh(var view: View) : RecyclerView.ViewHolder(view) {
    var username = view.findViewById<TextView>(R.id.username)
    var profession = view.findViewById<TextView>(R.id.profession)
    var btn_follow = view.findViewById<Button>(R.id.btn_follow)
    var ppic = view.findViewById<CircleImageView>(R.id.search_profile_pic)
}