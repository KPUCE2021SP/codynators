package com.example.summerproject.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.summerproject.CheckInActivity
import com.example.summerproject.LatestMessagesActivity
import com.example.summerproject.PlaceListActivity
import com.example.summerproject.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

class homeFragment : Fragment(){
    private var mBinding : FragmentHomeBinding? = null


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
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        home_checkIn.setOnClickListener{
            activity?.let{
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
        super.onActivityCreated(savedInstanceState)
    }
}