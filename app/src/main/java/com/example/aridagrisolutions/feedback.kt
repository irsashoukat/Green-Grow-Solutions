package com.example.aridagrisolutions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aridagrisolutions.databinding.ActivityFeedbackBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class feedback : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private var _binding: ActivityFeedbackBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = ""
        _binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        binding.publishFeedback.setOnClickListener{
            publishFeedback()
        }
    }
    private fun publishFeedback() {
        var rating = binding.ratingBar.rating.toString()
        var messageFeedback = binding.feedbackText.text.toString()
        //Toast.makeText(this, rating+" "+messageFeedback, Toast.LENGTH_LONG).show()
        if(rating=="0.0")
        {
            Toast.makeText(this, "Please rate our app", Toast.LENGTH_LONG).show()
        }
        else if(messageFeedback.isEmpty())
        {
            Toast.makeText(this, "Please enter feedback", Toast.LENGTH_LONG).show()
            binding.feedbackText.requestFocus()
        }
        else
        {
            //save in database
            var feedbackRecord = FeedbackMC(rating,messageFeedback)
            val dbRef= FirebaseDatabase.getInstance().reference.child("FEEDBACK")
            val recordId=dbRef.push().key.toString()
            dbRef.child(recordId).setValue(feedbackRecord)
                .addOnCompleteListener{
                    if(it.isSuccessful)
                    {
                        Toast.makeText(this, "Successful", Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener{
                    Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show()
                }

        }
    }
}