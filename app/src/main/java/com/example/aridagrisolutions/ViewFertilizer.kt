package com.example.aridagrisolutions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class ViewFertilizer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_fertilizer)

        val edTitle=findViewById<TextView>(R.id.fertTitl)
        val edUsage=findViewById<TextView>(R.id.fertUsage)
        val edDes=findViewById<TextView>(R.id.fertDesc)
        val getImg=findViewById<ImageView>(R.id.getFertImg)
        /**get Data*/
        val intent = intent
        val title = intent.getStringExtra("tit")
        val description = intent.getStringExtra("des")
        val cultivation = intent.getStringExtra("use")
        val image = intent.getStringExtra("img")

        /**call text and images*/
        edTitle.text=title
        edUsage.text=cultivation
        edDes.text=description
        Picasso.get().load(image).into(getImg)

    }
}