package com.example.aridagrisolutions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class faqs : AppCompatActivity() {
    private lateinit var mDataBase: DatabaseReference
    lateinit var recyclerView: RecyclerView
    private lateinit var FaqList:ArrayList<FaqMC>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faqs)
        supportActionBar?.title = ""
        recyclerView=findViewById(R.id.recyclerFaq)
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        FaqList= arrayListOf<FaqMC>()
        getData()
    }

    private fun getData() {
        mDataBase = FirebaseDatabase.getInstance().reference.child("FAQS")
        mDataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
                    for(itemSnapshot in snapshot.children){
                        var faq = itemSnapshot.getValue(FaqMC::class.java)
                        FaqList.add(faq!!)
                    }
                    recyclerView.adapter = FaqAdapter(FaqList)
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


