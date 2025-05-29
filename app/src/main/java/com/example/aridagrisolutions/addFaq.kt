package com.example.aridagrisolutions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aridagrisolutions.databinding.ActivityAddFaqBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class addFaq : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private var _binding: ActivityAddFaqBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        _binding = ActivityAddFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

        binding.save.setOnClickListener{
            saveFAQ()
        }
        binding.view.setOnClickListener{
            viewFAQ()
        }
    }

    private fun viewFAQ() {
        val intent= Intent(this, faqs::class.java)
        startActivity(intent)
    }

    private fun saveFAQ() {
        var question = binding.edQuestion.text.toString()
        var answer = binding.edAnswer.text.toString()
        //Toast.makeText(this, rating+" "+messageFeedback, Toast.LENGTH_LONG).show()
        if(question.isEmpty())
        {
            Toast.makeText(this, "Enter Question", Toast.LENGTH_SHORT).show()
            binding.edQuestion.requestFocus()
        }
        else if(answer.isEmpty())
        {
            Toast.makeText(this, "Enter Answer", Toast.LENGTH_SHORT).show()
            binding.edAnswer.requestFocus()
        }
        else{
            //Toast.makeText(this, "Uploading...", Toast.LENGTH_LONG).show()
            var FAQRecord = FaqMC(question,answer)
            val dbRef= FirebaseDatabase.getInstance().reference.child("FAQS")
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
