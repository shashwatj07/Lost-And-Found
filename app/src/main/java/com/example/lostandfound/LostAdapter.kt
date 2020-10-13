package com.example.lostandfound

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class LostAdapter(val context: Context, val lostItems: List<Item>) : RecyclerView.Adapter<LostAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("Range")
        fun setData (item: Item?, pos: Int) {
            itemView.lostTitle.text = item!!.itemName
            itemView.lostDesc.text = item!!.description
            itemView.lostBy.text = item!!.lostBy
            itemView.lostFoundDate.text = item!!.lostFoundDate
            itemView.tstamp.text = item!!.tstamp
            itemView.itemBox.setBackgroundColor(Color.parseColor(item!!.color))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lostItems.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemLost = lostItems[position]
        holder.setData(itemLost, position)
    }
}
//itemLost.color!!