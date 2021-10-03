package com.example.summerproject.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.summerproject.BoardAdapter
import com.example.summerproject.BoardData
import com.example.summerproject.CheckInActivity
import com.example.summerproject.MakeBoardActivity
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
        var binding = FragmentMessageBinding.inflate(inflater, container, false)
        val db: FirebaseFirestore = Firebase.firestore
        val itemsCollectionRef = db.collection("MemoList") // Collection 이름
        mBinding = binding

        init()
//        with(data){
//            itemsCollectionRef.document(FirebaseAuth.getInstance().uid.toString()) // document확인하기 위해서
//                .collection("Memo").get().addOnSuccessListener {
//                    for(document in it){
//                        add(BoardData(document.id,document["text"].toString()))
//                    }
//                }
//        }
        //refresh()

        return mBinding?.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        register_btn.setOnClickListener {
            activity?.let{
                val intent = Intent(context, MakeBoardActivity::class.java)
                onDestroyView()// fragment destroy 10.03
                startActivity(intent)
            }
        }
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    private fun init(){
        val db: FirebaseFirestore = Firebase.firestore
        val itemsCollectionRef = db.collection("MemoList") // Collection 이름
        boardAdapter = BoardAdapter()
        mBinding!!.rvBulletinBoard.adapter = boardAdapter

        itemsCollectionRef.document(FirebaseAuth.getInstance().uid.toString()) // document확인하기 위해서
            .collection("Memo").get().addOnSuccessListener {
            data.apply{
                for(document in it){
                    add(BoardData(document.id,document["text"].toString()))
                }
                boardAdapter.datas = data
                boardAdapter.notifyDataSetChanged()
            }
        }
    }
    fun refresh(){
        val adapter = BoardAdapter()
        adapter.datas = data
        mBinding!!.rvBulletinBoard.adapter = adapter
        mBinding!!.rvBulletinBoard.layoutManager=LinearLayoutManager(this.context)
    }
}