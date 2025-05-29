package com.example.aridagrisolutions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SelectLanguageCrop : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_language_crop)
        val btnCropEnglish=findViewById<Button>(R.id.selectCropLangEnglish)
        val btnCropUrdu=findViewById<Button>(R.id.selectCropLangUrdu)
        supportActionBar?.title = ""
        btnCropEnglish.setOnClickListener{
            startActivity(Intent(this, crops::class.java))
        }
        btnCropUrdu.setOnClickListener{
            startActivity(Intent(this, CropsUrdu::class.java))
        }
    }
}