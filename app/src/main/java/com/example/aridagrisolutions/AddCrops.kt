package com.example.aridagrisolutions

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aridagrisolutions.databinding.ActivityAddCropsBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

@Suppress("DEPRECATION")
class AddCrops : AppCompatActivity() {
    private var _binding: ActivityAddCropsBinding? = null
    lateinit var imageUri: Uri
    private val binding get() = _binding!!
    private val pickAudio = 1
    var audioUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_crops)
        supportActionBar?.title = "Add Crops"
        _binding = ActivityAddCropsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnbrowse.setOnClickListener {
            selectImage()
        }
        binding.btnupload.setOnClickListener {
            uploadImage()
        }
        binding.btnRetrieve.setOnClickListener {
            var intent = Intent(this, crops::class.java)
            startActivity(intent)
        }
        binding.btnBrowseAudio.setOnClickListener{
            selectAudio()
        }
    }

    private fun uploadImage() {
        val title = binding.edtitle.text.toString()
        val description = binding.eddescription.text.toString()
        val temperature = binding.edtemp.text.toString()
        val soil = binding.edsoil.text.toString()
        val variety = binding.edvariety.text.toString()
        val sowing = binding.edsowing.text.toString()
        val seedRate = binding.edseedrate.text.toString()
        val fertilizer = binding.edfertilizer.text.toString()
        val irrigation = binding.edirrigation.text.toString()
        val harvesting = binding.edharvesting.text.toString()
        if (title.isEmpty()) {
            binding.edtitle.error = ("Title Required")
            binding.edtitle.requestFocus()
        } else if (description.isEmpty()) {
            binding.eddescription.error = ("Description Required")
            binding.eddescription.requestFocus()
        } else if (temperature.isEmpty()) {
            binding.edtemp.error = ("Temperature Required")
            binding.edtemp.requestFocus()
        } else if (soil.isEmpty()) {
            binding.edsoil.error = ("Soil details Required")
            binding.edsoil.requestFocus()
        } else if (variety.isEmpty()) {
            binding.edvariety.error = ("Crop variety Required")
            binding.edvariety.requestFocus()
        } else if (sowing.isEmpty()) {
            binding.edsowing.error = ("Sowing Required")
            binding.edsowing.requestFocus()
        } else if (seedRate.isEmpty()) {
            binding.edseedrate.error = ("Seed rate Required")
            binding.edseedrate.requestFocus()
        } else if (fertilizer.isEmpty()) {
            binding.edfertilizer.error = ("Fertilizer Required")
            binding.edfertilizer.requestFocus()
        } else if (irrigation.isEmpty()) {
            binding.edirrigation.error = ("Irrigation details Required")
            binding.edirrigation.requestFocus()
        } else if (harvesting.isEmpty()) {
            binding.edharvesting.error = ("Harvesting Required")
            binding.edharvesting.requestFocus()
        } else {
            //uploading...
            var progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Uploading data...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            val storageReference = FirebaseStorage.getInstance().getReference("images/$title")
            val storageReference2 = FirebaseStorage.getInstance().getReference("audio/eng/$title")
            storageReference.putFile(imageUri).addOnSuccessListener {
                val result = it.metadata!!.reference!!.downloadUrl
                result.addOnSuccessListener {
                    var imageLink = it.toString()

                    storageReference2.putFile(audioUri!!).addOnSuccessListener {
                        val audioResult = it.metadata!!.reference!!.downloadUrl
                        audioResult.addOnSuccessListener {

                            var audioLink = it.toString()

                            val cropDetails = CropMC(title, description, imageLink,
                                    temperature, soil, variety, sowing, seedRate, fertilizer, irrigation, harvesting, audioLink)
                            val dbRef = FirebaseDatabase.getInstance().reference.child("CROPS")
                            val recordId = dbRef.push().key.toString()

                            dbRef.child(recordId).setValue(cropDetails).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    //Toast.makeText(this, "Data saved Successfully", Toast.LENGTH_SHORT).show()
                                }
                                binding.uploadImage.setImageURI(null)
                                //Toast.makeText(this, "Successfully uploaded", Toast.LENGTH_LONG).show()
                                if (progressDialog.isShowing) {
                                    progressDialog.dismiss()
                                }
                            }.addOnFailureListener {
                                if (progressDialog.isShowing) {
                                    progressDialog.dismiss()
                                    //Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show()
                                }
                            }
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
    private fun selectAudio() {
        val intent = Intent()
        intent.type = "audio/*"
        intent.action = Intent.ACTION_OPEN_DOCUMENT
        startActivityForResult(Intent.createChooser(intent, "Select Audio"), pickAudio);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            binding.uploadImage.setImageURI(imageUri)
        }
        if (requestCode == pickAudio && resultCode == RESULT_OK) {
            // Audio is Picked in format of URI
            audioUri = data?.data!!
        }
    }
}