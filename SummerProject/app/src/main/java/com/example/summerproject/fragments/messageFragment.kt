package com.example.summerproject.fragments

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.summerproject.*
import com.example.summerproject.databinding.FragmentMessageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_make_board.*
import kotlinx.android.synthetic.main.fragment_message.*

class messageFragment : Fragment(){

    private var mBinding : FragmentMessageBinding? = null
    lateinit var boardAdapter: BoardAdapter
    private val data:MutableList<BoardData> = mutableListOf()

    companion object{
        val msg : String = "로그"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMessageBinding.inflate(inflater, container, false)
        mBinding = binding
        boardAdapter = BoardAdapter()
        mBinding!!.rvBulletinBoard.adapter = boardAdapter
        init()
        return mBinding?.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        register_btn.setOnClickListener {
            activity?.let{
                val intent = Intent(context, MakeBoardActivity::class.java)
                onDestroyView()// fragment destroy 10.03
                startActivityForResult(intent, 0)
            }
        }
        super.onActivityCreated(savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }




    private fun init(){
        val db: FirebaseFirestore = Firebase.firestore
        val itemsCollectionRef = db.collection("MemoList") // Collection 이름
        //mBinding!!.rvBulletinBoard.adapter = boardAdapter

        itemsCollectionRef.document(FirebaseAuth.getInstance().uid.toString()) // document확인하기 위해서
            .collection("Memo").get().addOnSuccessListener {
                data.clear()
                data.apply{
                for(document in it){
                    add(BoardData(document.id,document["text"].toString()))
                }
                boardAdapter.datas = data
                boardAdapter.notifyDataSetChanged()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK){
            Log.d("msg","onActivityResult 실행")
            init()
        }
    }
    
//    private fun refresh(){
//        fragmentManager?.beginTransaction()?.detach(this)?.commitNow()
//        fragmentManager?.beginTransaction()?.attach(this)?.commitNow()
//    }
//    private fun refreshFragment(){
//        val adapter = BoardAdapter()
//        adapter.datas = data
//        mBinding!!.rvBulletinBoard.adapter = adapter
//        mBinding!!.rvBulletinBoard.layoutManager=LinearLayoutManager(this.context)
//    }
}