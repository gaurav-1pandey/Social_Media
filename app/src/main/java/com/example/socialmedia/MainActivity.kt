package com.example.socialmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.socialmedia.Fragments.AddFrag
import com.example.socialmedia.Fragments.HomeFrag
import com.example.socialmedia.Fragments.NotificationFrag
import com.example.socialmedia.Fragments.ProfileFrag
import com.example.socialmedia.Fragments.SearchFrag
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MainActivity : AppCompatActivity() {
    lateinit var bottomNav: ChipNavigationBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav=findViewById(R.id.chipNavigationBar)
        bottomNav.setItemSelected(R.id.home,true)

        bottomNav.setOnItemSelectedListener { id->
            var selected:Fragment=HomeFrag()
            var l=ArrayList<Fragment>()
            l.add(HomeFrag())
            l.add(NotificationFrag())
            l.add(AddFrag())
            l.add(SearchFrag())
            l.add(ProfileFrag())
            var k:Int
            if (id==R.id.home){
                k=0
            }
            else if (id==R.id.noti){
                k=1
            }else if (id==R.id.add){
                k=2
            }else if (id==R.id.search){
                k=3
            }else if (id==R.id.profile){
                k=4
            }else {
                k=0
            }


            selected =l.get(k)

            supportFragmentManager.beginTransaction().replace(R.id.frame,selected).commit()

            true

        }
        supportFragmentManager.beginTransaction().replace(R.id.frame,HomeFrag()).commit()

    }
}