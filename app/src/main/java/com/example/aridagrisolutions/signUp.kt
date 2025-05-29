package com.example.aridagrisolutions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aridagrisolutions.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class signUp : AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth
    private var _binding: ActivitySignUpBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

        binding.register.setOnClickListener{
            var name=binding.name.text.toString()
            var email=binding.email.text.toString()
            var phno=binding.phone.text.toString()
            var pass=binding.password.text.toString()
            var repassword=binding.repassword.text.toString()

            if(name.isEmpty())
            {
                binding.name.error = ("Name Required")
                binding.name.requestFocus()
            }
            else if(email.isEmpty())
            {
                binding.email.error = ("Email Required")
                binding.email.requestFocus()
            }
            else if(phno.isEmpty())
            {
                binding.phone.error = ("Contacts Required")
                binding.phone.requestFocus()
            }
            else if(pass.isEmpty() || repassword.isEmpty())
            {
                binding.password.error = ("Password Required")
                binding.password.requestFocus()
            }
            else if(pass!=repassword)
            {
                binding.password.error = ("Password Mismatch")
                binding.password.requestFocus()
                binding.repassword.requestFocus()
            }
            else
            {
                //register
                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                    if(it.isSuccessful)
                    {
                        var user = userMC()
                        user.name=name
                        user.email=email
                        user.phNo=phno
                        user.password=pass
                        user.authId=mAuth.currentUser!!.uid
                        val uid = mAuth.uid
                        val dbRef =
                            FirebaseDatabase.getInstance().reference.child("USERS/$uid")
                        dbRef.setValue(user)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Toast.makeText(
                                        this,
                                        "Data saved Succcessfully",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    startActivity(
                                        Intent(
                                            this,
                                            LatestMessageActivity::class.java
                                        )
                                    )
                                }
                            else
                            {
                                Toast.makeText(this, "Saving data Failed due to ${it.exception}", Toast.LENGTH_SHORT).show()
                            }
                        }
                        Toast.makeText(this, "User Registered Succcessfully", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                    else
                    {
                        Toast.makeText(this, "User Register Failed due to ${it.exception}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
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