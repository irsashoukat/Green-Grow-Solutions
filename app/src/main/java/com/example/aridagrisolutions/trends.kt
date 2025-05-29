package com.example.aridagrisolutions

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aridagrisolutions.databinding.ActivityTrendsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class trends : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private var _binding: ActivityTrendsBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTrendsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Trends"
        mAuth = FirebaseAuth.getInstance()
        binding.trend1.setOnClickListener{
            intent= Intent(Intent.ACTION_VIEW, Uri.parse("https://gro-intelligence.com/insights/gro-s-2022-watchlist-9-major-themes-for-agriculture-in-the-year-ahead"))
            startActivity(intent)
        }
        binding.trend2.setOnClickListener{
            intent= Intent(Intent.ACTION_VIEW, Uri.parse("https://www.upkeep.com/blog/agriculture-trends-2022"))
            startActivity(intent)
        }
    }

    override fun onStart()
    {
        super.onStart()
        //check if the user is signed in (not-null) and update UI accordingly
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser==null)
        {
            finish()
            startActivity(Intent(this, login::class.java))
        }

    }

    override fun onDestroy()
    {
        super.onDestroy()
        _binding=null
    }

}