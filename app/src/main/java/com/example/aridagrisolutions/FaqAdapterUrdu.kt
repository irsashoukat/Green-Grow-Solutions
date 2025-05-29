package com.example.aridagrisolutions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FaqAdapterUrdu (private var faqList: ArrayList<FaqMC>)
    : RecyclerView.Adapter<FaqAdapterUrdu.ViewHolder>(){
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var question: TextView = itemView.findViewById(R.id.questionUrdu)
        var answer: TextView = itemView.findViewById(R.id.answerUrdu)
        var layout: LinearLayout = itemView.findViewById(R.id.expandedLayoutUrdu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.faq_list_urdu, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentItem=faqList[position]
        holder.question.text=currentItem.question
        holder.answer.text=currentItem.answer
        val isVisible:Boolean = currentItem.visibile
        holder.layout.visibility = if(isVisible) View.VISIBLE else View.GONE


        holder.question.setOnClickListener{
            currentItem.visibile=!currentItem.visibile
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return faqList.size
    }


}