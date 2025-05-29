package com.example.aridagrisolutions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import husaynhakeem.com.aboutpage.AboutPage
import husaynhakeem.com.aboutpage.Item

class about : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "About"
        setContentView(AboutPage(this)
                .setBackground(android.R.color.white)
                .setDescription("Green-Grow solution App aims to provide farmers important agriculture related information in local language(s) and support interaction with the experts and other farmers using modern digital age communication technologies. Green-Grow solution App seamlessly integrates digitized video, audio, text and the software applications developed to serve the needs of rural population.")
                .setImage(R.drawable.logo)
                .addItem(Item("Green Grow Version 1.0"))
                .addEmail("rahatimam666@gmail.com")
                .addFacebook("AridAgri")
                .addGithub("rahatimam")
                .addInstagram("GreenGrow")
                .addPlayStore("com.maketrumptweetseightagain")
                .addYoutube("UCyWqModMQlbIo8274Wh_ZsQ")
                .create())
    }
}