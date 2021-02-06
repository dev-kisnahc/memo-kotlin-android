package com.kisnahc.memo_kotlin_android

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView


class MyAdapter(var context: Context,
                var list: List<MemoEntity>,
                var onDeleteListener: OnDeleteListener) : RecyclerView.Adapter<MyAdapter.MyViewHolder> () {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {


        val itemView = LayoutInflater.from(context).inflate(R.layout.item_memo,parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //list = 1, 2, 3,
        val memo = list[position]

        holder.memo.text = memo.memo
        holder.root1.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {

                onDeleteListener.onDeleteListener(memo)

                return true
            }
        })

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var tv_memo : TextView = itemView.findViewById(R.id.tv_memo)
        var root : ConstraintLayout = itemView.findViewById(R.id.root)

        val memo = tv_memo
        val root1 = root
    }


}