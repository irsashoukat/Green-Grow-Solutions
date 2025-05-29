package com.example.aridagrisolutions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class fertilizers : AppCompatActivity() {
    private lateinit var mDataBase: DatabaseReference
    lateinit var recyclerView: RecyclerView
    private lateinit var fertList:ArrayList<FertilizerMC>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fertilizers)
        supportActionBar?.title = ""
        recyclerView=findViewById(R.id.recyclerFertilizer)
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        fertList= arrayListOf<FertilizerMC>()
        getData()
    }
    private fun getData() {
        mDataBase = FirebaseDatabase.getInstance().reference.child("FERTILIZER")
        mDataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
                    for(itemSnapshot in snapshot.children){
                        var fert = itemSnapshot.getValue(FertilizerMC::class.java)
                        fertList.add(fert!!)
                    }
                    recyclerView.adapter = FertilizerAdapter(fertList)

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
