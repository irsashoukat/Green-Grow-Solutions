package com.example.aridagrisolutions

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import java.io.IOException

@Suppress("DEPRECATION")
class ViewItemUrdu : AppCompatActivity() {
    private lateinit var audioLink: String
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var btnPlayAudio: ImageView
    private lateinit var btnPauseAudio: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_item_urdu)

        val btnTranslateEnglish=findViewById<Button>(R.id.btnTranslateEnglish)
        btnTranslateEnglish.setOnClickListener{
            if(mediaPlayer.isPlaying){
                mediaPlayer!!.stop()
                mediaPlayer!!.reset()
                mediaPlayer!!.release()
            }
            startActivity(Intent(this, crops::class.java))
        }

            var edTitle = findViewById<TextView>(R.id.titleUrdu)
            var edDes = findViewById<TextView>(R.id.descUrdu)
            var edTemp = findViewById<TextView>(R.id.tempUrdu)
            var edSoil = findViewById<TextView>(R.id.soilUrdu)
            var edVariety = findViewById<TextView>(R.id.varietyUrdu)
            var edSowing = findViewById<TextView>(R.id.sowingUrdu)
            var edSeedRate = findViewById<TextView>(R.id.seedRateUrdu)
            var edFertilizer = findViewById<TextView>(R.id.fertilizerUrdu)
            var edIrrigation = findViewById<TextView>(R.id.irrigationUrdu)
            var edHarvesting = findViewById<TextView>(R.id.harvestingUrdu)

        val getImg = findViewById<ImageView>(R.id.getImgUrdu)
        btnPlayAudio = findViewById(R.id.btnPlayUrdu)
        btnPauseAudio = findViewById(R.id.btnPauseUrdu)

        /**get Data*/
        val intent = intent
        val title: String? = intent.getStringExtra("titUrdu")
        val description = intent.getStringExtra("desUrdu")
        val temperature = intent.getStringExtra("tempUrdu")
        val soil = intent.getStringExtra("soilUrdu")
        val variety = intent.getStringExtra("varietyUrdu")
        val sowing = intent.getStringExtra("sowingUrdu")
        val seedRate = intent.getStringExtra("seedRateUrdu")
        val fertilizer = intent.getStringExtra("fertilizerUrdu")
        val irrigation = intent.getStringExtra("irrigationUrdu")
        val harvesting = intent.getStringExtra("harvestingUrdu")
        audioLink = intent.getStringExtra("audioLinkUrdu").toString()
        val image = intent.getStringExtra("imgUrdu")
        /**call text and images*/
        edTitle.text = title
        edDes.text = description
        edTemp.text = temperature
        edSoil.text = soil
        edVariety.text = variety
        edSowing.text = sowing
        edSeedRate.text = seedRate
        edFertilizer.text = fertilizer
        edIrrigation.text = irrigation
        edHarvesting.text = harvesting
        Picasso.get().load(image).into(getImg)
        supportActionBar?.title = title
        btnPlayAudio.setOnClickListener {
            playAudio()
        }
        btnPauseAudio.setOnClickListener {
            pauseAudio()
        }

    }
    private fun playAudio() {
        val audioUrl=audioLink
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try{
            mediaPlayer!!.setDataSource(audioUrl)
            mediaPlayer!!.prepare()
            mediaPlayer.start()
        }
        catch (e: IOException){
            e.printStackTrace()
        }
        Toast.makeText(this, "Audio started playing...", Toast.LENGTH_LONG).show()
    }
    private fun pauseAudio() {

        if(mediaPlayer==null){
            btnPauseAudio.isEnabled=false
        }
        else if(mediaPlayer!!.isPlaying){
            mediaPlayer!!.stop()
            mediaPlayer!!.reset()
            mediaPlayer!!.release()
        }
        else
        {
            Toast.makeText(this, "Audio has not played...", Toast.LENGTH_LONG).show()
        }
    }
    override fun onBackPressed() {
        if(mediaPlayer.isPlaying){
            mediaPlayer!!.stop()
            mediaPlayer!!.reset()
            mediaPlayer!!.release()
        }
        super.onBackPressed()
    }
}