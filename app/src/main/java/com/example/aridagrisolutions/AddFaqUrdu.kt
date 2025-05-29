package com.example.aridagrisolutions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aridagrisolutions.databinding.ActivityAddFaqBinding
import com.example.aridagrisolutions.databinding.ActivityAddFaqUrduBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class AddFaqUrdu : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private var _binding: ActivityAddFaqUrduBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        _binding = ActivityAddFaqUrduBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

        binding.uploadFaqUrdu.setOnClickListener{
            saveFAQ()
        }
        binding.displayFaqUrdu.setOnClickListener{
            viewFAQ()
        }
    }
    private fun viewFAQ() {
        val intent= Intent(this, FaqsUrdu::class.java)
        startActivity(intent)
    }
    private fun saveFAQ() {
        var question = binding.edQuestionUrdu.text.toString()
        var answer = binding.edAnswerUrdu.text.toString()
        //Toast.makeText(this, rating+" "+messageFeedback, Toast.LENGTH_LONG).show()
        if(question.isEmpty())
        {
            Toast.makeText(this, "سوال درج کریں...", Toast.LENGTH_SHORT).show()
            binding.edQuestionUrdu.requestFocus()
        }
        else if(answer.isEmpty())
        {
            Toast.makeText(this, "جواب درج کریں...", Toast.LENGTH_SHORT).show()
            binding.edAnswerUrdu.requestFocus()
        }
        else{
            //Toast.makeText(this, "Uploading...", Toast.LENGTH_LONG).show()

            var FAQRecord = FaqMC(question,answer)
            val dbRef= FirebaseDatabase.getInstance().reference.child("FAQSInUrdu")
            val recordId=dbRef.push().key.toString()
            dbRef.child(recordId).setValue(FAQRecord)
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

