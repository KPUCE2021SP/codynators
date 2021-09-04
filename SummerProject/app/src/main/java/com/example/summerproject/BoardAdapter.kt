package com.example.summerproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.summerproject.databinding.ItemRecyclerBinding

class BoardAdapter : RecyclerView.Adapter<BoardAdapter.Holder>(){

    var datas = mutableListOf<BoardData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val member = datas[position]
        holder.setData(member)
    }

    class Holder(val binding: ItemRecyclerBinding):RecyclerView.ViewHolder(binding.root){
        fun setData(item: BoardData){
            binding.itemTitle.text = item.title
            binding.itemContext.text = item.context
            binding.itemTimeline.text = item.timeline
        }
    }
}