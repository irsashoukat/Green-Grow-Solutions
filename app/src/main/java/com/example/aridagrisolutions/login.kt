package com.example.aridagrisolutions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aridagrisolutions.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class login : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private var _binding: ActivityLoginBinding?=null
    private val binding get() = _binding!!
    lateinit var mDataBase:DatabaseReference
    var expertIdentity=10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

        binding.loginbtn.setOnClickListener{
            var email=binding.emailAddress.text.toString()
            var pass=binding.password.text.toString()

            if(email.isEmpty())
            {
                binding.emailAddress.error = ("Email Required")
                binding.emailAddress.requestFocus()
            }
            else if(pass.isEmpty())
            {
                binding.password.error = ("Password Required")
                binding.password.requestFocus()
            }
            else {
                //login
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (email == "rahatadmin2820@gmail.com") {
                            Toast.makeText(this, "Admin Login Succcessfully", Toast.LENGTH_SHORT).show()
                            var intent = Intent(this, AdminDashboard::class.java)
                            startActivity(intent)
                        } else {
                            val uid = mAuth.currentUser!!.uid
                            mDataBase = FirebaseDatabase.getInstance().getReference("Expert")
                            mDataBase.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists()) {
                                        Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG)
                                                .show()
                                        for (itemSnapshot in snapshot.children) {
                                            var currentExpert =
                                                    itemSnapshot.getValue(ExpertMC::class.java)
                                            if (currentExpert?.authId == uid) {
                                                expertIdentity=1
                                                var intent = Intent(this@login, ShowUserMessageActivity::class.java)
                                                startActivity(intent)
                                            }
                                        }

                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    Toast.makeText(
                                            applicationContext,
                                            error.message, Toast.LENGTH_SHORT
                                    ).show()
                                }

                            })
                            var intent = Intent(this@login, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    else
                    {
                        Toast.makeText(this, "User login Failed due to ${it.exception}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        binding.registerbtn.setOnClickListener{
            val intent=Intent(this, signUp::class.java)
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

        //if(currentUser?.uid=="mnI2lySHBjgRX9XL6DWxG4z2d5C2"){
        //    startActivity(Intent(this, AdminDashboard::class.java))
        //}

        if(mAuth.currentUser?.email =="rahatadmin2820@gmail.com"){
            finish()
            startActivity(Intent(this, AdminDashboard::class.java))
        }
        if(expertIdentity==1)
        {
            finish()
            startActivity(Intent(this, ShowUserMessageActivity::class.java))
        }
        if(currentUser!=null)
        {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onDestroy()
    {
        super.onDestroy()
        _binding=null
    }
}