package com.example.aridagrisolutions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.aridagrisolutions.databinding.ActivityAdminDashboardBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AdminDashboard : AppCompatActivity(), View.OnClickListener {
    private lateinit var mAuth: FirebaseAuth
    private var _binding: ActivityAdminDashboardBinding?=null
    private val binding get() = _binding!!
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

        binding.adminCrops.setOnClickListener(this)
        binding.adminCropsUrdu.setOnClickListener(this)
        binding.adminFaqs.setOnClickListener(this)
        binding.adminFeedback.setOnClickListener(this)
        binding.adminFertilizer.setOnClickListener(this)
        binding.adminFaqsUrdu.setOnClickListener(this)
        binding.adminQueries.setOnClickListener(this)

        supportActionBar?.title = "" // set the top title
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerlayout)
        val navView : NavigationView = findViewById(R.id.navView)
        navView.bringToFront()
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menuDashboard-> startActivity(Intent(this, AdminDashboard::class.java))
                R.id.menuAbout->  startActivity(Intent(this, about::class.java))
                R.id.menuCrops->  startActivity(Intent(this, SelectLanguageCrop::class.java))
                R.id.menuFaqs->  startActivity(Intent(this, SelectLanguageFaq::class.java))
                R.id.menuFertilizer->  startActivity(Intent(this, fertilizers::class.java))
                R.id.menuWeather-> startActivity(Intent(this, weather::class.java))
                R.id.menuLogout->{
                    Toast.makeText(applicationContext,"Log out", Toast.LENGTH_LONG).show()
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
            binding.adminCrops-> intent= Intent(this,AddCrops::class.java)
            binding.adminFertilizer-> intent= Intent(this,addFertilizer::class.java)
            binding.adminFaqs-> intent= Intent(this,addFaq::class.java)
            binding.adminFeedback-> intent= Intent(this,viewFeedback::class.java)
            binding.adminCropsUrdu->intent= Intent(this,AddCropUrduActivity::class.java)
            binding.adminFaqsUrdu->intent= Intent(this,AddFaqUrdu::class.java)
            binding.adminQueries-> intent= Intent(this,ExpertSignUp::class.java)

        }
        startActivity(intent)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item))
        {return true}
        return super.onOptionsItemSelected(item)
    }
}


