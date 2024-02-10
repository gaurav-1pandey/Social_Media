package com.example.socialmedia.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.Models.FollowerModel
import com.example.socialmedia.R
import de.hdodenhof.circleimageview.CircleImageView
class ProfileFriendsAdapted(var context: Context,var list: ArrayList<FollowerModel>):RecyclerView.Adapter<myfriendsvh>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myfriendsvh {
        val inflater= LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_profile_friends_rv,parent,false)
        return myfriendsvh(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: myfriendsvh, position: Int) {
    }
}
class myfriendsvh(var view:View):RecyclerView.ViewHolder(view){
    var profile_img=view.findViewById<CircleImageView>(R.id.noti_profile_rv_img)
    var profile_view=view.findViewById<View>(R.id.noti_profile_rv_img)
}