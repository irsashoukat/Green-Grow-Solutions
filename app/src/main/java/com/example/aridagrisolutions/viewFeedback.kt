package com.example.aridagrisolutions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class viewFeedback : AppCompatActivity() {
    private lateinit var mDataBase: DatabaseReference
    lateinit var recyclerViewFeedback: RecyclerView
    private lateinit var feedbackList:ArrayList<FeedbackMC>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_feedback)
        recyclerViewFeedback=findViewById(R.id.recyclerFeedback)
        recyclerViewFeedback.layoutManager= LinearLayoutManager(this)
        recyclerViewFeedback.setHasFixedSize(true)
        feedbackList= arrayListOf<FeedbackMC>()
        getData()
}
    private fun getData() {
        mDataBase = FirebaseDatabase.getInstance().reference.child("FEEDBACK")
        mDataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
                    for(itemSnapshot in snapshot.children){
                        var feedback = itemSnapshot.getValue(FeedbackMC::class.java)
                        feedbackList.add(feedback!!)
                    }
                    recyclerViewFeedback.adapter = FeedbackAdapter(feedbackList)
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