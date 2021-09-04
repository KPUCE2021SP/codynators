package com.example.summerproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.summerproject.BoardAdapter
import com.example.summerproject.BoardData
import com.example.summerproject.databinding.FragmentMessageBinding
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
        mBinding = binding

        init()
        with(data){
            add(BoardData("안녕","방가","지금"))
            add(BoardData("아니","이제","아까"))
            add(BoardData("겨우","이걸 하네","아까"))
        }
        refresh()

        return mBinding?.root

    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    private fun init(){
        boardAdapter = BoardAdapter()
        mBinding!!.rvBulletinBoard.adapter = boardAdapter

        data.apply {
            add(BoardData("공지","태용 꼬 3","몇분 전"))
            add(BoardData("필돌","비방글 삭제","몇초 전"))

            boardAdapter.datas = data
            boardAdapter.notifyDataSetChanged()
        }
    }
    private fun refresh(){
        val adapter = BoardAdapter()
        adapter.datas = data
        mBinding!!.rvBulletinBoard.adapter = adapter
        mBinding!!.rvBulletinBoard.layoutManager=LinearLayoutManager(this.context)
    }

}