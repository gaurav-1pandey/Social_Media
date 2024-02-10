package com.example.socialmedia.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.Models.UserModel
import com.example.socialmedia.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class SearchAdapter(var context: Context) :
    RecyclerView.Adapter<mysearchvh>() {

        var storelist=ArrayList<UserModel>()
        var list=ArrayList<UserModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mysearchvh {
        var inflater = LayoutInflater.from(parent.context)
        var vi = inflater.inflate(R.layout.layout_search_rv, parent, false)
        return mysearchvh(vi)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun filter(s:String){
        list.clear()
        for (i in storelist){
            if (i.username.lowercase().contains(s.lowercase())==true || i.profession.lowercase().contains(s.lowercase())==true){
                list.add(i)
            }
        }
        notifyDataSetChanged()
    }
    fun updatelist(listx:ArrayList<UserModel>){
        storelist.clear()
        storelist.addAll(listx)



        list.clear()
        list.addAll(storelist)
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: mysearchvh, position: Int) {
        Picasso.get().load(list.get(position).ppic).placeholder(R.drawable.profilea).into(holder.ppic)
        holder.username.text=list.get(position).username
        holder.profession.text=list.get(position).profession

        holder.btn_follow.setOnClickListener{
            if(holder.btn_follow.text.toString().lowercase().equals("follow")){
                holder.btn_follow.text="Following"
                holder.btn_follow.setBackgroundResource(R.drawable.btn_unfollow_bg)
            }
            else{
                holder.btn_follow.text="follow"
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