package com.example.aridagrisolutions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FeedbackAdapter  (private var feedbackList: ArrayList<FeedbackMC>)
    : RecyclerView.Adapter<FeedbackAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fbRating: TextView = itemView.findViewById(R.id.feedbackRating)
        var fbMessage: TextView = itemView.findViewById(R.id.feedbackMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.feedback_item_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentItem=feedbackList[position]
        holder.fbRating.text=currentItem.rating
        holder.fbMessage.text=currentItem.message

    }

    override fun getItemCount(): Int {
        return feedbackList.size
    }


}