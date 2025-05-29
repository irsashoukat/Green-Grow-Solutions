package com.example.aridagrisolutions

import android.content.Intent
import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.squareup.picasso.Picasso
import java.io.IOException


class View_item : AppCompatActivity() {

    private lateinit var audioLink: String
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var btnPlayAudio: ImageView
    private lateinit var btnPauseAudio: ImageView

    /*lateinit var engUrduTranslator:com.google.mlkit.nl.translate.Translator
    lateinit var pDialog: SweetAlertDialog
    var originalTitle=""
    var originalDescription=""
    var originalTemp=""
    var originalSoil=""
    var originalVariety=""
    var originalSowing=""
    var originalSeedRate=""
    var originalFertilizer=""
    var originalIrrigation=""
    var originalHarvesting=""

     */
    lateinit var edTitle: TextView
    lateinit var edDes: TextView
    lateinit var edTemp: TextView
    lateinit var edSoil: TextView
    lateinit var edVariety: TextView
    lateinit var edSowing: TextView
    lateinit var edSeedRate: TextView
    lateinit var edFertilizer: TextView
    lateinit var edIrrigation: TextView
    lateinit var edHarvesting: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_item)
        edTitle = findViewById(R.id.titl)
        edDes = findViewById(R.id.desc)
        edTemp = findViewById(R.id.temp)
        edSoil = findViewById(R.id.soil)
        edVariety = findViewById(R.id.variety)
        edSowing = findViewById(R.id.sowing)
        edSeedRate = findViewById(R.id.seedrate)
        edFertilizer = findViewById(R.id.fertilizer)
        edIrrigation = findViewById(R.id.irrigation)
        edHarvesting = findViewById(R.id.harvesting)

        /*
        var headingDescription=findViewById<TextView>(R.id.headingDescription)
        var headingCultivation=findViewById<TextView>(R.id.headingCultivation)
        var headingTemperature=findViewById<TextView>(R.id.headingTemperature)
        var headingSoilConditions=findViewById<TextView>(R.id.headingSoilConditions)
        var headingVariety=findViewById<TextView>(R.id.headingVariety)
        var headingSowing=findViewById<TextView>(R.id.headingSowing)
        var headingSeedRate=findViewById<TextView>(R.id.headingSeedRate)
        var headingFertilizer=findViewById<TextView>(R.id.headingFertilizer)
        var headingIrrigation=findViewById<TextView>(R.id.headingIrrigation)
        var headingHarvesting=findViewById<TextView>(R.id.headingHarvesting)
         */
        val getImg = findViewById<ImageView>(R.id.getImg)
        btnPlayAudio = findViewById(R.id.btnPlay)
        btnPauseAudio = findViewById(R.id.btnPause)
        val btnTranslateUrdu=findViewById<Button>(R.id.btnTranslateUrdu)
        btnTranslateUrdu.setOnClickListener{
            if(mediaPlayer.isPlaying){
                mediaPlayer!!.stop()
                mediaPlayer!!.reset()
                mediaPlayer!!.release()
            }
            startActivity(Intent(this, CropsUrdu::class.java))
        }
        /**get Data*/
        val intent = intent
        val title: String? = intent.getStringExtra("tit")
        val description = intent.getStringExtra("des")
        val temperature = intent.getStringExtra("temp")
        val soil = intent.getStringExtra("soil")
        val variety = intent.getStringExtra("variety")
        val sowing = intent.getStringExtra("sowing")
        val seedRate = intent.getStringExtra("seedRate")
        val fertilizer = intent.getStringExtra("fertilizer")
        val irrigation = intent.getStringExtra("irrigation")
        val harvesting = intent.getStringExtra("harvesting")
        audioLink = intent.getStringExtra("audioLink").toString()
        val image = intent.getStringExtra("img")
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
        /*
        btnTranslateUrdu.setOnClickListener{
            setUpProgressDialog()
            if (title != null) { originalTitle=title }
            if (description != null) { originalDescription=description }
            if (temperature != null) { originalTemp=temperature }
            if (soil != null) { originalSoil=soil }
            if (variety != null) { originalVariety=variety }
            if (sowing != null) { originalSowing=sowing }
            if (seedRate != null) { originalSeedRate=seedRate }
            if (fertilizer != null) { originalFertilizer=fertilizer }
            if (irrigation != null) { originalIrrigation=irrigation }
            if (harvesting != null) { originalHarvesting=harvesting }
            headingDescription.text="تفصیلات"
            headingCultivation.text="کاشت"
            headingTemperature.text="درجہ حرارت"
            headingSoilConditions.text="مٹی کی خصوصیات"
            headingVariety.text="مختلف اقسام"
            headingSowing.text="بیج بونے کا عمل"
            headingSeedRate.text="بیج کی شرح"
            headingFertilizer.text="کھاد"
            headingIrrigation.text="آبپاشی"
            headingHarvesting.text="فصل کی کٹائی"
            prepareTranslateModel()

         */

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

    /*
    private fun setUpProgressDialog() {
        pDialog= SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor= Color.parseColor("#A5DC86")
        pDialog.titleText="Loading"
        pDialog.setCancelable(false)
        pDialog.show()
    }

    private fun prepareTranslateModel() {
        val options: TranslatorOptions = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.URDU)
                .build()
        engUrduTranslator= Translation.getClient(options)
        pDialog.titleText="Model Downloading..."
        pDialog.show()
        engUrduTranslator.downloadModelIfNeeded().addOnSuccessListener {
            pDialog.dismissWithAnimation()
            translateText()
        }.addOnFailureListener{
        }
    }
    private fun translateText() {
        pDialog.titleText="Translate Text"
        pDialog.show()
        engUrduTranslator.translate(originalTitle).addOnSuccessListener { edTitle.text=it }
        engUrduTranslator.translate(originalDescription).addOnSuccessListener { edDes.text=it }
        engUrduTranslator.translate(originalTemp).addOnSuccessListener { edTemp.text=it }
        engUrduTranslator.translate(originalSoil).addOnSuccessListener { edSoil.text=it }
        engUrduTranslator.translate(originalVariety).addOnSuccessListener { edVariety.text=it }
        engUrduTranslator.translate(originalSowing).addOnSuccessListener { edSowing.text=it }
        engUrduTranslator.translate(originalSeedRate).addOnSuccessListener { edSeedRate.text=it }
        engUrduTranslator.translate(originalFertilizer).addOnSuccessListener { edFertilizer.text=it }
        engUrduTranslator.translate(originalIrrigation).addOnSuccessListener { edIrrigation.text=it }
        engUrduTranslator.translate(originalHarvesting).addOnSuccessListener { edHarvesting.text=it }
    }

     */
}

