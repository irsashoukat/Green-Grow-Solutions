package com.example.aridagrisolutions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class CropsUrdu : AppCompatActivity() {
    private lateinit var mDataBase: DatabaseReference
    lateinit var recyclerView: RecyclerView
    private lateinit var cropList:ArrayList<CropMC>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "فصلیں"
        setContentView(R.layout.activity_crops_urdu)
        recyclerView=findViewById(R.id.recyclerCropsUrdu)
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        cropList= arrayListOf<CropMC>()
        getData()
    }
    private fun getData() {
        mDataBase = FirebaseDatabase.getInstance().reference.child("CropsInUrdu")
        mDataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    //Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
                    for(itemSnapshot in snapshot.children){
                        var crop = itemSnapshot.getValue(CropMC::class.java)
                        cropList.add(crop!!)
                    }
                    recyclerView.adapter = CropsAdapterUrdu(cropList)
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
}
