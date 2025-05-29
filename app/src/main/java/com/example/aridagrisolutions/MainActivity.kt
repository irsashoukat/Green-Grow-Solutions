package com.example.aridagrisolutions

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.aridagrisolutions.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mAuth: FirebaseAuth
    private var _binding: ActivityMainBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        supportActionBar?.title = "" // set the top title
        //actionBar?.hide() // hide the actionbar
        binding.cvLanguage.setOnClickListener(this)
        binding.cvWeather.setOnClickListener(this)
        binding.cvCrops.setOnClickListener(this)
        binding.cvFertilizer.setOnClickListener(this)
        binding.cvFaqs.setOnClickListener(this)
        binding.cvQueries.setOnClickListener(this)
        binding.cvFeedback.setOnClickListener(this)
        binding.cvAbout.setOnClickListener(this)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerlayout)
        val navView: NavigationView = findViewById(R.id.navView)
        navView.bringToFront()
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuDashboard -> startActivity(Intent(this, AdminDashboard::class.java))
                R.id.menuAbout -> startActivity(Intent(this, about::class.java))
                R.id.menuCrops -> startActivity(Intent(this, SelectLanguageCrop::class.java))
                R.id.menuFaqs -> startActivity(Intent(this, SelectLanguageFaq::class.java))
                R.id.menuFertilizer -> startActivity(Intent(this, fertilizers::class.java))
                R.id.menuWeather -> startActivity(Intent(this, weather::class.java))
                R.id.menuLogout -> {
                    Toast.makeText(applicationContext, "Log out", Toast.LENGTH_LONG).show()
                    mAuth.signOut()
                    updateUI(mAuth.currentUser)
                    startActivity(Intent(this, login::class.java))
                }
            }
            true
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

    override fun onClick(p0: View?) {

        var intent:Intent?=null
       when(p0){
            binding.cvLanguage-> intent= Intent(this,trends::class.java)
            binding.cvWeather-> intent= Intent(this,weather::class.java)
            binding.cvCrops-> intent= Intent(this,SelectLanguageCrop::class.java)
            binding.cvFertilizer-> intent= Intent(this,fertilizers::class.java)
            binding.cvFaqs-> intent= Intent(this,SelectLanguageFaq::class.java)
            binding.cvQueries-> intent= Intent(this,Queries::class.java)
            binding.cvFeedback-> intent= Intent(this,feedback::class.java)
            binding.cvAbout-> intent= Intent(this,about::class.java)
       }
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item))
        {return true}
        return super.onOptionsItemSelected(item)
    }

}


