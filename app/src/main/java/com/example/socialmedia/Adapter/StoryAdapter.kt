package com.example.socialmedia.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.R
import com.example.socialmedia.Models.StoryModel
import com.makeramen.roundedimageview.RoundedImageView
import de.hdodenhof.circleimageview.CircleImageView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

fun resizeDrawable(context: Context, drawableResId: Int, targetWidth: Int, targetHeight: Int): Bitmap {
    // Decode the original drawable resource
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(context.resources, drawableResId, options)

    // Calculate the inSampleSize to reduce the image dimensions
    options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight)

    // Decode the drawable resource with the calculated inSampleSize
    options.inJustDecodeBounds = false
    return BitmapFactory.decodeResource(context.resources, drawableResId, options)
}

// Calculate the inSampleSize based on the target dimensions
private fun calculateInSampleSize(options: BitmapFactory.Options, targetWidth: Int, targetHeight: Int): Int {
    var inSampleSize = 1

    if (options.outHeight > targetHeight || options.outWidth > targetWidth) {
        val halfHeight = options.outHeight / 2
        val halfWidth = options.outWidth / 2

        while ((halfHeight / inSampleSize) >= targetHeight && (halfWidth / inSampleSize) >= targetWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}


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
        holder.profileimg.setImageBitmap(resizeDrawable(context,list.get(position).profile,200,200))
        holder.storyimg.setImageBitmap(resizeDrawable(context,list.get(position).story,200,300))
        Log.d("callbyme","call{$position}")

    }
}
class mystoryVH(var view:View):RecyclerView.ViewHolder(view){
    var name=view.findViewById<TextView>(R.id.tv_name_rv)
    var storyimg=view.findViewById<RoundedImageView>(R.id.story_rv_img)
    var profileimg=view.findViewById<CircleImageView>(R.id.noti_profile_rv_img)



}