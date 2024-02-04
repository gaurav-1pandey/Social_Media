package com.example.socialmedia.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.Adapter.ProfileFriendsAdapted
import com.example.socialmedia.Adapter.resizeDrawable
import com.example.socialmedia.Models.FriendsModel
import com.example.socialmedia.R
//import com.flaviofaria.kenburnsview.KenBurnsView
//import com.flaviofaria.kenburnsview.Transition
import com.flaviofaria.kenburnsview.KenBurnsView
import de.hdodenhof.circleimageview.CircleImageView
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class ProfileFrag : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    lateinit var friendsAdapter:ProfileFriendsAdapted
    lateinit var rvfriend:RecyclerView
    lateinit var arrayfriend:ArrayList<FriendsModel>
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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val kenBurnsView: KenBurnsView = view.findViewById(R.id.img_anim)
        kenBurnsView.setImageBitmap(resizeDrawable(view.context,R.drawable.profiled,200,200))
        kenBurnsView.resume()
        arrayfriend=ArrayList()
        arrayfriend.add(FriendsModel(R.drawable.profilea))
        arrayfriend.add(FriendsModel(R.drawable.profileb))
        arrayfriend.add(FriendsModel(R.drawable.profilec))
        arrayfriend.add(FriendsModel(R.drawable.profiled))
        arrayfriend.add(FriendsModel(R.drawable.profilea))
        arrayfriend.add(FriendsModel(R.drawable.profileb))
        arrayfriend.add(FriendsModel(R.drawable.profilec))
        arrayfriend.add(FriendsModel(R.drawable.profiled))
        rvfriend=view.findViewById(R.id.profile_friend_rv)
        friendsAdapter= ProfileFriendsAdapted(view.context,arrayfriend)
        var pf=view.findViewById<CircleImageView>(R.id.noti_profile_rv_img)
        rvfriend.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        rvfriend.adapter=friendsAdapter

    }

}