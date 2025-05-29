package com.example.aridagrisolutions

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aridagrisolutions.databinding.ActivityAddCropUrduBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

@Suppress("DEPRECATION")
class AddCropUrduActivity : AppCompatActivity() {
    private var _binding: ActivityAddCropUrduBinding? = null
    lateinit var imageUri: Uri
    private val binding get() = _binding!!
    private val pickAudio = 1
    var audioUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddCropUrduBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "فصل کی معلومات درج کریں۔"
        binding.btnBrowseUrdu.setOnClickListener {
            selectImage()
        }
        binding.btnUploadCropUrdu.setOnClickListener {
            uploadImage()
        }
        binding.btnRetrieveCropUrdu.setOnClickListener {
            var intent = Intent(this, CropsUrdu::class.java)
            startActivity(intent)
        }
        binding.btnBrowseAudioUrdu.setOnClickListener{
            selectAudio()
        }
    }

    private fun uploadImage() {
        val title = binding.edTitleUrdu.text.toString()
        val description = binding.edDescriptionUrdu.text.toString()
        val temperature = binding.edTempUrdu.text.toString()
        val soil = binding.edSoilUrdu.text.toString()
        val variety = binding.edVarietyUrdu.text.toString()
        val sowing = binding.edSowingUrdu.text.toString()
        val seedRate = binding.edSeedRateUrdu.text.toString()
        val fertilizer = binding.edFertilizerUrdu.text.toString()
        val irrigation = binding.edIrrigationUrdu.text.toString()
        val harvesting = binding.edHarvestingUrdu.text.toString()
        if (title.isEmpty()) {
            binding.edTitleUrdu.error = ("فصل کا عنوان درج کریں..")
            binding.edTitleUrdu.requestFocus()
        } else if (description.isEmpty()) {
            binding.edDescriptionUrdu.error = ("فصل کی تفصیل درج کریں..")
            binding.edDescriptionUrdu.requestFocus()
        } else if (temperature.isEmpty()) {
            binding.edTempUrdu.error = ("فصل کا درجہ حرارت درج کریں..")
            binding.edTempUrdu.requestFocus()
        } else if (soil.isEmpty()) {
            binding.edSoilUrdu.error = ("فصل کی مٹی کے حالات درج کریں..")
            binding.edSoilUrdu.requestFocus()
        } else if (variety.isEmpty()) {
            binding.edVarietyUrdu.error = ("فصل کی اقسام درج کریں...")
            binding.edVarietyUrdu.requestFocus()
        } else if (sowing.isEmpty()) {
            binding.edSowingUrdu.error = ("فصل کی بوائی کی تفصیلات درج کریں...")
            binding.edSowingUrdu.requestFocus()
        } else if (seedRate.isEmpty()) {
            binding.edSeedRateUrdu.error = ("فصل کے بیج کی شرح درج کریں...")
            binding.edSeedRateUrdu.requestFocus()
        } else if (fertilizer.isEmpty()) {
            binding.edFertilizerUrdu.error = ("فصل کی کھاد کی تفصیلات درج کریں...")
            binding.edFertilizerUrdu.requestFocus()
        } else if (irrigation.isEmpty()) {
            binding.edIrrigationUrdu.error = ("فصل کی آبپاشی درج کریں ..")
            binding.edIrrigationUrdu.requestFocus()
        } else if (harvesting.isEmpty()) {
            binding.edHarvestingUrdu.error = ("فصل کی کٹائی کی تفصیلات درج کریں...")
            binding.edHarvestingUrdu.requestFocus()
        } else {
            //uploading...
            var progressDialog = ProgressDialog(this)
            progressDialog.setMessage("اپ لوڈ ہو رہا ہے...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            val storageReference = FirebaseStorage.getInstance().getReference("images/urdu/$title")
            val storageReference2 = FirebaseStorage.getInstance().getReference("audio/urdu/$title")
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
                            val dbRef = FirebaseDatabase.getInstance().reference.child("CropsInUrdu")
                            val recordId = dbRef.push().key.toString()

                            dbRef.child(recordId).setValue(cropDetails).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    //Toast.makeText(this, "Data saved Succcessfully", Toast.LENGTH_SHORT).show()
                                }
                                binding.uploadImageUrdu.setImageURI(null)
                                //Toast.makeText(this, "Successfully uploaded", Toast.LENGTH_LONG).show()
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
        startActivityForResult(Intent.createChooser(intent, "Select Audio"), pickAudio)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            binding.uploadImageUrdu.setImageURI(imageUri)
        }
        if (requestCode == pickAudio && resultCode == RESULT_OK) {
            // Audio is Picked in format of URI
            audioUri = data?.data!!
        }
    }
}