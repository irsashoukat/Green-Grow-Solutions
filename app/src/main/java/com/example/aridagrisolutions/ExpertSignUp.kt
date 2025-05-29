package com.example.aridagrisolutions
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aridagrisolutions.databinding.ActivityExpertSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
class ExpertSignUp : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private var _binding: ActivityExpertSignUpBinding? = null
    private val binding get() = _binding!!
    lateinit var imageUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        _binding = ActivityExpertSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        binding.btnbrowse.setOnClickListener {
            selectImage()
        }
        binding.register.setOnClickListener {
            var name = binding.name.text.toString()
            var email = binding.email.text.toString()
            var phno = binding.phone.text.toString()
            var pass = binding.password.text.toString()
            var repassword = binding.repassword.text.toString()

            if (name.isEmpty()) {
                binding.name.error = ("Name Required")
                binding.name.requestFocus()
            } else if (email.isEmpty()) {
                binding.email.error = ("Email Required")
                binding.email.requestFocus()
            } else if (phno.isEmpty()) {
                binding.phone.error = ("Contacts Required")
                binding.phone.requestFocus()
            } else if (pass.isEmpty() || repassword.isEmpty()) {
                binding.password.error = ("Password Required")
                binding.password.requestFocus()
            } else if (pass != repassword) {
                binding.password.error = ("Password Mismatch")
                binding.password.requestFocus()
                binding.repassword.requestFocus()
            } else {
                //register
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {

                        val fileName = binding.name.text
                        val storageReference =
                            FirebaseStorage.getInstance().getReference("images/$fileName")
                        storageReference.putFile(imageUri).addOnSuccessListener {
                            val result = it.metadata!!.reference!!.downloadUrl

                            result.addOnSuccessListener {
                                var imageLink = it.toString()


                                var expertDetails = ExpertMC()
                                expertDetails.name = name
                                expertDetails.email = email
                                expertDetails.phNo = phno
                                expertDetails.password = pass
                                expertDetails.imageLink = imageLink
                                expertDetails.authId = mAuth.currentUser!!.uid

                                val uid = mAuth.uid
                                val dbRef =
                                    FirebaseDatabase.getInstance().reference.child("Expert/$uid")
                                dbRef.setValue(expertDetails)
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
                                        } else {
                                            Toast.makeText(
                                                this,
                                                "Saving data Failed due to ${it.exception}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                Toast.makeText(
                                    this,
                                    "Expert Registered Succcessfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(Intent(this, LatestMessageActivity::class.java))
                            }.addOnFailureListener {

                                Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show()
                            }


                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Expert Register Failed due to ${it.exception}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            }
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            binding.imageView.setImageURI(imageUri)
        }
    }
}