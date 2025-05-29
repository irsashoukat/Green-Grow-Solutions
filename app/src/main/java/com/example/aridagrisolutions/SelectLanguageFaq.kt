package com.example.aridagrisolutions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SelectLanguageFaq : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_language_faq)
        val btnFAQEnglish=findViewById<Button>(R.id.selectFAQLangEnglish)
        val btnFAQUrdu=findViewById<Button>(R.id.selectFAQLangUrdu)
        supportActionBar?.title = ""
        btnFAQEnglish.setOnClickListener{
            startActivity(Intent(this, faqs::class.java))
        }
        btnFAQUrdu.setOnClickListener{
            startActivity(Intent(this, FaqsUrdu::class.java))
        }
    }
}