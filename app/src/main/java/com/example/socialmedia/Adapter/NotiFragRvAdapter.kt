package com.example.socialmedia.Adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.Models.NotificationModel
import com.example.socialmedia.R
import de.hdodenhof.circleimageview.CircleImageView

class NotiFragRvAdapter(var context: Context,var list:ArrayList<NotificationModel>):RecyclerView.Adapter<myNotiVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myNotiVH {
        var inflater =LayoutInflater.from(parent.context)
        var view=inflater.inflate(R.layout.rv_noti_frag,parent,false)
        return myNotiVH(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: myNotiVH, position: Int) {
        holder.noti.text=Html.fromHtml(list.get(position).noti)
        holder.profile.setImageBitmap(resizeDrawable(context,list.get(position).profile,200,200))
        holder.time.text=list.get(position).time
    }

}

class myNotiVH(var view: View):RecyclerView.ViewHolder(view){
    var noti=view.findViewById<TextView>(R.id.tv_noti_msg)
    var time=view.findViewById<TextView>(R.id.tv_noti_rv_time)
    var profile=view.findViewById<CircleImageView>(R.id.noti_profile_rv_img)

}