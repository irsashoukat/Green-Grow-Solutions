package com.example.aridagrisolutions

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class CropsAdapterUrdu (private var cropList: ArrayList<CropMC>)
    : RecyclerView.Adapter<CropsAdapterUrdu.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView =itemView.findViewById(R.id.cropImgUrdu)
        var title: TextView =itemView.findViewById(R.id.edCropTitleUrdu)
        var description: TextView =itemView.findViewById(R.id.edCropDescriptionUrdu)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.crop_item_list_urdu, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val contact: CropMC = cropList[position]
        holder.title.text = contact.cropTitle
        holder.description.text = contact.cropDescription
        Picasso.get().load(contact.cropImage).into(holder.image)
        holder.itemView.setOnClickListener{
            var intent= Intent(holder.title.context,ViewItemUrdu::class.java)
            intent.putExtra("titUrdu", contact.cropTitle)
            intent.putExtra("desUrdu", contact.cropDescription)
            intent.putExtra("tempUrdu", contact.temperature)
            intent.putExtra("soilUrdu", contact.soil)
            intent.putExtra("varietyUrdu", contact.variety)
            intent.putExtra("sowingUrdu", contact.sowing)
            intent.putExtra("seedRateUrdu", contact.seedRate)
            intent.putExtra("fertilizerUrdu", contact.fertilizer)
            intent.putExtra("irrigationUrdu", contact.irrigation)
            intent.putExtra("harvestingUrdu", contact.harvesting)
            intent.putExtra("imgUrdu", contact.cropImage)
            intent.putExtra("audioLinkUrdu", contact.audioLink)
            holder.title.context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return cropList.size
    }
}