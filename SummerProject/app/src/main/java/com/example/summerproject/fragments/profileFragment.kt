package com.example.summerproject.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.summerproject.MainActivity
import com.example.summerproject.MakeBoardActivity
import com.example.summerproject.User
import com.example.summerproject.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_set_profile.*
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*
import org.jetbrains.anko.startActivity

class profileFragment : Fragment(){
    private var mBinding : FragmentProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)

        mBinding = binding

        return mBinding?.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {

        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                val user = p0.getValue(User::class.java)
                profile_name.setText(user?.username)

                Picasso.get().load(user?.profileImageUrl).into(profile_img)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })





        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}