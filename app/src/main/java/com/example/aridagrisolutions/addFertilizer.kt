package com.example.aridagrisolutions

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aridagrisolutions.databinding.ActivityAddFertilizerBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class addFertilizer : AppCompatActivity() {
    private var _binding: ActivityAddFertilizerBinding? = null
    lateinit var imageUri: Uri
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        _binding = ActivityAddFertilizerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.fertilizerBrowse.setOnClickListener {
            selectImage()
        }
        binding.fertilizerUpload.setOnClickListener {
            uploadImage()
        }
        binding.fertilizerRetrieve.setOnClickListener {
            var intent = Intent(this, fertilizers::class.java)
            startActivity(intent)
        }
    }
    private fun uploadImage() {
        val title = binding.fertilizerTitle.text.toString()
        val description = binding.fertilizerDescription.text.toString()
        val usage = binding.fertilizerUsage.text.toString()
        if (title.isEmpty()) {
            binding.fertilizerTitle.error = ("Title Required")
            binding.fertilizerTitle.requestFocus()
        }
        else if (description.isEmpty()) {
            binding.fertilizerDescription.error = ("Description Required")
            binding.fertilizerDescription.requestFocus()
        }
        else if (usage.isEmpty()) {
            binding.fertilizerUsage.error = ("Usage Required")
            binding.fertilizerUsage.requestFocus()
        }
        else
        {
            //uploading...
            var progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Uploading data...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            val fileName = title
            val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

            storageReference.putFile(imageUri).addOnSuccessListener {
                val result= it.metadata!!.reference!!.downloadUrl

                result.addOnSuccessListener { it ->
                    var imageLink = it.toString()
                    val fertDetails = FertilizerMC(title, description, usage, imageLink)
                    val dbRef = FirebaseDatabase.getInstance().reference.child("FERTILIZER")
                    val recordId = dbRef.push().key.toString()

                    dbRef.child(recordId).setValue(fertDetails).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Data saved Succcessfully", Toast.LENGTH_SHORT).show()
                        }
                        binding.fertilizerImage.setImageURI(null)
                        Toast.makeText(this, "Successfully uploaded", Toast.LENGTH_LONG).show()
                        if (progressDialog.isShowing) {
                            progressDialog.dismiss()
                        }
                    }.addOnFailureListener {
                        if (progressDialog.isShowing) {
                            progressDialog.dismiss()
                            Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show()
                        }
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
            binding.fertilizerImage.setImageURI(imageUri)
        }
    }
}

