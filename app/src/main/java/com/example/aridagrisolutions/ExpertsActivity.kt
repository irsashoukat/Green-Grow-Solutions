package com.example.aridagrisolutions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aridagrisolutions.databinding.ActivityExpertsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ExpertsActivity : AppCompatActivity() {

        private lateinit var mDataBase: DatabaseReference
        lateinit var recyclerView: RecyclerView
        private lateinit var expertList: ArrayList<ExpertMC>
        lateinit var toggle: ActionBarDrawerToggle
        private lateinit var mAuth: FirebaseAuth
        private var _binding: ActivityExpertsBinding? = null
        private val binding get() = _binding!!
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            _binding = ActivityExpertsBinding.inflate(layoutInflater)
            setContentView(binding.root)
            mAuth = FirebaseAuth.getInstance()
            supportActionBar?.setTitle("") // set the top title
            recyclerView = binding.recyclerExperts
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true)
            expertList = arrayListOf<ExpertMC>()
            getData()
        }

        private fun getData() {
            val uid = mAuth.currentUser!!.uid
            mDataBase = FirebaseDatabase.getInstance().getReference("Expert")
            mDataBase.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    expertList.clear()

                    if (snapshot.exists()) {
                        Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
                        for (itemSnapshot in snapshot.children) {
                            var currentExpert = itemSnapshot.getValue(ExpertMC::class.java)
                            if (mAuth.currentUser?.uid != currentExpert?.authId) {
                                expertList.add(currentExpert!!)
                            }

                        }
                        recyclerView.adapter = ExpertsAdapter(expertList)

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        applicationContext,
                        error.message, Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }

        override fun onStart() {
            super.onStart()
            //check if the user is signed in (not-null) and update UI accordingly
            val currentUser = mAuth.currentUser
            updateUI(currentUser)
        }

        private fun updateUI(currentUser: FirebaseUser?) {
            if (currentUser == null) {
                finish()
                startActivity(Intent(this, login::class.java))
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            _binding = null
        }
    }


