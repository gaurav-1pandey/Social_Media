package com.example.socialmedia.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.Adapter.SearchAdapter
import com.example.socialmedia.Models.FollowerModel
import com.example.socialmedia.Models.UserModel
import com.example.socialmedia.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFrag : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var database: FirebaseDatabase
    lateinit var auth: FirebaseAuth
    lateinit var rv:RecyclerView
    lateinit var flist:ArrayList<UserModel>
    lateinit var adapter:SearchAdapter
    var arrayfollowers=ArrayList<FollowerModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database= Firebase.database("https://social-media-2cb36-default-rtdb.europe-west1.firebasedatabase.app")
        auth=Firebase.auth
        rv=view.findViewById(R.id.rv_search)
        flist= ArrayList()
        var layoutmanager=LinearLayoutManager(view.context)
        adapter=SearchAdapter(view.context)





        database.reference.child("Users").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    flist.clear()
                    for(i in snapshot.children){
                        val u=i.getValue(UserModel::class.java)
                        flist.add(u!!)

                    }
                    adapter.updatelist(flist)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        arrayfollowers.add(FollowerModel("ff","ss"))

        database.reference.child("Users").child("${FirebaseAuth.getInstance().currentUser?.uid}")
            .child("followers").addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                        for (i in snapshot.children) {
                            val u = i.getValue(FollowerModel::class.java)
                            arrayfollowers.add(u!!)

                        }
                        adapter.updatefollower(arrayfollowers)

                    }



                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(),"Failed", Toast.LENGTH_SHORT).show()
                }

            })




        rv.adapter=adapter
        rv.layoutManager=layoutmanager



        var search=view.findViewById<SearchView>(R.id.searchView)

        search.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String?): Boolean {
                if (s!=null){
                    adapter.filter(s)
                }
                return true
            }

        })




    }
}