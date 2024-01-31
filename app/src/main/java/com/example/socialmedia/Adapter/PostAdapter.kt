package com.example.socialmedia.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.Models.PostModel
import com.example.socialmedia.R

class PostAdapter(var context: Context,var post:ArrayList<PostModel>):RecyclerView.Adapter<mypostvh>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mypostvh {
        var inflater=LayoutInflater.from(parent.context)
        var v=inflater.inflate(R.layout.layout_post_rv,parent,false)
        return mypostvh(v)
    }

    override fun getItemCount(): Int {
        return post.size   }

    override fun onBindViewHolder(holder: mypostvh, position: Int) {
        holder.username.text=post.get(position).username
        holder.about.text=post.get(position).about
        holder.like.text=post.get(position).like.toString()
        holder.share.text=post.get(position).comment.toString()
        holder.comment.text=post.get(position).share.toString()


        if(post.get(position).isliked){
            holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_post_liked,0,0,0)
        }
        else{
            holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_post_like,0,0,0)        }


        if(post.get(position).isshared){
            holder.share.setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_post_shared,0,0,0)        }
        else{
            holder.share.setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_post_forward,0,0,0)        }

    }

}

class mypostvh(view: View):RecyclerView.ViewHolder(view){

    var username =view.findViewById<TextView>(R.id.tv_post_name)
    var about =view.findViewById<TextView>(R.id.tv_post_about)
    var like =view.findViewById<TextView>(R.id.tv_post_like)
    var comment =view.findViewById<TextView>(R.id.tv_post_comment)
    var share =view.findViewById<TextView>(R.id.tv_post_share)

}