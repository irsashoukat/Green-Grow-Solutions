package com.example.aridagrisolutions

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ExpertsAdapter(private var expertList: ArrayList<ExpertMC>)
    : RecyclerView.Adapter<ExpertsAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var expertsName: TextView =itemView.findViewById(R.id.expertName)
        var expertImage: ImageView = itemView.findViewById(R.id.expertImg)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.expert_list, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val contact: ExpertMC = expertList[position]
        holder.expertsName.text= contact.name
        Picasso.get().load(contact.imageLink).into(holder.expertImage)
        holder.itemView.setOnClickListener{
            var intent= Intent(holder.expertsName.context, MessageActivity::class.java)
            intent.putExtra("name", contact.name)
            intent.putExtra("uid", contact.authId)
            intent.putExtra("img", contact.imageLink)
            holder.expertsName.context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return expertList.size
    }
}