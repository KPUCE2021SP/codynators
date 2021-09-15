package com.example.summerproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.summerproject.databinding.FragmentLogoutBinding
import com.google.firebase.auth.FirebaseAuth

class logoutFragment : Fragment(){
    private var mBinding : FragmentLogoutBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLogoutBinding.inflate(inflater, container, false)

        mBinding = binding

        FirebaseAuth.getInstance().signOut()
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.remove(this)
            ?.commit()

        activity?.supportFragmentManager
            ?.popBackStack()

        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}