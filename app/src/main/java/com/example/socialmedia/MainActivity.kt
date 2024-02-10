package com.example.socialmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.socialmedia.Fragments.AddFrag
import com.example.socialmedia.Fragments.HomeFrag
import com.example.socialmedia.Fragments.NotificationFrag
import com.example.socialmedia.Fragments.ProfileFrag
import com.example.socialmedia.Fragments.SearchFrag
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import java.util.zip.Inflater


class MainActivity : AppCompatActivity() {
    lateinit var bottomNav: ChipNavigationBar
    lateinit var toolbar:Toolbar
    lateinit var shard:MySharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav=findViewById(R.id.chipNavigationBar)
        bottomNav.setItemSelected(R.id.home,true)
        shard= MySharedPreferences(applicationContext)


        toolbar=findViewById(R.id.toolbar)
        toolbar.title="My Profile"
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        toolbar.visibility= View.INVISIBLE
        setSupportActionBar(toolbar)


        toolbar.setOnMenuItemClickListener{menu->
            when(menu.itemId){
                R.id.setting_menu_item-> {

                    Firebase.auth.signOut()
                    shard.saveString("islogin","no")
                    finish()
                    startActivity(Intent(this,LoginActivity::class.java))



                    true

                }
                else -> false
            }

        }





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
                toolbar.visibility= View.INVISIBLE

            }
            else if (id==R.id.noti){
                k=1
                toolbar.visibility= View.INVISIBLE

            }else if (id==R.id.add){
                k=2
                toolbar.visibility= View.INVISIBLE

            }else if (id==R.id.search){
                k=3
                toolbar.visibility= View.INVISIBLE

            }else if (id==R.id.profile){
                k=4
                toolbar.visibility= View.VISIBLE

            }else {
                k=0
            }


            selected =l.get(k)

            supportFragmentManager.beginTransaction().replace(R.id.frame,selected).commit()

            true

        }
        supportFragmentManager.beginTransaction().replace(R.id.frame,HomeFrag()).commit()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbarmenu,menu)
        return true
    }
}