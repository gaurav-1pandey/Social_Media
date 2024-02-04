package com.example.socialmedia.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.socialmedia.Fragments.NoitFragment
import com.example.socialmedia.Fragments.RequstFragment

class NotiFragPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager
) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        if (position==0){
            return NoitFragment()

        }
        else{
            return RequstFragment()
        }
    }


    override fun getPageTitle(position: Int): CharSequence? {

        var title:String?=null
        if (position==0){
            title="Notification"
        }
        else if (position==1){
            title="Request"
        }
        else{
            title="Notification"
        }
        return title
    }
}