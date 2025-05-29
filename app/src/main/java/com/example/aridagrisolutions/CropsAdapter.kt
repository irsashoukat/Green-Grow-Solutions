package com.example.aridagrisolutions
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class CropsAdapter(private var cropList: ArrayList<CropMC>)
    : RecyclerView.Adapter<CropsAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image:ImageView=itemView.findViewById(R.id.cropImg)
        var title:TextView=itemView.findViewById(R.id.edCropTitle)
        var description:TextView=itemView.findViewById(R.id.edCropDescription)
        var cultivation:TextView=itemView.findViewById(R.id.edCropCultivation)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.crop_item_list, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val contact: CropMC = cropList[position]
        holder.title.text = contact.cropTitle
        holder.description.text = contact.cropDescription
        Picasso.get().load(contact.cropImage).into(holder.image)
        holder.itemView.setOnClickListener{
            var intent=Intent(holder.title.context,View_item::class.java)
            intent.putExtra("tit", contact.cropTitle)
            intent.putExtra("des", contact.cropDescription)
            intent.putExtra("temp", contact.temperature)
            intent.putExtra("soil", contact.soil)
            intent.putExtra("variety", contact.variety)
            intent.putExtra("sowing", contact.sowing)
            intent.putExtra("seedRate", contact.seedRate)
            intent.putExtra("fertilizer", contact.fertilizer)
            intent.putExtra("irrigation", contact.irrigation)
            intent.putExtra("harvesting", contact.harvesting)
            intent.putExtra("img", contact.cropImage)
            intent.putExtra("audioLink", contact.audioLink)
            holder.title.context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return cropList.size
    }
}