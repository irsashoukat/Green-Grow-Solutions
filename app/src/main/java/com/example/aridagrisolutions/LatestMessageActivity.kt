package com.example.aridagrisolutions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aridagrisolutions.databinding.ActivityLatestMessageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class LatestMessageActivity : AppCompatActivity() {
    private lateinit var mDataBase: DatabaseReference
    lateinit var recyclerView: RecyclerView
    private lateinit var messageList: ArrayList<MessageMC>
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mAuth: FirebaseAuth
    private var _binding: ActivityLatestMessageBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLatestMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = ""
        binding.btnNewMessage.setOnClickListener {
            var intent = Intent(this, ExpertsActivity::class.java)
            startActivity(intent)
        }

        recyclerView = binding.showRecentUserMessageList
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        messageList = arrayListOf<MessageMC>()
        getData()
    }
    val latestMessageMap = HashMap<String, MessageMC>()
    private fun getData() {
        val senderUid=FirebaseAuth.getInstance().uid
        val dbRef = FirebaseDatabase.getInstance().getReference("latest_messages/$senderUid")
        dbRef.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var chatMessage:MessageMC? = snapshot.getValue(MessageMC::class.java) ?: return
                if (chatMessage != null) {
                    latestMessageMap[snapshot.key!!] = chatMessage
                    refreshRecyclerViewMessages()
                }
                recyclerView.adapter=LatestMessageAdapter(messageList)
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                var chatMessage:MessageMC? = snapshot.getValue(MessageMC::class.java) ?: return
                if (chatMessage != null) {
                    latestMessageMap[snapshot.key!!] = chatMessage
                    refreshRecyclerViewMessages()
                }
                recyclerView.adapter=LatestMessageAdapter(messageList)
            }
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {
            }
        })
    }

    private fun refreshRecyclerViewMessages() {
        messageList.clear()
        latestMessageMap.values.forEach{
            messageList.add(it)
        }
    }

}