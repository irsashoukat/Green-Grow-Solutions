package com.example.aridagrisolutions

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FertilizerAdapter(private var fertList: ArrayList<FertilizerMC>)
    : RecyclerView.Adapter<FertilizerAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView =itemView.findViewById(R.id.fertImg)
        var title: TextView =itemView.findViewById(R.id.edFertTitle)
        var description: TextView =itemView.findViewById(R.id.edFertDescription)
        var usage: TextView =itemView.findViewById(R.id.edFertUsage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_fert_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val contact: FertilizerMC = fertList[position]
        holder.title.text = contact.fertTitle
        holder.description.text = contact.fertDescription
        holder.usage.text = contact.fertUsage
        Picasso.get().load(contact.fertImage).into(holder.image)

        holder.itemView.setOnClickListener{
            var intent= Intent(holder.title.context, ViewFertilizer::class.java)
            intent.putExtra("tit", contact.fertTitle)
            intent.putExtra("des", contact.fertDescription)
            intent.putExtra("use", contact.fertUsage)
            intent.putExtra("img", contact.fertImage)
            holder.title.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return fertList.size
    }
}