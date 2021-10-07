package com.example.summerproject.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.summerproject.*
import com.example.summerproject.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_checkin.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.activity_check_out.*

class homeFragment : Fragment(){
    private var mBinding : FragmentHomeBinding? = null
    private val db: FirebaseFirestore = Firebase.firestore
    private val itemsCollectionRef = db.collection("Table_Use_Information") // Collection 이름
    val uId : String = FirebaseAuth.getInstance().uid.toString()
    var flag : TableData = TableData("", false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        mBinding = binding

        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        home_message.setOnClickListener {
            activity?.let{
                val intent = Intent(context, LatestMessagesActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        home_checkIn.setOnClickListener{
                activity?.let {
                    val intent = Intent(context, CheckInActivity::class.java)
                    startActivity(intent)
                }

        }
        home_place.setOnClickListener {
            activity?.let{
                val intent = Intent(context, PlaceListActivity::class.java)
                startActivity(intent)
            }
        }
        home_checkOut.setOnClickListener {
            activity?.let{
                val intent = Intent(context, CheckOutActivity::class.java)
                startActivity(intent)
            }
        }
        super.onActivityCreated(savedInstanceState)
    }

}