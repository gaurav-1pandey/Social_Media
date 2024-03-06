package com.example.socialmedia.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.Models.PostModel
import com.example.socialmedia.Models.UserModel
import com.example.socialmedia.R
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class PostAdapter(var context: Context):RecyclerView.Adapter<mypostvh>(){
    var post=ArrayList<PostModel>()

    var database: FirebaseDatabase = Firebase.database("https://social-media-2cb36-default-rtdb.europe-west1.firebasedatabase.app")


    fun updatelist(listx:ArrayList<PostModel>){
        post.clear()
        post.addAll(listx)
        notifyDataSetChanged()
    }

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
        else {
            holder.share.setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_post_forward,0,0,0)
        }



            database.reference.child("Users").child(post.get(position).userid).addValueEventListener(
                object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()){
                            var userdata=snapshot.getValue(UserModel::class.java)
                            holder.username.text=userdata!!.username
                            Picasso.get().load(userdata.ppic).placeholder(R.drawable.white_bg).into(holder.image_profile)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                }
            )
            Picasso.get().load(post.get(position).postlink).placeholder(R.drawable.jaisrisitaram).into(holder.image_post)


//        holder.image_profile.setImageDrawable(holder.view.resources.getDrawable(post.get(position).profile))
//        holder.image_post.setImageDrawable(holder.view.resources.getDrawable(post.get(position).post))

    }
}

class mypostvh(var view: View):RecyclerView.ViewHolder(view){

    var username =view.findViewById<TextView>(R.id.tv_post_name)
    var about =view.findViewById<TextView>(R.id.tv_post_about)
    var like =view.findViewById<TextView>(R.id.tv_post_like)
    var comment =view.findViewById<TextView>(R.id.tv_post_comment)
    var share =view.findViewById<TextView>(R.id.tv_post_share)

    var image_profile = view.findViewById<CircleImageView>(R.id.noti_profile_rv_img)
    var image_post = view.findViewById<RoundedImageView>(R.id.post_image)
    var image_post_more_option = view.findViewById<ImageView>(R.id.img_post_more_option)



}