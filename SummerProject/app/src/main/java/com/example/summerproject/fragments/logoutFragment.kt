package com.example.summerproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.summerproject.databinding.FragmentHomeBinding
import com.example.summerproject.databinding.FragmentLogoutBinding
import com.example.summerproject.databinding.FragmentProfileBinding

class logoutFragment : Fragment(){
    private var mBinding : FragmentLogoutBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLogoutBinding.inflate(inflater, container, false)

        mBinding = binding

        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}