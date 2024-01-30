package com.example.socialmedia.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.Adapter.StoryAdapter
import com.example.socialmedia.R
import com.example.socialmedia.StoryModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFrag : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    lateinit var recyclerView:RecyclerView
    lateinit var adapter:StoryAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var array=ArrayList<StoryModel>()

        recyclerView=view.findViewById(R.id.story_rv)
        array.add(StoryModel("Rajnish",R.drawable.storya,1,R.drawable.profilea))
        array.add(StoryModel("Ankit",R.drawable.storyb,1,R.drawable.profileb))
        array.add(StoryModel("Mamta",R.drawable.storyc,1,R.drawable.profilea))
        array.add(StoryModel("Priyanshu",R.drawable.storyd,1,R.drawable.profilea))
        adapter= StoryAdapter(view.context,array)
        var layoutrv=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        recyclerView.layoutManager=layoutrv
        recyclerView.isNestedScrollingEnabled=false
        recyclerView.adapter=adapter


    }
}