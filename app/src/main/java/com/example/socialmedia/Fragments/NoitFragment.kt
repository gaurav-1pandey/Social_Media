package com.example.socialmedia.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.Adapter.NotiFragRvAdapter
import com.example.socialmedia.Models.NotificationModel
import com.example.socialmedia.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [NoitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NoitFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_noit, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NoitFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NoitFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var notilist=ArrayList<NotificationModel>()
        notilist.add(NotificationModel(R.drawable.dp1,"","just now","<b>Gaurav</b> mentioned uou in a comment"))
        notilist.add(NotificationModel(R.drawable.dp2,"","just now","<b>Ravi</b> liked your photo"))
        notilist.add(NotificationModel(R.drawable.dp3,"","1 minute ago","<b>Shivam</b> mentioned uou in a comment"))
        notilist.add(NotificationModel(R.drawable.dp4,"","1 minute ago","<b>Shubhangi</b> mentioned uou in a comment"))
        notilist.add(NotificationModel(R.drawable.dp5,"","5 minute ago","<b>Pankaj</b> liked your photo"))
        notilist.add(NotificationModel(R.drawable.gaurav,"","3 hour ago","<b>puja</b> mentioned uou in a comment"))
        notilist.add(NotificationModel(R.drawable.shubhangi,"","3 hour ago","<b>aarti</b> mentioned uou in a comment"))
        notilist.add(NotificationModel(R.drawable.dp2,"","just now","<b>Gaurav</b> mentioned uou in a comment"))
        notilist.add(NotificationModel(R.drawable.dp1,"","just now","<b>Ravi</b> liked your photo"))
        notilist.add(NotificationModel(R.drawable.dp2,"","1 minute ago","<b>Shivam</b> mentioned uou in a comment"))
        notilist.add(NotificationModel(R.drawable.shubhangi,"","1 minute ago","<b>Shubhangi</b> mentioned uou in a comment"))
        notilist.add(NotificationModel(R.drawable.dp3,"","5 minute ago","<b>Pankaj</b> liked your photo"))
        notilist.add(NotificationModel(R.drawable.dp5,"","1 hour ago","<b>Akhil</b> accepted your friend requst"))
        notilist.add(NotificationModel(R.drawable.dp4,"","3 hour ago","<b>puja</b> mentioned uou in a comment"))
        notilist.add(NotificationModel(R.drawable.dp2,"","3 hour ago","<b>aarti</b> mentioned uou in a comment"))
        notilist.add(NotificationModel(R.drawable.dp1,"","just now","<b>Gaurav</b> mentioned uou in a comment"))
        notilist.add(NotificationModel(R.drawable.dp2,"","just now","<b>Ravi</b> liked your photo"))
        notilist.add(NotificationModel(R.drawable.dp5,"","1 minute ago","Shivam mentioned uou in a comment"))
        notilist.add(NotificationModel(R.drawable.shubhangi,"","1 minute ago","Shubhangi mentioned uou in a comment"))
        notilist.add(NotificationModel(R.drawable.dp3,"","5 minute ago","Pankaj liked your photo"))
        notilist.add(NotificationModel(R.drawable.dp4,"","1 hour ago","Akhil accepted your friend requst"))
        notilist.add(NotificationModel(R.drawable.gaurav,"","3 hour ago","puja mentioned uou in a comment"))
        notilist.add(NotificationModel(R.drawable.shubhangi,"","3 hour ago","aarti mentioned uou in a comment"))
        notilist.add(NotificationModel(R.drawable.dp1,"","just now","Gaurav mentioned uou in a comment"))
        notilist.add(NotificationModel(R.drawable.dp1,"","just now","Ravi liked your photo"))
        notilist.add(NotificationModel(R.drawable.dp4,"","1 minute ago","Shivam mentioned uou in a comment"))
        notilist.add(NotificationModel(R.drawable.dp3,"","1 minute ago","Shubhangi mentioned uou in a comment"))
        notilist.add(NotificationModel(R.drawable.dp5,"","5 minute ago","Pankaj liked your photo"))
        notilist.add(NotificationModel(R.drawable.dp2,"","1 hour ago","Akhil accepted your friend requst"))
        notilist.add(NotificationModel(R.drawable.gaurav,"","3 hour ago","puja mentioned uou in a comment"))
        notilist.add(NotificationModel(R.drawable.shubhangi,"","3 hour ago","aarti mentioned uou in a comment"))



        var notiRv=view.findViewById<RecyclerView>(R.id.noti_rv)
        notiRv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        var notiadapter=NotiFragRvAdapter(view.context,notilist)
        notiRv.adapter=notiadapter

    }
}