package com.bannerview.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bannerview.R

class IndicatorAdapter(): RecyclerView.Adapter<IndicatorAdapter.IndicatorViewHolder>() {
    private var listItems: List<String> = arrayListOf()
    private var currentPosition = 0
    private var context: Context? = null

    fun setData(list: List<String>){
        listItems = list
        Log.e("AdapterData", list.size.toString())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicatorViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_indicator, parent, false)
        return IndicatorViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: IndicatorViewHolder, position: Int) {
        if (currentPosition == position){
            holder.dotViewChild.setImageDrawable(context?.resources?.getDrawable(R.drawable.selected_circle))
        }else {
            holder.dotViewChild.setImageDrawable(context?.resources?.getDrawable(R.drawable.not_selected_circle))
        }

    }

    class IndicatorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val dotView = itemView.findViewById<ImageView>(R.id.parent)
        val dotViewChild = itemView.findViewById<ImageView>(R.id.child)
    }

    fun setCurrentPosition(post: Int){
        currentPosition = post
        notifyDataSetChanged()
    }

}