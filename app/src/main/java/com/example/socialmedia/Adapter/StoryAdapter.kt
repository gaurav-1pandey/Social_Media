package com.example.socialmedia.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.socialmedia.R
import com.example.socialmedia.StoryModel
import com.makeramen.roundedimageview.RoundedImageView
import de.hdodenhof.circleimageview.CircleImageView

class StoryAdapter(val context:Context, var list: ArrayList<StoryModel>) : RecyclerView.Adapter<mystoryVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mystoryVH {
        var inflater=LayoutInflater.from(parent.context)
        var myview = inflater.inflate(R.layout.layout_story_rv,parent,false)
        return mystoryVH(myview)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: mystoryVH, position: Int) {
        holder.name.setText(list.get(position).name)
        holder.profileimg.setImageDrawable(holder.view.resources.getDrawable(list.get(position).profile))
        holder.storyimg.setImageDrawable(holder.view.resources.getDrawable(list.get(position).story))
        Log.d("callbyme","call{$position}")



//        Glide.with(holder.view)
//            .load(list.get(position).story)
//            .placeholder(list.get(position).story)
//            .error(list.get(position).story)
//            .apply(RequestOptions().centerCrop())
//            .into(holder.storyimg)
    }
}

class mystoryVH(var view:View):RecyclerView.ViewHolder(view){
    var name=view.findViewById<TextView>(R.id.tv_name_rv)
    var storyimg=view.findViewById<RoundedImageView>(R.id.story_rv_img)
    var profileimg=view.findViewById<CircleImageView>(R.id.profile_rv_img)

}